package cool.arch.whaleunit.junit.impl;

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
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.annotation.LoggerAdapter;
import cool.arch.whaleunit.annotation.WhaleUnit;
import cool.arch.whaleunit.junit.api.Container;
import cool.arch.whaleunit.junit.api.ContainerFactory;
import cool.arch.whaleunit.junit.api.Containers;
import cool.arch.whaleunit.junit.api.ContextTracker;
import cool.arch.whaleunit.junit.exception.InitializationException;
import cool.arch.whaleunit.junit.exception.ValidationException;

@Service
public class ContextTrackerImpl implements ContextTracker {
	
	private static final String MISSING_WHALEUNIT_ANNOTATION_TMPL = "Annotation %s is required on unit test %s that is using WhaleUnit.";
	
	private final Set<String> globallyDirtiedContainerNames = new HashSet<>();
	
	private final ContainerFactory containerFactory;
	
	private final Containers containers;
	
	private Class<?> testClass;
	
	private LoggerAdapter loggerAdapter;
	
	@Inject
	public ContextTrackerImpl(final ContainerFactory containerFactory, final Containers containers) {
		requireNonNull(containerFactory, "containerFactory shall not be null");
		this.containerFactory = containerFactory;
		this.containers = containers;
	}
	
	@Override
	public void onInit(final Class<?> testClass, final String... dirtiedContainers) {
		this.testClass = testClass;
		
		if (dirtiedContainers != null) {
			globallyDirtiedContainerNames.addAll(Arrays.asList(dirtiedContainers));
		}
		
		validate();
		init();
	}
	
	@Override
	public void onTestStart() {
		containers.startAll();
	}
	
	@Override
	public void onTestSucceeded() {
		// Intentionally do nothing
	}
	
	@Override
	public void onTestFailed() {
		containers.stopAll();
	}
	
	@Override
	public void onTestEnd(final String... dirtiedContainers) {
		containers.stop(globallyDirtiedContainerNames);
		containers.stop(dirtiedContainers);
	}
	
	@Override
	public void onCleanup() {
		containers.stopAll();
		containers.destroyAll();
	}
	
	private void init() {
		final WhaleUnit whaleUnit = testClass.getAnnotation(WhaleUnit.class);
		
		try {
			loggerAdapter = whaleUnit.loggerAdapter().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InitializationException("Error initializing LoggerAdapter", e);
		}
		
		containers.setLoggerAdapter(loggerAdapter);
		
		addContainer("foo");
		addContainer("bar");
		addContainer("bat");
		addContainer("baz");
	}
	
	private void validate() {
		final WhaleUnit annotation = testClass.getAnnotation(WhaleUnit.class);
		
		if (annotation == null) {
			throw new ValidationException(String.format(MISSING_WHALEUNIT_ANNOTATION_TMPL, WhaleUnit.class.getName(), testClass.getName()));
		}
		
		annotation.config();
		
	}
	
	private void addContainer(final String name) {
		loggerAdapter.debug("Registering container " + name);
		
		final Container container = containerFactory.apply(name);
		containers.add(container);
	}
}
