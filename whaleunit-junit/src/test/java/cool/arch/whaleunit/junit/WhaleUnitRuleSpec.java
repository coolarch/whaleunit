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

import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Givens;
import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Thens;
import cool.arch.whaleunit.junit.WhaleUnitRuleSpec.Whens;
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
		
	}
	
	public interface Whens extends AbstractWhens<Thens> {
		
	}
	
	public interface Thens extends AbstractThens {
		
		Thens throwAnyCaughtException() throws Exception;
		
	}
	
	private class Fluent implements Givens, Whens, Thens {
		
		private Exception exception;
		
		@Override
		public Whens when() {
			return this;
		}
		
		@Override
		public Thens then() throws Exception {
			throwAnyCaughtException();
			return this;
		}
		
		@Override
		public final Thens throwAnyCaughtException() throws Exception {
			if (exception != null) {
				throw exception;
			}
			
			return this;
		}
	}
}
