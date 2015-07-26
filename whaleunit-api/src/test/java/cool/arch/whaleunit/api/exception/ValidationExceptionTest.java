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
public class ValidationExceptionTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ValidationException#ValidationException()}.
	 */
	@Test
	public final void testValidationException() {
		final Throwable throwable = new ValidationException();
		assertEquals(null, throwable.getMessage());
		assertEquals(null, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ValidationException#ValidationException(java.lang.String, java.lang.Throwable, boolean, boolean)}.
	 */
	@Test
	public final void testValidationExceptionStringThrowableBooleanBoolean() {
		final Throwable cause = new NullPointerException();
		final Throwable throwable = new ValidationException("message", cause, true, true);
		assertEquals("message", throwable.getMessage());
		assertSame(cause, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ValidationException#ValidationException(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public final void testValidationExceptionStringThrowable() {
		final Throwable cause = new NullPointerException();
		final Throwable throwable = new ValidationException("message", cause);
		assertEquals("message", throwable.getMessage());
		assertSame(cause, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ValidationException#ValidationException(java.lang.String)}.
	 */
	@Test
	public final void testValidationExceptionString() {
		final Throwable throwable = new ValidationException("message");
		assertEquals("message", throwable.getMessage());
		assertSame(null, throwable.getCause());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.exception.ValidationException#ValidationException(java.lang.Throwable)}.
	 */
	@Test
	public final void testValidationExceptionThrowable() {
		final Throwable cause = new NullPointerException();
		final Throwable throwable = new ValidationException(cause);
		assertEquals("java.lang.NullPointerException", throwable.getMessage());
		assertSame(cause, throwable.getCause());
	}
}
