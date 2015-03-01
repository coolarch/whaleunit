package cool.arch.whaleunit.runtime.impl;

/*
 * #%L
 * WhaleUnit - JUnit
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

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.annotation.WhaleUnit;
import cool.arch.whaleunit.api.ContainerDescriptorLoader;
import cool.arch.whaleunit.runtime.api.Container;
import cool.arch.whaleunit.runtime.api.ContainerFactory;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.api.ContextTracker;
import cool.arch.whaleunit.runtime.api.DelegatingLoggerAdapterFactory;
import cool.arch.whaleunit.runtime.exception.InitializationException;
import cool.arch.whaleunit.runtime.exception.TestManagementException;
import cool.arch.whaleunit.runtime.exception.ValidationException;

@Service
public class ContextTrackerImpl implements ContextTracker {
	
	private static final String MISSING_WHALEUNIT_ANNOTATION_TMPL = "Annotation %s is required on unit test %s that is using WhaleUnit.";
	
	private final Set<String> globallyDirtiedContainerNames = new HashSet<>();
	
	private final ContainerFactory containerFactory;
	
	private final DelegatingLoggerAdapterFactory delegatingLoggerAdapterFactory;

	private final Containers containers;
	
	private Class<?> testClass;
	
	private LoggerAdapterFactory loggerAdapterFactory;
	
	private Logger log;

	@Inject
	public ContextTrackerImpl(final ContainerFactory containerFactory, final Containers containers,
		final DelegatingLoggerAdapterFactory delegatingLoggerAdapterFactory) {
		requireNonNull(containerFactory, "containerFactory shall not be null");
		this.containerFactory = containerFactory;
		this.containers = containers;
		this.delegatingLoggerAdapterFactory = delegatingLoggerAdapterFactory;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onInit(final Class<?> testClass) {
		this.testClass = testClass;
		
		Optional.of((Class) testClass)
			.map(tc -> tc.getAnnotation(DirtiesContainers.class))
			.map(annotation -> ((DirtiesContainers) annotation).value())
			.map(Arrays::asList)
			.ifPresent(globallyDirtiedContainerNames::addAll);
		
		preInit();
		validate();
		init();
	}
	
	@Override
	public void onTestStart(final String methodName) {
		containers.startAll();
	}
	
	@Override
	public void onTestSucceeded(final String methodName) {
		// Intentionally do nothing
	}
	
	@Override
	public void onTestFailed(final String methodName) {
		containers.stopAll();
	}
	
	@Override
	public void onTestEnd(final String methodName) {
		containers.stop(globallyDirtiedContainerNames);

		Optional.of(methodName)
			.map(name -> {
				try {
					return testClass.getMethod(name);
				} catch (NoSuchMethodException | SecurityException e) {
					throw new TestManagementException("Error looking up method " + methodName);
				}
			})
			.map(method -> method.getAnnotation(DirtiesContainers.class))
			.map(annotation -> annotation.value())
			.ifPresent(containers::stop);
	}
	
	@Override
	public void onCleanup() {
		containers.stopAll();
		containers.destroyAll();
	}
	
	@SuppressWarnings("rawtypes")
	private void preInit() {
		final WhaleUnit annotation = testClass.getAnnotation(WhaleUnit.class);
		
		if (annotation == null) {
			throw new ValidationException(String.format(MISSING_WHALEUNIT_ANNOTATION_TMPL, WhaleUnit.class.getName(), testClass.getName()));
		}
		
		try {
			loggerAdapterFactory = annotation.loggerAdapterFactory().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InitializationException("Error initializing LoggerAdapter", e);
		}
		
		delegatingLoggerAdapterFactory.setLoggerAdapterFactory(loggerAdapterFactory);

		log = loggerAdapterFactory.create(getClass());

		final ServiceLoader<ContainerDescriptorLoader> loadersIterable = ServiceLoader.load(ContainerDescriptorLoader.class);
		
		final Map<Class<?>, ContainerDescriptorLoader> loaders = new HashMap<>();
		
		for (final ContainerDescriptorLoader loader : loadersIterable) {
			getLog().debug("Loaded: " + loader.getTitle());
			loaders.put(loader.getAnnotationType(), loader);
		}
	}
	
	private void init() {
		addContainer("foo");
		addContainer("bar");
		addContainer("bat");
		addContainer("baz");
	}
	
	private void validate() {
		final WhaleUnit annotation = testClass.getAnnotation(WhaleUnit.class);
		
		annotation.config();
		
		// TODO - Implement
	}
	
	private void addContainer(final String name) {
		getLog().debug("Registering container " + name);
		
		final Container container = containerFactory.apply(name);
		containers.add(container);
	}
	
	public Logger getLog() {
		return log;
	}
}
