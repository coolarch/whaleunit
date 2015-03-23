/**
 * 
 */
package cool.arch.whaleunit.support.io;

import org.junit.Test;

import cool.arch.whaleunit.support.io.exception.UnknownResourceException;

/*
 * #%L WhaleUnit - Support %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

/**
 * Test for the {@link cool.arch.whaleunit.support.io.Resource} class.
 */
public class ResourceTest extends ResourceSpec {

	/**
	 * Test method for {@link cool.arch.whaleunit.support.io.Resource#forPath(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public final void testAsStringForClathpathValidFile() throws Exception {
		given().aResourceFor("classpath:/some/resource/path/test.txt")
			.when()
			.resourceIsReadAsAString()
			.then()
			.resultIs("This is test text.\nThis is also test text.");
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.support.io.Resource#forPath(java.lang.String)} for the case of a missing classpath resource being referenced.
	 * @throws Exception 
	 */
	@Test(
		expected = UnknownResourceException.class)
	public final void testAsStringForClathpathMissingFile() throws Exception {
		given().aResourceFor("classpath:/some/resource/path/test_missing.txt")
			.when()
			.resourceIsReadAsAString()
			.then()
			.throwAnyCaughtException();
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.support.io.Resource#forPath(java.lang.String)} for the case of a valid file being referenced.
	 * @throws Exception 
	 */
	@Test
	public final void testAsStringForValidFile() throws Exception {
		given().aResourceFor("file:pom.xml")
			.when()
			.resourceIsReadAsAString()
			.then()
			.resultContains("<project", "<dependencies>", "<artifactId>whaleunit-support</artifactId>");
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.support.io.Resource#forPath(java.lang.String)} for the case of a missing file being referenced.
	 * @throws Exception 
	 */
	@Test(
		expected = UnknownResourceException.class)
	public final void testAsStringForMissingFile() throws Exception {
		given().aResourceFor("file:some_random_file_that_should_not_exist.txt")
			.when()
			.resourceIsReadAsAString()
			.then()
			.throwAnyCaughtException();
	}
}
