package cool.arch.whaleunit.testsupport;

/*
 * #%L
 * WhaleUnit - Test Support
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.mockito.MockitoAnnotations;

public abstract class AbstractFluent<G extends AbstractGivens<W, T>, W extends AbstractWhens<T>, T extends AbstractThens<T>> implements AbstractGivens<W, T>,
	AbstractWhens<T>, AbstractThens<T> {
	
	private final Map<Class<?>, Exception> exceptions = new HashMap<>();
	
	public AbstractFluent() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final T exceptionsThrownCount(final int expectedCount) {
		assertEquals(expectedCount, exceptions.size());
		
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final T exceptionThrown(final Class<?> type) {
		assertTrue(exceptions.containsKey(type));
		
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final T noExceptionsThrown() {
		assertTrue(exceptions.isEmpty());
		
		return (T) this;
	}
	
	protected final void recordException(final Exception e) {
		exceptions.put(e.getClass(), e);
	}
	
}
