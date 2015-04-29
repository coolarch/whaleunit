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

import static cool.arch.whaleunit.runtime.States.CREATED;
import static cool.arch.whaleunit.runtime.States.DECOMISSIONED;
import static cool.arch.whaleunit.runtime.States.ENDED;
import static cool.arch.whaleunit.runtime.States.ERRORED;
import static cool.arch.whaleunit.runtime.States.FAILED;
import static cool.arch.whaleunit.runtime.States.INITIALIZED;
import static cool.arch.whaleunit.runtime.States.STARTED;
import static cool.arch.whaleunit.runtime.States.SUCCEEDED;
import static cool.arch.whaleunit.runtime.model.Alphabet.CLEAN_UP;
import static cool.arch.whaleunit.runtime.model.Alphabet.END;
import static cool.arch.whaleunit.runtime.model.Alphabet.FAILURE;
import static cool.arch.whaleunit.runtime.model.Alphabet.INIT;
import static cool.arch.whaleunit.runtime.model.Alphabet.START;
import static cool.arch.whaleunit.runtime.model.Alphabet.SUCCESS;

import java.util.function.BiPredicate;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.stateroom.Context;
import cool.arch.stateroom.Machine;
import cool.arch.stateroom.State;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.model.Alphabet;
import cool.arch.whaleunit.runtime.model.MachineModel;
import cool.arch.whaleunit.runtime.transform.ContinuedErrorTransform;
import cool.arch.whaleunit.runtime.transform.DecommissionedTransform;
import cool.arch.whaleunit.runtime.transform.EndTransform;
import cool.arch.whaleunit.runtime.transform.ErrorTransform;
import cool.arch.whaleunit.runtime.transform.FailedTransform;
import cool.arch.whaleunit.runtime.transform.InitTransform;
import cool.arch.whaleunit.runtime.transform.StartedTransform;
import cool.arch.whaleunit.runtime.transform.SuccessTransform;

@Service
public class MachineWrapper {

	private final Machine<MachineModel> machine;

	private Context<MachineModel> context;

	@Inject
	public MachineWrapper(final InitTransform initTransform, final DecommissionedTransform decommissionedTransform,
		final EndTransform endTransform, final FailedTransform failedTransform,
		final StartedTransform startedTransform, final SuccessTransform successTransform, final Containers containers) {

		machine = Machine.builder(MachineModel.class)
			.withStartState(CREATED)
			.withPreEvaluationTransform((state, model) -> model.updateInput())
			.haltWhen((state, model) -> model == null || CLEAN_UP.equals(model))
			.withModelSupplier(() -> new MachineModel())

			.withState(CREATED)
			.to(INITIALIZED, is(INIT), initTransform)
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Created"))

			.withState(INITIALIZED)
			.to(STARTED, is(START), startedTransform)
			.to(ERRORED, is(FAILURE), failedTransform)
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Initialized"))

			.withState(STARTED)
			.to(SUCCEEDED, is(SUCCESS), successTransform)
			.to(FAILED, is(FAILURE), failedTransform)
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Started"))

			.withState(SUCCEEDED)
			.to(STARTED, is(START))
			.to(DECOMISSIONED, is(CLEAN_UP))
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Succeeded"))

			.withState(FAILED)
			.to(STARTED, is(START), startedTransform)
			.to(ENDED, is(FAILURE))
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Failed"))

			.withState(ENDED)
			.to(STARTED, is(START), startedTransform)
			.to(DECOMISSIONED, is(CLEAN_UP), decommissionedTransform)
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Ended"))

			.withState(DECOMISSIONED)
			.to(ERRORED, (state, model) -> true, new ErrorTransform("Error"))

			.withState(ERRORED)
			.to(ERRORED, (state, model) -> true, new ContinuedErrorTransform())

			.build();

		context = machine.create();
	}

	public Context<MachineModel> getContext() {
		return context;
	}

	public void evaluate() {
		while (!context.getModel()
			.getQueue()
			.isEmpty()) {
			context = machine.evaluate(context);
		}
	}

	public void submit(final Alphabet letter) {
		System.out.println("Submitting: " + letter.toString());
		context.getModel()
			.getQueue()
			.add(letter);
	}

	private BiPredicate<State<MachineModel>, MachineModel> is(final Alphabet value) {
		return (state, model) -> {
			System.out.println("Comparing expected " + value + " to " + model.getInput());

			return value.equals(model.getInput());
		};
	}
}
