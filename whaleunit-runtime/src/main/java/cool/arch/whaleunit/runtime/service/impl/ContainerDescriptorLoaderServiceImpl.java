package cool.arch.whaleunit.runtime.service.impl;

/*
 * #%L WhaleUnit - Runtime %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static cool.arch.whaleunit.support.functional.Exceptions.wrap;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.annotation.ContainerDescriptorLoaderSource;
import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.api.ContainerDescriptorLoader;
import cool.arch.whaleunit.api.exception.ContainerDescriptorLoadException;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.api.model.ContainerDescriptor;
import cool.arch.whaleunit.runtime.service.api.ContainerDescriptorLoaderService;

@Service
public class ContainerDescriptorLoaderServiceImpl implements ContainerDescriptorLoaderService {

	private final Map<Class<?>, ContainerDescriptorLoader<?>> loaders = new HashMap<>();

	private final Logger logger;

	@Inject
	public ContainerDescriptorLoaderServiceImpl(final LoggerAdapterFactory factory) {
		logger = factory.create(getClass());
	}

	@Override
	public Collection<ContainerDescriptor> extractDescriptors(final Class<?> testClass)
		throws ContainerDescriptorLoadException {
		return stream(testClass.getAnnotations()).filter(this::supported)
			.map(wrap(this::load, TestManagementException::new))
			.flatMap(c -> c.stream())
			.collect(Collectors.toList());
	}

	@PostConstruct
	public void init() {
		StreamSupport.stream(ServiceLoader.load(ContainerDescriptorLoader.class)
			.spliterator(), false)
			.peek(loader -> logger.debug("Loaded: " + loader.getTitle()))
			.forEach(loader -> loaders.put(loader.getAnnotationType(), loader));

		loaders.keySet()
			.stream()
			.filter(c -> !c.isAnnotationPresent(ContainerDescriptorLoaderSource.class))
			.forEach(this::failBadContainerDescriptorLoader);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ContainerDescriptor> load(final Annotation annotation) throws ContainerDescriptorLoadException {
		requireNonNull(annotation, "annotation shall not be null");

		final ContainerDescriptorLoader<Annotation> loader =
			(ContainerDescriptorLoader<Annotation>) loaders.get(annotation.annotationType());
		final Collection<ContainerDescriptor> descriptors = loader.load(annotation);

		return descriptors;
	}

	private void failBadContainerDescriptorLoader(final Class<?> clazz) {
		throw new TestManagementException(
			String.format(
				"ContainerDescriptorLoader %s present in classpath that is not marked with ContainerDescriptorLoaderSource annotation.",
				clazz.getName()));
	}

	private boolean supported(final Annotation annotation) {
		return loaders.containsKey(annotation.annotationType());
	}
}
