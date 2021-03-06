package cool.arch.whaleunit.runtime.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Optional;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerConfig.Builder;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;

import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.api.model.ContainerDescriptor;
import cool.arch.whaleunit.runtime.api.Container;
import cool.arch.whaleunit.runtime.api.SimpleExecutorService;
import cool.arch.whaleunit.runtime.api.UniqueIdService;
import cool.arch.whaleunit.runtime.enumeration.ContainerState;

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

/**
 * 
 */
public class ContainerImpl implements Container {

	private ContainerCreation creation;

	private final ContainerDescriptor descriptor;

	private final DockerClient docker;

	private final SimpleExecutorService executorService;

	private final String id;

	private Logger logger;

	private final String name;

	private String runId;

	private ContainerState state = ContainerState.NEW;

	/**
	 * @param logger 
	 * @param factory
	 * @param descriptor
	 * @param uniqueIdService
	 * @param docker
	 * @param executorService
	 */
	public ContainerImpl(final Logger logger, final ContainerDescriptor descriptor,
		final UniqueIdService uniqueIdService, final DockerClient docker, final SimpleExecutorService executorService) {
		this.descriptor = requireNonNull(descriptor, "descriptor shall not be null");
		this.executorService = requireNonNull(executorService, "executorService shall not be null");
		final String uniqueId = uniqueIdService.getUniqueId();
		this.logger = logger;
		id = descriptor.getId()
			.get();
		name = "whaleunit_" + uniqueId + "_" + id;
		this.docker = requireNonNull(docker, "docker shall not be null");
	}

	@Override
	public void create() {
		if (!ContainerState.NEW.equals(state)) {
			return;
		}

		state = ContainerState.CREATING;

		logger.info("create: " + name);

		//		final String[] ports = descriptor.getPorts()
		//			.map(v -> new String[] {}) // TODO - Extract ports here from descriptor
		//			.orElse(new String[] {"80", "22"});

		descriptor.getImage()
			.ifPresent(image -> {
				try {
					docker.pull(image);
				} catch (final Exception e) {
					throw new TestManagementException(String.format("Error pulling image %s", image), e);
				}

			});

		// for(String port : ports) {
		// List<PortBinding> hostPorts = new ArrayList<>();
		// hostPorts.add(PortBinding.of("0.0.0.0", port));
		// portBindings.put(port, hostPorts);
		// }
		final HostConfig hostConfig = HostConfig.builder()
			.publishAllPorts(TRUE)
			.build();

		final Builder builder = ContainerConfig.builder();

		descriptor.getImage()
			.ifPresent(builder::image);
		descriptor.getCommand()
			.ifPresent(builder::cmd);
		builder.attachStdout(FALSE);
		builder.tty(FALSE);
		builder.hostConfig(hostConfig);

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
			docker.removeContainer(name, true);
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

		stop();
		start();
	}

	@Override
	public void start() {
		if (ContainerState.STARTED.equals(state) && isRunning()) {
			return;
		}

		state = ContainerState.STARTED;

		runId = creation.id();

		try {
			final PipedInputStream stdout = new PipedInputStream();
			final PipedInputStream stderr = new PipedInputStream();
			final PipedOutputStream stdoutPipe = new PipedOutputStream(stdout);
			final PipedOutputStream stderrPipe = new PipedOutputStream(stderr);

			docker.startContainer(runId);

			Thread.sleep(50);

			if (isRunning()) {
				//				docker.attachContainer(runId, LOGS, STDOUT, STDERR, STREAM)
				//					.attach(stdoutPipe, stderrPipe);
				//
				//				executorService.submit(() -> {
				//					try (Scanner sc_stdout = new Scanner(stdout); Scanner sc_stderr = new Scanner(stderr)) {
				//						sc_stdout.forEachRemaining(line -> logger.info(String.format("[%s] [STDOUT] %s", name, line)));
				//					} catch (Exception e) {
				//						logger.error("Error reading input", e);
				//					}
				//				});
			} else {
				logger.error("Container no longer running");
			}

		} catch (final DockerException e) {
			logger.error("Error starting container", e);

			throw new TestManagementException(e);
		} catch (final InterruptedException e) {
			logger.error("Error starting container", e);

			throw new TestManagementException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("boxing")
	public boolean isRunning() {
		boolean running = false;

		try {
			running = docker.inspectContainer(runId)
				.state()
				.running();
		} catch (DockerException | InterruptedException e) {
			logger.error("Error checking container state", e);
		}

		return running;
	}

	@Override
	public void stop() {
		if (ContainerState.STOPPED.equals(state)) {
			return;
		}

		state = ContainerState.STOPPED;
		logger.info("stop: " + name);

		try {
			docker.stopContainer(name, 30);
		} catch (final DockerException | InterruptedException e) {
			throw new TestManagementException(e);
		}
	}

	@Override
	public Optional<Integer> externalTcpPortFor(final int internalPort) {
		return Optional.of(docker)
			.map(docker -> {
				try {
					return docker.inspectContainer(runId);
				} catch (Exception e) {
					throw new TestManagementException("Error looking up port information", e);
				}
			})
			.map(container -> container.networkSettings())
			.map(networkSettings -> networkSettings.ports())
			.map(ports -> ports.get(internalPort + "/tcp"))
			.map(bindings -> bindings.get(0))
			.map(binding -> binding.hostPort())
			.map(Integer::parseInt);
	}

	@Override
	public String getHostname() {
		return docker.getHost();
	}
}
