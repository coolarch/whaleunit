package cool.arch.whaleunit.api;

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

import java.lang.annotation.Annotation;
import java.util.Collection;

import cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException;
import cool.arch.whaleunit.api.model.ContainerDescriptor;

/**
 * SPI interface for loading {@link ContainerDescriptor} instances from a pluggable source.
 * @param <A> Annotation type that the implementation loads
 */
public interface ContainerDescriptorLoader<A extends Annotation> {

	/**
	 * Attempts to load a collection of {@link ContainerDescriptor} instances from a given annotation.
	 * @param annotation Annotation from which to get information regarding loading the container descriptors
	 * @return Collection of zero or more {@link ContainerDescriptor} instances
	 * @throws ContainerDescriptorLoadException If a failure to load from the annotation takes place
	 */
	Collection<ContainerDescriptor> load(A annotation) throws ContainerDescriptorLoadException;

	/**
	 * Gets the type of annotation that the {@link ContainerDescriptorLoader} implementation uses to source its data 
	 * @return Class of the annotation type
	 */
	Class<?> getAnnotationType();

	/**
	 * Gets the title of the {@link ContainerDescriptorLoader} instance.
	 * @return the title
	 */
	String getTitle();
}
