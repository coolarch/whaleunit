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

import static cool.arch.whaleunit.support.functional.Exceptions.wrap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import cool.arch.stateroom.State;
import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.model.MachineModel;

public final class EndModelTransformBiFunction implements BiFunction<State<MachineModel>, MachineModel, MachineModel> {

	private final Containers containers;

	private final Set<String> globallyDirtiedContainerNames;

	public EndModelTransformBiFunction(final Containers containers, final Set<String> globallyDirtiedContainerNames) {
		this.containers = containers;
		this.globallyDirtiedContainerNames = globallyDirtiedContainerNames;
	}

	@Override
	public MachineModel apply(final State<MachineModel> state, final MachineModel model) {
		final Set<String> containersToRestart = new HashSet<>();
		containersToRestart.addAll(globallyDirtiedContainerNames);
		final String methodName = model.getCurrentMethod();

		Optional.ofNullable(model.getTestClass())
			.map(
				wrap(testClass -> testClass.getMethod(methodName), e -> new TestManagementException(
					"Error looking up method " + methodName)))
			.map(method -> method.getAnnotation(DirtiesContainers.class))
			.map(annotation -> annotation.value())
			.map(Arrays::asList)
			.ifPresent(containersToRestart::addAll);

		containers.restart(containersToRestart);

		return model;
	}
}
