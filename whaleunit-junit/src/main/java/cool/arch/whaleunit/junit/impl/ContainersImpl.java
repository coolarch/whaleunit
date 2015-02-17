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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.annotation.LoggerAdapter;
import cool.arch.whaleunit.junit.api.Container;
import cool.arch.whaleunit.junit.api.Containers;

@Service
public class ContainersImpl implements Containers {
	
	private final Map<String, Container> containers = new HashMap<>();
	
	private LoggerAdapter loggerAdapter;
	
	public void add(final Container container) {
		requireNonNull(container, "container shall not be null");
		containers.put(container.getName(), container);
	}
	
	public void startAll() {
		for (final Container container : containers.values()) {
			container.start();
		}
	}
	
	public void stopAll() {
		for (final Container container : containers.values()) {
			container.stop();
		}
	}
	
	public void destroyAll() {
		for (final Container container : containers.values()) {
			container.destroy();
		}
	}
	
	public void stop(final String... names) {
		loggerAdapter.debug(Arrays.toString(names));
		
		// TODO - Implement
		
	}
	
	public void stop(final Collection<String> names) {
		loggerAdapter.debug(names.toString());
		
		// TODO - Implement
		
	}
	
	public LoggerAdapter getLoggerAdapter() {
		return loggerAdapter;
	}
	
	public void setLoggerAdapter(final LoggerAdapter loggerAdapter) {
		this.loggerAdapter = loggerAdapter;
	}
}
