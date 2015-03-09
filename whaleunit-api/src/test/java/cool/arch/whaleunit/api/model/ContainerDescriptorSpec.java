package cool.arch.whaleunit.api.model;

/*
 * #%L
 * WhaleUnit - API
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
import cool.arch.whaleunit.api.model.ContainerDescriptorSpec.Givens;
import cool.arch.whaleunit.api.model.ContainerDescriptorSpec.Thens;
import cool.arch.whaleunit.api.model.ContainerDescriptorSpec.Whens;
import cool.arch.whaleunit.testsupport.AbstractFluent;
import cool.arch.whaleunit.testsupport.AbstractGivens;
import cool.arch.whaleunit.testsupport.AbstractThens;
import cool.arch.whaleunit.testsupport.AbstractWhens;
import cool.arch.whaleunit.testsupport.Spec;

public abstract class ContainerDescriptorSpec implements Spec<Givens, Whens, Thens> {
	
	@Override
	public Givens given() {
		return new Fluent();
	}
	
	public interface Givens extends AbstractGivens<Whens, Thens> {
		
		Givens aBuilder();

	}
	
	public interface Thens extends AbstractThens<Thens> {
		
		Thens theCommandIs(String command);
		
		Thens theEntryPointIs(String entryPoint);

	}
	
	public interface Whens extends AbstractWhens<Thens> {
		
		Whens aCommandIsDefined(String command);

		Whens anEntryPointIsDefined(String entryPoint);
		
		Whens anIdIsDefined(String id);

		Whens theContainerIsBuilt();

	}
	
	class Fluent extends AbstractFluent<Givens, Whens, Thens> implements Givens, Whens, Thens {
		
		private ContainerDescriptor.Builder builder;
		
		private ContainerDescriptor container;

		@Override
		public Givens aBuilder() {
			builder = ContainerDescriptor.builder();

			return this;
		}
		
		@Override
		public Whens aCommandIsDefined(final String command) {
			builder = builder.withCommand(command);
			
			return this;
		}
		
		@Override
		public Whens anEntryPointIsDefined(final String entryPoint) {
			builder = builder.withEntryPoint(entryPoint);
			
			return this;
		}
		
		@Override
		public Whens anIdIsDefined(final String id) {
			builder = builder.withId(id);
			
			return this;
		}
		
		@Override
		public Thens theCommandIs(final String command) {
			assertNotNull(container);
			assertEquals(command, container.getCommand().get());
			
			return this;
		}
		
		@Override
		public Whens theContainerIsBuilt() {
			container = builder.build().get();
			
			return this;
		}
		
		@Override
		public Thens theEntryPointIs(final String entryPoint) {
			assertNotNull(container);
			assertEquals(entryPoint, container.getEntryPoint().get());
			
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
