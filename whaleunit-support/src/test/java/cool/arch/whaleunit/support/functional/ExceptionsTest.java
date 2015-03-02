package cool.arch.whaleunit.support.functional;

/*
 * #%L
 * WhaleUnit - Support
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

import static cool.arch.whaleunit.support.functional.Exceptions.wrap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;

public class ExceptionsTest {
	
	@Test
	public final void testWrapNoException() {
		
		final Class<?> result = Optional.of("java.lang.Object")
			.map(wrap(c -> Class.forName(c), e -> new RuntimeException(e)))
			.get();
		
		assertEquals(Object.class, result);
	}
	
	@Test(expected = RuntimeException.class)
	public final void testWrapException() {
		try {
			Optional.of("java.lang.NotAValidClass")
				.map(wrap(c -> Class.forName(c), e -> new RuntimeException(e)));
		} catch (final RuntimeException result) {
			assertNotNull(result);
			assertEquals(RuntimeException.class, result.getClass());
			assertEquals(ClassNotFoundException.class, result.getCause().getClass());
			
			throw result;
		}
	}
}
