package cool.arch.whaleunit.junit;

/*
 * #%L WhaleUnit - JUnit Support %% Copyright (C) 2015 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

public final class WhaleUnitJUnit4ClassRunner extends BlockJUnit4ClassRunner {

	private final WhaleUnitRuntime runtime = WhaleUnitRuntime.create();

	public WhaleUnitJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected Object createTest() throws Exception {
		final Object instance = super.createTest();

		return instance;
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		return new PreClassStatement(super.withBeforeClasses(statement), runtime, getTestClass().getJavaClass());
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		return new PostClassStatement(super.withAfterClasses(statement), runtime);
	}

	@Override
	protected Statement withBefores(FrameworkMethod frameworkMethod, Object instance, Statement statement) {
		runtime.inject(instance);
		return new PreMethodStatement(super.withBefores(frameworkMethod, instance, statement),
			frameworkMethod.getMethod(), runtime, instance);
	}

	@Override
	protected Statement withAfters(FrameworkMethod frameworkMethod, Object instance, Statement statement) {
		return new PostMethodStatement(super.withAfters(frameworkMethod, instance, statement),
			frameworkMethod.getMethod(), runtime);
	}
}
