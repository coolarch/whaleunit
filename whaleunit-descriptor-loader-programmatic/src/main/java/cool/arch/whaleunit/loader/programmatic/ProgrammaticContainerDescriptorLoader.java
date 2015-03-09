package cool.arch.whaleunit.loader.programmatic;

/*
 * #%L
 * WhaleUnit - Annotation Container Loader
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

import static java.util.Arrays.stream;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import cool.arch.whaleunit.api.ContainerDescriptorLoader;
import cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.api.model.ContainerDescriptor;

public class ProgrammaticContainerDescriptorLoader implements ContainerDescriptorLoader<ProgrammaticContainerDescriptors> {
	
	@Override
	public Class<ProgrammaticContainerDescriptors> getAnnotationType() {
		return ProgrammaticContainerDescriptors.class;
	}
	
	@Override
	public String getTitle() {
		return "Programmatic Container Loader";
	}
	
	@Override
	public Collection<ContainerDescriptor> load(final ProgrammaticContainerDescriptors annotation) throws ContainerDescriptorLoadException {
		final Class<? extends Supplier<Collection<ContainerDescriptor>>>[] sources = annotation.sources();
		
		return stream(sources)
			.map(this::instantiate)
			.map(i -> i.get())
			.flatMap(c -> c.stream())
			.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	private Supplier<Collection<ContainerDescriptor>> instantiate(final Class<?> clazz) {
		try {
			return (Supplier<Collection<ContainerDescriptor>>) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new TestManagementException(e);
		}
	}
}
