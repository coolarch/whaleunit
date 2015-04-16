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

import java.util.function.BiFunction;

import cool.arch.stateroom.State;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.model.MachineModel;

public final class FailedModelTransformBiFunction implements
	BiFunction<State<MachineModel>, MachineModel, MachineModel> {

	private final Containers containers;

	public FailedModelTransformBiFunction(final Containers containers) {
		this.containers = containers;
	}

	@Override
	public MachineModel apply(final State<MachineModel> state, final MachineModel model) {
		containers.restartAll();

		return model;
	}
}
