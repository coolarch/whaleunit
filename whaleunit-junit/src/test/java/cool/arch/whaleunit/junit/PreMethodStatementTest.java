/**
 * 
 */
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

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

/**
 * 
 */
public class PreMethodStatementTest {

	private PreMethodStatement specimen;

	@Mock
	private Statement mockChildStatement;

	private Method mockMethod;

	@Mock
	private WhaleUnitRuntime mockRuntime;

	@Mock
	private Object mockInstance;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMethod = PreMethodStatementTest.class.getMethod("testEvaluate");
		specimen = new PreMethodStatement(mockChildStatement, mockMethod, mockRuntime, mockInstance);
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.PreMethodStatement#evaluate()}.
	 * @throws Throwable 
	 */
	@Test
	public final void testEvaluate() throws Throwable {
		specimen.evaluate();
	}
}
