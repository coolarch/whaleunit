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

import org.mockito.Mock;

import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Givens;
import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Thens;
import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Whens;
import cool.arch.whaleunit.runtime.WhaleUnitRuntime;
import cool.arch.whaleunit.testsupport.AbstractFluent;
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
		
		Givens aWhaleUnitRuleInstantiatedWithMockArguments();
		
		Givens aWhaleUnitRuleInstantiatedWithNullArguments();
		
		Givens aWhaleUnitRuleInstantiatedWithoutArguments();
	}
	
	public interface Thens extends AbstractThens<Thens> {
		
	}
	
	public interface Whens extends AbstractWhens<Thens> {
		
		Whens nothingElseNeedsToBedone();
		
	}
	
	private class Fluent extends AbstractFluent<Givens, Whens, Thens> implements Givens, Whens, Thens {
		
		@Mock
		private WhaleUnitRuntime mockWhaleUnitRuntime;
		
		private WhaleUnitRule specimen;
		
		@Override
		public Givens aWhaleUnitRuleInstantiatedWithMockArguments() {
			specimen = new WhaleUnitRule(mockWhaleUnitRuntime);
			
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
		public Givens aWhaleUnitRuleInstantiatedWithoutArguments() {
			specimen = new WhaleUnitRule();
			
			return this;
		}
		
		@Override
		public Whens nothingElseNeedsToBedone() {
			return this;
		}
		
		@Override
		public Thens then() throws Exception {
			return this;
		}
		
		@Override
		public Whens when() {
			return this;
		}
	}
}
