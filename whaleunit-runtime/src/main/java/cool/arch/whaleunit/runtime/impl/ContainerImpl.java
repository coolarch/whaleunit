package cool.arch.whaleunit.runtime.impl;

import static java.util.Objects.requireNonNull;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.AttachParameter;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerConfig.Builder;
import com.spotify.docker.client.messages.ContainerCreation;

import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.api.model.ContainerDescriptor;
import cool.arch.whaleunit.runtime.api.Container;
import cool.arch.whaleunit.runtime.api.SimpleExecutorService;
import cool.arch.whaleunit.runtime.api.UniqueIdService;
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
	
	private ContainerCreation creation;
	
	private final ContainerDescriptor descriptor;
	
	private final DockerClient docker;
	
	private final SimpleExecutorService executorService;
	
	private final String id;
	
	private final Logger logger;
	
	private final String name;
	
	private String runId;
	
	private ContainerState state = ContainerState.NEW;
	
	public ContainerImpl(final LoggerAdapterFactory factory, final ContainerDescriptor descriptor, final UniqueIdService uniqueIdService,
		final DockerClient docker, final SimpleExecutorService executorService) {
		this.descriptor = requireNonNull(descriptor, "descriptor shall not be null");
		this.executorService = requireNonNull(executorService, "executorService shall not be null");
		final String uniqueId = uniqueIdService.getUniqueId();
		id = descriptor.getId().get();
		name = "whaleunit_" + uniqueId + "_" + id;
		logger = factory.create(getClass());
		this.docker = requireNonNull(docker, "docker shall not be null");
	}
	
	@Override
	public void create() {
		if (!ContainerState.NEW.equals(state)) {
			return;
		}
		
		state = ContainerState.CREATING;
		
		logger.info("create: " + name);
		
		final String[] ports = { "80", "22" };
		
		descriptor.getImage().ifPresent(image -> {
			try {
				docker.pull(image);
			}
			catch (final Exception e) {
				throw new TestManagementException(String.format("Error pulling image %s", image), e);
			}
			
		});
		
		final Builder builder = ContainerConfig.builder();
		
		descriptor.getImage().ifPresent(builder::image);
		builder.exposedPorts(ports);
		descriptor.getCommand().ifPresent(builder::cmd);
		builder.attachStdout(true);
		builder.stdinOnce(true);
		builder.tty(true);
		
		final ContainerConfig config = builder.build();
		
		try {
			creation = docker.createContainer(config, name);
		} catch (final InterruptedException | DockerException e) {
			state = ContainerState.FAILED;
			
			throw new TestManagementException(e);
		}
		
		state = ContainerState.CREATED;
	}
	
	@Override
	public void destroy() {
		if (ContainerState.DESTROYED.equals(state)) {
			return;
		}
		
		if (ContainerState.DESTROYING.equals(state)) {
			return;
		}
		
		state = ContainerState.DESTROYING;
		
		logger.info("destroy: " + name);
		
		try {
			docker.removeContainer(runId, true);
		} catch (final InterruptedException | DockerException e) {
			state = ContainerState.FAILED;
			
			throw new TestManagementException(e);
		}
		
		state = ContainerState.DESTROYED;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void restart() {
		if (ContainerState.RESTARTING.equals(state)) {
			return;
		}
		
		if (!ContainerState.STARTED.equals(state)) {
			return;
		}
		
	}
	
	@Override
	public void start() {
		if (ContainerState.STARTED.equals(state)) {
			return;
		}
		
		state = ContainerState.STARTED;
		
		logger.info("start: " + name);
		
		runId = creation.id();
		
		try {
			docker.startContainer(runId);
			
			executorService.submit(() -> {
				try {
					docker.attachContainer(runId, AttachParameter.LOGS, AttachParameter.STDOUT, AttachParameter.STDERR, AttachParameter.STREAM)
						.attach(System.out, System.err);
				} catch (final Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		})	;
		} catch (final DockerException e) {
			throw new TestManagementException(e);
		} catch (final InterruptedException e) {
			throw new TestManagementException(e);
		}
	}
	
	@Override
	public void stop() {
		if (ContainerState.STOPPED.equals(state)) {
			return;
		}
		
		state = ContainerState.STOPPED;
		
		logger.info("stop: " + name);
		
		try {
			docker.stopContainer(runId, 30);
		} catch (final DockerException e) {
			throw new TestManagementException(e);
		} catch (final InterruptedException e) {
			throw new TestManagementException(e);
		}
	}
}
