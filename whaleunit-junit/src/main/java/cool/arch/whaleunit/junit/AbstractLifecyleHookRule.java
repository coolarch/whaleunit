package cool.arch.whaleunit.junit;

/*
 * #%L WhaleUnit - JUnit %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class AbstractLifecyleHookRule implements TestRule {

	@Override
	public Statement apply(final Statement base, final Description description) {
		return description.getMethodName() == null
			? new ClassStatement(this, base, description)
			: new MethodStatement(this, base, description);
	}

	protected abstract void beforeClass(Class<?> testClass);

	protected abstract void afterClass(Class<?> testClass);

	/**
	 * Invoked when a test succeeds
	 */
	protected abstract void succeeded(Class<?> testClass, String methodName);

	/**
	 * Invoked when a test fails
	 */
	protected abstract void failed(Class<?> testClass, String methodName, Throwable e);

	/**
	 * Invoked when a test is skipped due to a failed assumption.
	 */
	protected abstract void skipped(Class<?> testClass, String methodName);

	/**
	 * Invoked when a test is about to start
	 */
	protected abstract void starting(Class<?> testClass, String methodName);

	/**
	 * Invoked when a test method finishes (whether passing or failing)
	 */
	protected abstract void finished(Class<?> testClass, String methodName);
}
