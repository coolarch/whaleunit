package cool.arch.whaleunit.runtime.transform;

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

import static java.lang.reflect.Modifier.FINAL;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.stateroom.State;
import cool.arch.whaleunit.api.WhaleUnitContext;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.impl.WhaleUnitContextImpl;
import cool.arch.whaleunit.runtime.model.Alphabet;
import cool.arch.whaleunit.runtime.model.MachineModel;

@Service
public final class StartedTransform implements BiFunction<State<MachineModel>, MachineModel, MachineModel> {

	private final Containers containers;
	
	private final WhaleUnitContext whaleUnitContext;

	@Inject
	public StartedTransform(final Containers containers, final WhaleUnitContextImpl whaleUnitContext) {
		this.containers = containers;
		this.whaleUnitContext = requireNonNull(whaleUnitContext, "whaleUnitContext shall not be null");
	}

	@Override
	public MachineModel apply(final State<MachineModel> state, final MachineModel model) {
		model.setWhaleUnitContext(whaleUnitContext);
		
		try {
			containers.startAll();
		} catch (Exception e) {
			model.getQueue()
				.add(Alphabet.FAILURE);

			throw e;
		}
		
		injectContext(model);

		return model;
	}
	
	private void injectContext(final MachineModel model) {
		final Object instance = model.getInstance();
		
		if (instance == null) {
			return;
		}
		
		injectContext(instance.getClass(), model);
	}
	
	private void injectContext(final Class<?> clazz, final MachineModel model) {
		if (Object.class.equals(clazz)) {
			return;
		}
		
		final Field[] fields = clazz.getDeclaredFields();
		
		stream(fields)
			.filter(field -> WhaleUnitContext.class.equals(field.getType()))
			.filter(field -> (field.getModifiers() & FINAL) != FINAL)
			.forEach(field -> {
				field.setAccessible(true);
				
				try {
					field.set(model.getInstance(), model.getWhaleUnitContext());
				} catch (Exception e) {
					throw new TestManagementException("Error injecting context into field " + field.getName(), e);
				}
			});
		
		injectContext(clazz.getSuperclass(), model);
	}
}
