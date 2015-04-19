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

import static cool.arch.whaleunit.runtime.States.CREATED;
import static cool.arch.whaleunit.runtime.States.DECOMISSIONED;
import static cool.arch.whaleunit.runtime.States.ENDED;
import static cool.arch.whaleunit.runtime.States.FAILED;
import static cool.arch.whaleunit.runtime.States.INITIALIZED;
import static cool.arch.whaleunit.runtime.States.STARTED;
import static cool.arch.whaleunit.runtime.States.SUCCEEDED;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Predicate;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import cool.arch.stateroom.Context;
import cool.arch.stateroom.Machine;
import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.binder.LoggerAdapterBinder;
import cool.arch.whaleunit.runtime.model.Alphabet;
import cool.arch.whaleunit.runtime.model.MachineModel;
import cool.arch.whaleunit.runtime.transform.DecommissionedModelTransformBiFunction;
import cool.arch.whaleunit.runtime.transform.EndModelTransformBiFunction;
import cool.arch.whaleunit.runtime.transform.FailedModelTransformBiFunction;
import cool.arch.whaleunit.runtime.transform.InitializedModelTransformBiFunction;

public final class WhaleUnitRuntimeImpl implements WhaleUnitRuntime {

	@Inject
	private MachineWrapper machineWrapper;

	private Logger log;

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
	}

	public Logger getLog() {
		return log;
	}

	@Override
	public void onCleanup() {
		machineWrapper.submit(CLEAN_UP);
		machineWrapper.evaluate();
	}

	@Override
	public void onInit(final Class<?> testClass) {
		machineWrapper.getContext().getModel().setTestClass(testClass);
		machineWrapper.submit(START);
		machineWrapper.evaluate();
	}

	@Override
	public void onTestEnd(final String methodName) {
		checkIfCurrentMethod(methodName);
		machineWrapper.submit(END);
		machineWrapper.evaluate();
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
