package cool.arch.whaleunit.runtime.api;

/*
 * #%L
 * WhaleUnit - Runtime
 * %%
 * Copyright (C) 2015 CoolArch
 * %%
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * #L%
 */

import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.runtime.impl.DelegatingLoggerAdapterFactoryImpl;

public final class WhaleUnitRuntime implements ContextTracker {
	
	private final ServiceLocator locator;

	@Inject
	private ContextTracker tracker;

	/**
	 * Constructs a new WhaleUnitRuntime allowing JSR-330 injection to be disabled.
	 * @param inject Whether or not creation of an JSR-330 service locator should be created or used
	 */
	WhaleUnitRuntime(final ContextTracker tracker) {
		this.tracker = tracker;
		locator = null;
	}
	
	/**
	 * Constructs a new WhaleUnitRule.
	 */
	public WhaleUnitRuntime() {
		locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
		ServiceLocatorUtilities.bind(locator, new LoggerAdapterBinder());

		locator.inject(this);
	}

	@Override
	public void onInit(final Class<?> testClass) {
		tracker.onInit(testClass);
	}
	
	@Override
	public void onTestStart(final String methodName) {
		tracker.onTestStart(methodName);
	}
	
	@Override
	public void onTestSucceeded(final String methodName) {
		tracker.onTestStart(methodName);
	}
	
	@Override
	public void onTestFailed(final String methodName) {
		tracker.onTestFailed(methodName);
	}
	
	@Override
	public void onTestEnd(final String methodName) {
		tracker.onTestEnd(methodName);
	}
	
	@Override
	public void onCleanup() {
		tracker.onCleanup();
	}
	
	private static class LoggerAdapterBinder extends AbstractBinder {
		
		@Override
		protected void configure() {
			bind(DelegatingLoggerAdapterFactoryImpl.class)
				.to(DelegatingLoggerAdapterFactory.class)
				.to(LoggerAdapterFactory.class)
				.in(Singleton.class);
			;
		}
	}
}
