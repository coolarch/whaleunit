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

import java.lang.reflect.Method;

import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.junit.exception.TestManagementException;


public final class WhaleUnitRule extends AbstractLifecyleHookRule {
	
	private final LifeCycle tracker = new ContextTracker();

	@Override
	protected void beforeClass(Class<?> testClass) {
		final DirtiesContainers annotation = testClass.getAnnotation(DirtiesContainers.class);
		
		if (annotation == null) {
			tracker.onInit(testClass);
		} else {
			tracker.onInit(testClass, annotation.value());
		}
	}

	@Override
	protected void afterClass(Class<?> testClass) {
		tracker.onCleanup();
	}

	@Override
	protected void succeeded(Class<?> testClass, String methodName) {
		tracker.onTestSucceeded();
	}

	@Override
	protected void failed(Class<?> testClass, String methodName, Throwable e) {
		tracker.onTestFailed();
	}

	@Override
	protected void skipped(Class<?> testClass, String methodName) {
		// Intentionally do nothing
	}

	@Override
	protected void starting(Class<?> testClass, String methodName) {
		tracker.onTestStart();
	}

	@Override
	protected void finished(Class<?> testClass, String methodName) {
		try {
			final Method method = testClass.getMethod(methodName);
			
			final DirtiesContainers annotation = method.getAnnotation(DirtiesContainers.class);
			
			if (annotation == null) {
				tracker.onTestEnd();
			} else {
				tracker.onTestEnd("foo", "bar");
			}
			
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO - Try to break this
			throw new TestManagementException("Error looking up method " + methodName);
		}
	}
}
