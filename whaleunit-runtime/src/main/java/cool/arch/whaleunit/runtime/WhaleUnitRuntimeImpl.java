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
import static cool.arch.whaleunit.runtime.model.Alphabet.FAILURE;
import static cool.arch.whaleunit.runtime.model.Alphabet.START;
import static cool.arch.whaleunit.runtime.model.Alphabet.SUCCESS;
import static cool.arch.whaleunit.runtime.model.Alphabet.INIT;
import static java.lang.reflect.Modifier.FINAL;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.api.WhaleUnitContext;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.runtime.binder.LoggerAdapterBinder;
import cool.arch.whaleunit.runtime.impl.WhaleUnitContextImpl;
import cool.arch.whaleunit.runtime.model.MachineModel;

public final class WhaleUnitRuntimeImpl implements WhaleUnitRuntime {

	@Inject
	private MachineWrapper machineWrapper;

	private Logger log;

	@Inject
	private LoggerAdapterFactory loggerAdapterFactory;

	@Inject
	private WhaleUnitContextImpl context;

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
		log = loggerAdapterFactory.create(this.getClass());

		System.out.println("Runtime instantiated");
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
		machineWrapper.getContext()
			.getModel()
			.setTestClass(testClass);

		machineWrapper.submit(INIT);
		machineWrapper.evaluate();
	}

	@Override
	public void onTestFailed(final String methodName) {
		checkIfCurrentMethod(methodName);
		machineWrapper.submit(FAILURE);
		machineWrapper.evaluate();

	}

	@Override
	public void onTestStart(final String methodName, final Object instance) {
		machineWrapper.getContext()
			.getModel()
			.setCurrentMethod(methodName);

		machineWrapper.getContext()
			.getModel()
			.setInstance(instance);

		machineWrapper.submit(START);
		machineWrapper.evaluate();
	}

	@Override
	public void onTestSucceeded(final String methodName) {
		checkIfCurrentMethod(methodName);
		machineWrapper.submit(SUCCESS);
		machineWrapper.evaluate();
	}

	private void checkIfCurrentMethod(final String methodName) {
		final MachineModel model = machineWrapper.getContext()
			.getModel();

		if (!Objects.equals(model.getCurrentMethod(), methodName)) {
			throw new TestManagementException("Unexpected method ended: " + methodName);
		}
	}

	@Override
	public void inject(Object instance) {
		if (instance == null) {
			return;
		}
		
		injectContext(instance.getClass(), instance);
	}
	
	private void injectContext(final Class<?> clazz, final Object instance) {
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
					field.set(instance, context);
				} catch (Exception e) {
					throw new TestManagementException("Error injecting context into field " + field.getName(), e);
				}
			});
		
		injectContext(clazz.getSuperclass(), instance);
	}

	
}
