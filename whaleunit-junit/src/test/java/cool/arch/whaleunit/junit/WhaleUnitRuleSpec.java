package cool.arch.whaleunit.junit;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Givens;
import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Thens;
import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Whens;
import cool.arch.whaleunit.runtime.WhaleUnitRuntime;
import cool.arch.whaleunit.testsupport.AbstractGivens;
import cool.arch.whaleunit.testsupport.AbstractThens;
import cool.arch.whaleunit.testsupport.AbstractWhens;
import cool.arch.whaleunit.testsupport.Spec;

/**
 * Specification for test actions for the {@link cool.arch.whaleunit.support.io.Resource} class.
 */
public abstract class WhaleUnitRuleSpec implements Spec<Givens, Whens, Thens> {
	
	@Override
	public Givens given() {
		return new Fluent();
	}
	
	public interface Givens extends AbstractGivens<Whens, Thens> {
		
		Givens aWhaleUnitRuleInstantiatedWithNullArguments();

		Givens aWhaleUnitRuleInstantiatedWithoutArguments();
		
		Givens aWhaleUnitRuleInstantiatedWithMockArguments();
	}
	
	public interface Whens extends AbstractWhens<Thens> {
		
		Whens nothingElseNeedsToBedone();

	}
	
	public interface Thens extends AbstractThens {
		
		Thens exceptionsThrownCount(int count);
		
		Thens exceptionThrown(Class<?> type);

		Thens noExceptionsThrown();
		
	}
	
	private class Fluent implements Givens, Whens, Thens {
		
		private Fluent() {
			MockitoAnnotations.initMocks(this);
		}
		
		@Mock
		private WhaleUnitRuntime mockWhaleUnitRuntime;

		private WhaleUnitRule specimen;

		private final Map<Class<?>, Exception> exceptions = new HashMap<>();
		
		@Override
		public Whens when() {
			return this;
		}
		
		@Override
		public Thens then() throws Exception {
			return this;
		}
		
		@Override
		public final Thens noExceptionsThrown() {
			assertTrue(exceptions.isEmpty());
			
			return this;
		}
		
		@Override
		public Givens aWhaleUnitRuleInstantiatedWithoutArguments() {
			specimen = new WhaleUnitRule();
			
			return this;
		}
		
		@Override
		public Givens aWhaleUnitRuleInstantiatedWithNullArguments() {
			try {
				specimen = new WhaleUnitRule(null);
			} catch (final Exception e) {
				recordException(e);
			}
			
			return this;
		}
		
		@Override
		public Givens aWhaleUnitRuleInstantiatedWithMockArguments() {
			specimen = new WhaleUnitRule(mockWhaleUnitRuntime);
			
			return this;
		}
		
		@Override
		public Thens exceptionsThrownCount(final int expectedCount) {
			assertEquals(expectedCount, exceptions.size());

			return this;
		}
		
		@Override
		public Thens exceptionThrown(final Class<?> type) {
			assertTrue(exceptions.containsKey(type));
			
			return this;
		}
		
		private void recordException(final Exception e) {
			exceptions.put(e.getClass(), e);
		}
		
		@Override
		public Whens nothingElseNeedsToBedone() {
			return this;
		}
	}
}
