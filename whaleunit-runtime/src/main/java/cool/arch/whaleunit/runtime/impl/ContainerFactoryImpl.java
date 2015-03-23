package cool.arch.whaleunit.runtime.impl;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;

import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.api.model.ContainerDescriptor;
import cool.arch.whaleunit.runtime.api.Container;
import cool.arch.whaleunit.runtime.api.ContainerFactory;
import cool.arch.whaleunit.runtime.api.SimpleExecutorService;
import cool.arch.whaleunit.runtime.api.UniqueIdService;

/*
 * #%L WhaleUnit - JUnit %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

@Service
public class ContainerFactoryImpl implements ContainerFactory {

	private final DockerClient docker;

	private final LoggerAdapterFactory factory;

	private final Logger logger;

	private final SimpleExecutorService simpleExecutorService;

	private final UniqueIdService uniqueIdService;

	@Inject
	ContainerFactoryImpl(final LoggerAdapterFactory factory, final UniqueIdService uniqueIdService,
		final SimpleExecutorService simpleExecutorService) throws DockerCertificateException {
		this.factory = factory;
		this.uniqueIdService = uniqueIdService;
		this.simpleExecutorService = simpleExecutorService;
		logger = factory.create(getClass());
		docker = DefaultDockerClient.fromEnv()
			.build();
	}

	@Override
	public Container apply(final ContainerDescriptor descriptor) {
		return new ContainerImpl(factory, descriptor, uniqueIdService, docker, simpleExecutorService);
	}
}
