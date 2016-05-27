/**
 * 
 */
package cool.arch.whaleunit.junit4;

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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cool.arch.whaleunit.junit4.WhaleUnitJUnit4ClassRunner;

/**
 * 
 */
public class WhaleUnitJUnit4ClassRunnerTest {

	private WhaleUnitJUnit4ClassRunner specimen;

	@Mock
	private FrameworkMethod frameworkMethodMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		specimen = new WhaleUnitJUnit4ClassRunner(WhaleUnitJUnit4ClassRunnerTest.class);
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit4.WhaleUnitJUnit4ClassRunner#createTest()}.
	 * @throws Exception 
	 */
	@Test
	public final void testCreateTest() throws Exception {
		specimen.createTest();
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit4.WhaleUnitJUnit4ClassRunner#withBeforeClasses(org.junit.runners.model.Statement)}.
	 */
	@Test
	public final void testWithBeforeClassesStatement() {
		specimen.withBeforeClasses(null);
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit4.WhaleUnitJUnit4ClassRunner#withAfterClasses(org.junit.runners.model.Statement)}.
	 */
	@Test
	public final void testWithAfterClassesStatement() {
		specimen.withAfterClasses(null);
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit4.WhaleUnitJUnit4ClassRunner#withBefores(org.junit.runners.model.FrameworkMethod, java.lang.Object, org.junit.runners.model.Statement)}.
	 */
	@Test
	public final void testWithBeforesFrameworkMethodObjectStatement() {
		specimen.withBefores(frameworkMethodMock, null, null);
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit4.WhaleUnitJUnit4ClassRunner#withAfters(org.junit.runners.model.FrameworkMethod, java.lang.Object, org.junit.runners.model.Statement)}.
	 */
	@Test
	public final void testWithAftersFrameworkMethodObjectStatement() {
		specimen.withAfters(frameworkMethodMock, null, null);
	}
}
