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

import static java.util.Objects.requireNonNull;
import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

public final class WhaleUnitRule extends AbstractLifecyleHookRule {
	
	private WhaleUnitRuntime runtime;
	
	public WhaleUnitRule() {
	}
	
	WhaleUnitRule(final WhaleUnitRuntime runtime) {
		this.runtime = requireNonNull(runtime, "runtime shall not be null");
	}
	
	@Override
	protected void beforeClass(final Class<?> testClass) {
		requireNonNull(testClass, "testClass shall not be null");
		
		if (runtime == null) {
			runtime = WhaleUnitRuntime.create(testClass);
		}
		
		runtime.onInit(testClass);
	}
	
	@Override
	protected void afterClass(final Class<?> testClass) {
		runtime.onCleanup();
	}
	
	@Override
	protected void succeeded(final Class<?> testClass, final String methodName) {
		runtime.onTestSucceeded(methodName);
	}
	
	@Override
	protected void failed(final Class<?> testClass, final String methodName, final Throwable e) {
		runtime.onTestFailed(methodName);
	}
	
	@Override
	protected void skipped(final Class<?> testClass, final String methodName) {
		// Intentionally do nothing
	}
	
	@Override
	protected void starting(final Class<?> testClass, final String methodName) {
		runtime.onTestStart(methodName);
	}
	
	@Override
	protected void finished(final Class<?> testClass, final String methodName) {
		runtime.onTestEnd(methodName);
	}
}
