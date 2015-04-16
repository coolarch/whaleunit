package cool.arch.whaleunit.runtime;

/*
 * #%L WhaleUnit - Runtime %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static cool.arch.whaleunit.runtime.model.Alphabet.CLEAN_UP;
import static cool.arch.whaleunit.runtime.model.Alphabet.END;
import static cool.arch.whaleunit.runtime.model.Alphabet.FAILURE;
import static cool.arch.whaleunit.runtime.model.Alphabet.INIT;
import static cool.arch.whaleunit.runtime.model.Alphabet.START;
import static cool.arch.whaleunit.runtime.model.Alphabet.SUCCESS;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import cool.arch.stateroom.Context;
import cool.arch.stateroom.Machine;
import cool.arch.stateroom.State;
import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.binder.LoggerAdapterBinder;
import cool.arch.whaleunit.runtime.model.Alphabet;
import cool.arch.whaleunit.runtime.model.MachineModel;

public final class WhaleUnitRuntimeImpl implements WhaleUnitRuntime {

	@Inject
	private Containers containers;

	private final Set<String> globallyDirtiedContainerNames = new HashSet<>();

	private Logger log;

	@Inject
	private InitializedModelTransformBiFunction initializedModelTransformBiFunction;

	private static final State<MachineModel> CREATED = State.of("Created");

	private static final State<MachineModel> INITIALIZED = State.of("Initialized");

	private static final State<MachineModel> STARTED = State.of("Started");

	private static final State<MachineModel> ENDED = State.of("Ended");

	private static final State<MachineModel> FAILED = State.of("Failed");

	private static final State<MachineModel> SUCCEEDED = State.of("Succeeded");

	private static final State<MachineModel> DECOMISSIONED = State.of("Decomissioned", true);

	private final Queue<Alphabet> input = new LinkedList<>();

	private final Machine<MachineModel> machine;

	private Context<MachineModel> context;

	private static Predicate<Context<MachineModel>> is(final Alphabet value) {
		return c -> value.equals(c.getModel()
			.getInput());
	}

	/**
	 * Constructs a new WhaleUnitRule.
	 */
	public WhaleUnitRuntimeImpl() {
		this(ServiceLocatorUtilities.createAndPopulateServiceLocator());
	}

	WhaleUnitRuntimeImpl(final ServiceLocator locator) {
		requireNonNull(locator, "locator shall not be null");
		ServiceLocatorUtilities.bind(locator, new LoggerAdapterBinder());
		locator.inject(this);

		machine = Machine.builder(MachineModel.class)
			.withStartState(CREATED)
			.withPreEvaluationTransform((state, model) -> model.setInput(input.poll()))
			.haltWhen((state, model) -> model == null || CLEAN_UP.equals(model))
			.withModelSupplier(() -> new MachineModel().setInput(input.poll()))
			.withState(CREATED)
			.to(INITIALIZED, is(INIT), initializedModelTransformBiFunction)
			.withState(INITIALIZED)
			.to(STARTED, is(START), (start, model) -> {
				containers.startAll();

				return model;
			})
			.withState(STARTED)
			.to(SUCCEEDED, is(SUCCESS))
			.to(FAILED, is(FAILURE), new FailedModelTransformBiFunction(containers))
			.withState(SUCCEEDED)
			.to(ENDED, is(END))
			.withState(FAILED)
			.to(ENDED, is(END), new EndModelTransformBiFunction(containers, globallyDirtiedContainerNames))
			.withState(ENDED)
			.to(DECOMISSIONED, is(CLEAN_UP), new DecommissionedModelTransformBiFunction(containers))
			.build();

		context = machine.create();
	}

	public Logger getLog() {
		return log;
	}

	@Override
	public void onCleanup() {
		supplyAndEvaluate(CLEAN_UP);
		context = machine.evaluate(context);
	}

	private void supplyAndEvaluate(final Alphabet letter) {
		input.add(letter);
		context = machine.evaluate(context);
	}

	@Override
	public void onInit(final Class<?> testClass) {
		final MachineModel model = context.getModel();
		model.setTestClass(testClass);
		supplyAndEvaluate(START);
	}

	@Override
	public void onTestEnd(final String methodName) {
		checkIfCurrentMethod(methodName);
		supplyAndEvaluate(END);
	}

	@Override
	public void onTestFailed(final String methodName) {
		checkIfCurrentMethod(methodName);
		supplyAndEvaluate(FAILURE);
	}

	@Override
	public void onTestStart(final String methodName) {
		context.getModel()
			.setCurrentMethod(methodName);
		supplyAndEvaluate(START);
	}

	@Override
	public void onTestSucceeded(final String methodName) {
		checkIfCurrentMethod(methodName);
		supplyAndEvaluate(SUCCESS);
	}

	private void checkIfCurrentMethod(final String methodName) {
		final MachineModel model = context.getModel();

		if (!Objects.equals(model.getCurrentMethod(), methodName)) {
			throw new TestManagementException("Unexpected method ended: " + methodName);
		}
	}
}
