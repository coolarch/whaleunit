package cool.arch.whaleunit.runtime.impl;

import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.runtime.api.Container;
import cool.arch.whaleunit.runtime.enumeration.ContainerState;

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

public class ContainerImpl implements Container {
	
	private final String name;
	
	private ContainerState state = ContainerState.NEW;
	
	private final Logger logger;

	public ContainerImpl(final LoggerAdapterFactory factory, final String name) {
		this.name = name;
		logger = factory.create(getClass());
	}
	
	@Override
	public void create() {
		logger.info("create: " + name);
	}

	@Override
	public void start() {
		if (ContainerState.STARTED.equals(state)) {
			return;
		}
		
		state = ContainerState.STARTED;
		
		logger.info("start: " + name);
	}

	@Override
	public void stop() {
		if (ContainerState.STOPPED.equals(state)) {
			return;
		}
		
		state = ContainerState.STOPPED;
		
		logger.info("stop: " + name);
	}

	@Override
	public void destroy() {
		logger.info("destroy: " + name);
	}

	@Override
	public String getName() {
		return name;
	}
}
