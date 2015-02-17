package cool.arch.whaleunit.junit;

/*
 * #%L
 * WhaleUnit - JUnit
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

import static org.glassfish.hk2.utilities.ServiceLocatorUtilities.createAndPopulateServiceLocator;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;

import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.junit.api.ContextTracker;
import cool.arch.whaleunit.junit.exception.TestManagementException;
import cool.arch.whaleunit.support.functional.Optional;
import cool.arch.whaleunit.support.functional.primitives.Effect;
import cool.arch.whaleunit.support.functional.primitives.Function;
import cool.arch.whaleunit.support.functional.primitives.Predicate;

public final class WhaleUnitRule extends AbstractLifecyleHookRule {
	
	@Inject
	private ContextTracker tracker;
	
	private final Optional<ServiceLocator> locator;
	
	/**
	 * Constructs a new WhaleUnitRule.
	 */
	public WhaleUnitRule() {
		this(true);
	}
	
	/**
	 * Constructs a new WhaleUnitRule allowing JSR-330 injection to be disabled.
	 * @param inject Whether or not creation of an JSR-330 service locator should be created or used
	 */
	WhaleUnitRule(final boolean inject) {
		final WhaleUnitRule _this = this;
		
		locator = Optional.of(inject)
			.filter(new Predicate<Boolean>() {
				@Override
				public boolean test(final Boolean inject) {
					return inject;
				}
			}).map(new Function<Boolean, ServiceLocator>() {
				@Override
				public ServiceLocator apply(final Boolean locator) {
					return createAndPopulateServiceLocator();
				}
			}).ifPresent(new Effect<ServiceLocator>() {
				@Override
				public void apply(final ServiceLocator locator) {
					locator.inject(_this);
				}
			});
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void beforeClass(final Class<?> testClass) {
		Optional.of((Class) testClass)
		.map(new Function<Class, DirtiesContainers>() {
			@Override
			public DirtiesContainers apply(final Class testClass) {
				return (DirtiesContainers) testClass.getAnnotation(DirtiesContainers.class);
			}
		})
		.map(new Function<DirtiesContainers, String[]>() {
			@Override
			public String[] apply(final DirtiesContainers annotation) {
				return annotation.value();
			}
		})
		.or(new String[] {})
		.ifPresent(new Effect<String[]>() {
			@Override
			public void apply(final String[] dirtyContainerIds) {
				tracker.onInit(testClass, dirtyContainerIds);
			}
		});
	}
	
	@Override
	protected void afterClass(final Class<?> testClass) {
		tracker.onCleanup();
	}
	
	@Override
	protected void succeeded(final Class<?> testClass, final String methodName) {
		tracker.onTestSucceeded();
	}
	
	@Override
	protected void failed(final Class<?> testClass, final String methodName, final Throwable e) {
		tracker.onTestFailed();
	}
	
	@Override
	protected void skipped(final Class<?> testClass, final String methodName) {
		// Intentionally do nothing
	}
	
	@Override
	protected void starting(final Class<?> testClass, final String methodName) {
		tracker.onTestStart();
	}
	
	@Override
	protected void finished(final Class<?> testClass, final String methodName) {
		Optional.of(methodName).map(new Function<String, Method>() {
			@Override
			public Method apply(final String methodName) {
				try {
					return testClass.getMethod(methodName);
				} catch (NoSuchMethodException | SecurityException e) {
					throw new TestManagementException("Error looking up method " + methodName);
				}
			}
		})
		.map(new Function<Method,DirtiesContainers>() {
			@Override
			public DirtiesContainers apply(final Method method) {
				return method.getAnnotation(DirtiesContainers.class);
			}
		})
		.map(new Function<DirtiesContainers,String[]>() {
			@Override
			public String[] apply(final DirtiesContainers annotation) {
				return annotation.value();
			}
		})
		.or(new String[] {})
		.ifPresent(new Effect<String[]>() {
			@Override
			public void apply(final String[] containerIds) {
				tracker.onTestEnd(containerIds);
			}
		});
	}
	
	public ContextTracker getTracker() {
		return tracker;
	}
	
	public void setTracker(final ContextTracker tracker) {
		this.tracker = tracker;
	}
}
