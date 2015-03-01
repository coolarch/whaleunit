package cool.arch.whaleunit.testng;

/*
 * #%L
 * WhaleUnit - TestNG Support
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

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import cool.arch.whaleunit.runtime.api.WhaleUnitRuntime;

public abstract class AbstractWhaleUnitTestngTest {
	
	private WhaleUnitRuntime runtime;

	@BeforeClass
	public final void beforeClass() throws Exception {
		runtime = new WhaleUnitRuntime();
		runtime.onInit(getClass());
	}

	@BeforeMethod
	public final void beforeMethod(final Method method) throws Exception {
		runtime.onTestStart(method.getName());
	}
	
	@AfterMethod
	public final void afterMethod(final ITestResult testResult) throws Exception {
		final String methodName = testResult.getMethod().getMethodName();

		if (testResult.isSuccess()) {
			runtime.onTestSucceeded(methodName);
		} else {
			runtime.onTestFailed(methodName);
		}

		runtime.onTestEnd(methodName);
	}
	
	@AfterClass
	public final void afterClass() throws Exception {
		runtime.onCleanup();
	}
}
