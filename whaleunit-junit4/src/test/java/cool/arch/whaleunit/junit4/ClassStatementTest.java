/**
 * 
 */
package cool.arch.whaleunit.junit4;

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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 *
 */
public class ClassStatementTest {

	private ClassStatement specimen;

	@Mock
	private AbstractLifecyleHookRule mockRule;

	@Mock
	private Statement mockBase;

	@Mock
	private Description mockDescription;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		specimen = new ClassStatement(mockRule, mockBase, mockDescription);
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit4.ClassStatement#evaluate()}.
	 * 
	 * @throws Throwable
	 */
	@Test
	public final void testEvaluate() throws Throwable {
		specimen.evaluate();

		verify(mockRule).beforeClass(any(Class.class));
		verify(mockBase).evaluate();
		verify(mockRule).afterClass(any(Class.class));
	}
}
