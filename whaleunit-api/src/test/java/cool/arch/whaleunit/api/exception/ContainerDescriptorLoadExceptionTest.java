/**
 * 
 */
package cool.arch.whaleunit.api.exception;

/*
 * #%L WhaleUnit - API %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * 
 */
public class ContainerDescriptorLoadExceptionTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException#ContainerDescriptorLoadException()}.
	 */
	@Test
	public final void testContainerDescriptorLoadException() {
		final Throwable throwable = new ContainerDescriptorLoadException();
		assertEquals(null, throwable.getMessage());
		assertEquals(null, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException#ContainerDescriptorLoadException(java.lang.String, java.lang.Throwable, boolean, boolean)}.
	 */
	@Test
	public final void testContainerDescriptorLoadExceptionStringThrowableBooleanBoolean() {
		final Throwable cause = new NullPointerException();
		final Throwable throwable = new ContainerDescriptorLoadException("message", cause, true, true);
		assertEquals("message", throwable.getMessage());
		assertSame(cause, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException#ContainerDescriptorLoadException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public final void testContainerDescriptorLoadExceptionStringThrowable() {
		final Throwable cause = new NullPointerException();
		final Throwable throwable = new ContainerDescriptorLoadException("message", cause);
		assertEquals("message", throwable.getMessage());
		assertSame(cause, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException#ContainerDescriptorLoadException(java.lang.String)}.
	 */
	@Test
	public final void testContainerDescriptorLoadExceptionString() {
		final Throwable throwable = new ContainerDescriptorLoadException("message");
		assertEquals("message", throwable.getMessage());
		assertSame(null, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException#ContainerDescriptorLoadException(java.lang.Throwable)}.
	 */
	@Test
	public final void testContainerDescriptorLoadExceptionThrowable() {
		final Throwable cause = new NullPointerException();
		final Throwable throwable = new ContainerDescriptorLoadException(cause);
		assertEquals("java.lang.NullPointerException", throwable.getMessage());
		assertSame(cause, throwable.getCause());
	}
}
