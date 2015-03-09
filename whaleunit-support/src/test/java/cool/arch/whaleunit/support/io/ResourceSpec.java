package cool.arch.whaleunit.support.io;

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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import cool.arch.whaleunit.support.io.ResourceSpec.Givens;
import cool.arch.whaleunit.support.io.ResourceSpec.Thens;
import cool.arch.whaleunit.support.io.ResourceSpec.Whens;
import cool.arch.whaleunit.support.io.exception.InvalidResourceException;
import cool.arch.whaleunit.support.io.exception.UnknownResourceException;
import cool.arch.whaleunit.testsupport.AbstractFluent;
import cool.arch.whaleunit.testsupport.AbstractGivens;
import cool.arch.whaleunit.testsupport.AbstractThens;
import cool.arch.whaleunit.testsupport.AbstractWhens;
import cool.arch.whaleunit.testsupport.Spec;

/**
 * Specification for test actions for the {@link cool.arch.whaleunit.support.io.Resource} class.
 */
public abstract class ResourceSpec implements Spec<Givens, Whens, Thens> {
	
	@Override
	public Givens given() {
		return new Fluent();
	}
	
	public interface Givens extends AbstractGivens<Whens, Thens> {
		
		Givens aResourceFor(String path);
		
	}
	
	public interface Thens extends AbstractThens<Thens> {
		
		Thens resultContains(String... expectedContents) throws Exception;
		
		Thens resultIs(String expected) throws Exception;
		
		Thens throwAnyCaughtException() throws Exception;
		
	}
	
	public interface Whens extends AbstractWhens<Thens> {
		
		Whens resourceIsReadAsAString();
		
	}
	
	class Fluent extends AbstractFluent<Givens, Whens, Thens> implements Givens, Whens, Thens {
		
		private Exception exception;
		
		private Resource resource;
		
		private String result;
		
		@Override
		public final Givens aResourceFor(final String path) {
			try {
				resource = Resource.forPath(path);
			} catch (final InvalidResourceException e) {
				exception = e;
			}
			
			return this;
		}
		
		@Override
		public final Whens resourceIsReadAsAString() {
			try {
				result = resource.asString();
			} catch (final UnknownResourceException e) {
				exception = e;
			}
			
			return this;
		}
		
		@Override
		public final Thens resultContains(final String... expectedContents) throws Exception {
			assertNotNull(result);
			
			for (final String expectedContent : expectedContents) {
				assertTrue("result must contain " + expectedContent, result.contains(expectedContent));
			}
			
			return this;
		}
		
		@Override
		public final Thens resultIs(final String expected) throws Exception {
			assertNotNull(result);
			assertEquals(expected, result);
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
		
		@Override
		public Whens when() {
			return this;
		}
	}
}
