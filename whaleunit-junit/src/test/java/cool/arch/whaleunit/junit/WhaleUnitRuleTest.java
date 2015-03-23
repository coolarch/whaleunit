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

import org.junit.Test;

/**
 * 
 */
public class WhaleUnitRuleTest extends WhaleUnitRuleSpec {

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#beforeClass(java.lang.Class)}.
	 */
	@Test
	public final void testBeforeClass() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#afterClass(java.lang.Class)}.
	 */
	@Test
	public final void testAfterClass() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#succeeded(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public final void testSucceeded() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#failed(java.lang.Class, java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public final void testFailed() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#skipped(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public final void testSkipped() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#starting(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public final void testStarting() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#finished(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public final void testFinished() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#WhaleUnitRule()}.
	 */
	@Test
	public final void testWhaleUnitRule() {
		// TODO - Implement
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#WhaleUnitRule(cool.arch.whaleunit.runtime.WhaleUnitRuntime)}.
	 * @throws Exception 
	 */
	@Test
	public final void testWhaleUnitRuleWhaleUnitRuntime() throws Exception {
		given().aWhaleUnitRuleInstantiatedWithNullArguments()
			.when()
			.nothingElseNeedsToBedone()
			.then()
			.exceptionsThrownCount(1)
			.exceptionThrown(NullPointerException.class);
	}
}
