package cool.arch.whaleunit.api.model;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import cool.arch.whaleunit.support.patterns.AbstractBuilder;
import cool.arch.whaleunit.support.patterns.AbstractBuilderImpl;

/*
 * #%L
 * WhaleUnit - API
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

public final class ContainerDescriptor {
	
	private Optional<String> command = Optional.empty();

	private Optional<Dnses> dnses = Optional.empty();

	private Optional<String> domainName = Optional.empty();
	
	private Optional<String> entryPoint = Optional.empty();
	
	private Optional<Environments> environments = Optional.empty();
	
	private Optional<Exposes> exposes = Optional.empty();
	
	private Optional<String> hostname = Optional.empty();
	
	private Optional<String> id = Optional.empty();
	
	private Optional<String> image = Optional.empty();
	
	private Optional<Links> links = Optional.empty();
	
	private int memoryLimitMegs;
	
	private Optional<Net> net;
	
	private Optional<String> netContainer = Optional.empty();
	
	private Optional<Ports> ports = Optional.empty();
	
	private boolean privileged;
	
	private Optional<String> user = Optional.empty();
	
	private Optional<Volumes> volumes = Optional.empty();
	
	private Optional<VolumesFroms> volumesFroms = Optional.empty();
	
	private Optional<Path> workingDirectory = Optional.empty();
	
	private ContainerDescriptor() {}
	
	public Optional<String> getCommand() {
		return command;
	}

	public Optional<Dnses> getDnses() {
		return dnses;
	}
	
	public Optional<String> getDomainName() {
		return domainName;
	}
	
	public Optional<String> getEntryPoint() {
		return entryPoint;
	}
	
	public Optional<Environments> getEnvironments() {
		return environments;
	}
	
	public Optional<Exposes> getExposes() {
		return exposes;
	}
	
	public Optional<String> getHostname() {
		return hostname;
	}
	
	public Optional<String> getId() {
		return id;
	}
	
	public Optional<String> getImage() {
		return image;
	}
	
	public Optional<Links> getLinks() {
		return links;
	}
	
	public int getMemoryLimitMegs() {
		return memoryLimitMegs;
	}
	
	public Optional<Net> getNet() {
		return net;
	}
	
	public Optional<String> getNetContainer() {
		return netContainer;
	}
	
	public Optional<Ports> getPorts() {
		return ports;
	}
	
	public Optional<String> getUser() {
		return user;
	}
	
	public Optional<Volumes> getVolumes() {
		return volumes;
	}
	
	public Optional<VolumesFroms> getVolumesFrom() {
		return volumesFroms;
	}
	
	public Optional<Path> getWorkingDirectory() {
		return workingDirectory;
	}
	
	public boolean isPrivileged() {
		return privileged;
	}

	public static Builder builder() {
		return new BuilderImpl();
	}
	
	public interface Builder extends AbstractBuilder<ContainerDescriptor> {
		
		Builder withCommand(String command);

		Builder withDnses(Dnses dnses);
		
		Builder withDnses(Optional<Dnses> dnses);
		
		Builder withDomainName(String domainName);
		
		Builder withEntryPoint(String entryPoint);

		Builder withEnvironments(Environments environments);
		
		Builder withEnvironments(Optional<Environments> environments);

		Builder withExposes(Exposes exposes);
		
		Builder withExposes(Optional<Exposes> exposes);

		Builder withHostname(String hostname);
		
		Builder withId(String id);

		Builder withImage(String image);
		
		Builder withLinks(Links links);

		Builder withLinks(Optional<Links> links);
		
		Builder withMemoryLimitMegs(int memoryLimitMegs);

		Builder withNet(Net net);
		
		Builder withNetContainer(String netContainer);
		
		Builder withPorts(Optional<Ports> ports);
		
		Builder withPorts(Ports ports);

		Builder withPrivileged(boolean privileged);
		
		Builder withUser(String user);
		
		Builder withVolumes(Optional<Volumes> volumes);
		
		Builder withVolumes(Volumes volumes);
		
		Builder withVolumesFroms(Optional<VolumesFroms> volumesFroms);
		
		Builder withVolumesFroms(VolumesFroms volumesFroms);
		
		Builder withWorkingDirectory(Path workingDirectory);
	}
	
	static class BuilderImpl extends AbstractBuilderImpl<ContainerDescriptor> implements Builder {
		
		protected BuilderImpl() {
			super(new ContainerDescriptor());
		}
		
		@Override
		public Builder withCommand(final String command) {
			getInstance().command = Optional.ofNullable(command);
			
			return this;
		}
		
		@Override
		public Builder withDnses(final Dnses dnses) {
			getInstance().dnses = Optional.ofNullable(dnses);

			return this;
		}
		
		@Override
		public Builder withDnses(final Optional<Dnses> dnses) {
			getInstance().dnses = (dnses == null) ? Optional.empty() : dnses;

			return this;
		}
		
		@Override
		public Builder withDomainName(final String domainName) {
			getInstance().domainName = Optional.ofNullable(domainName);

			return this;
		}
		
		@Override
		public Builder withEntryPoint(final String entryPoint) {
			getInstance().entryPoint = Optional.ofNullable(entryPoint);

			return this;
		}
		
		@Override
		public Builder withEnvironments(final Environments environments) {
			getInstance().environments = Optional.ofNullable(environments);

			return this;
		}
		
		@Override
		public Builder withEnvironments(final Optional<Environments> environments) {
			getInstance().environments = (environments == null) ? Optional.empty() : environments;
			
			return this;
		}
		
		@Override
		public Builder withExposes(final Exposes exposes) {
			getInstance().exposes = Optional.ofNullable(exposes);

			return this;
		}
		
		@Override
		public Builder withExposes(final Optional<Exposes> exposes) {
			getInstance().exposes = (exposes == null) ? Optional.empty() : exposes;
			
			return this;
		}
		
		@Override
		public Builder withHostname(final String hostname) {
			getInstance().hostname = Optional.ofNullable(hostname);

			return this;
		}
		
		@Override
		public Builder withId(final String id) {
			getInstance().id = Optional.ofNullable(id);
			
			return this;
		}
		
		@Override
		public Builder withImage(final String image) {
			getInstance().image = Optional.of(image);
			
			return this;
		}
		
		@Override
		public Builder withLinks(final Links links) {
			getInstance().links = Optional.ofNullable(links);

			return this;
		}
		
		@Override
		public Builder withLinks(final Optional<Links> links) {
			getInstance().links = (links == null) ? Optional.empty() : links;
			
			return this;
		}
		
		@Override
		public Builder withMemoryLimitMegs(final int memoryLimitMegs) {
			getInstance().memoryLimitMegs = memoryLimitMegs;

			return this;
		}
		
		@Override
		public Builder withNet(final Net net) {
			getInstance().net = Optional.ofNullable(net);

			return this;
		}
		
		@Override
		public Builder withNetContainer(final String netContainer) {
			getInstance().netContainer = Optional.ofNullable(netContainer);

			return this;
		}
		
		@Override
		public Builder withPorts(final Optional<Ports> ports) {
			getInstance().ports = (ports == null) ? Optional.empty() : ports;

			return this;
		}
		
		@Override
		public Builder withPorts(final Ports ports) {
			getInstance().ports = Optional.ofNullable(ports);

			return this;
		}
		
		@Override
		public Builder withPrivileged(final boolean privileged) {
			getInstance().privileged = privileged;

			return this;
		}
		
		@Override
		public Builder withUser(final String user) {
			getInstance().user = Optional.ofNullable(user);

			return this;
		}
		
		@Override
		public Builder withVolumes(final Optional<Volumes> volumes) {
			getInstance().volumes = (volumes == null) ? Optional.empty() : volumes;
			
			return this;
		}
		
		@Override
		public Builder withVolumes(final Volumes volumes) {
			getInstance().volumes = Optional.ofNullable(volumes);

			return this;
		}
		
		@Override
		public Builder withVolumesFroms(final Optional<VolumesFroms> volumesFroms) {
			getInstance().volumesFroms = (volumesFroms == null) ? Optional.empty() : volumesFroms;
			
			return this;
		}
		
		@Override
		public Builder withVolumesFroms(final VolumesFroms volumesFroms) {
			getInstance().volumesFroms = Optional.ofNullable(volumesFroms);

			return this;
		}
		
		@Override
		public Builder withWorkingDirectory(final Path workingDirectory) {
			getInstance().workingDirectory = Optional.ofNullable(workingDirectory);

			return this;
		}
		
		@Override
		protected Set<String> validate(final ContainerDescriptor instance) {
			final Set<String> errors = new HashSet<>();
			
			if (!instance.id.isPresent()) {
				errors.add("id must be supplied");
			}
			
			if (!instance.id.map(String::trim).equals(instance.id)) {
				errors.add("id must not include whitespace");
			}
			
			if (!instance.id.map(String::trim).filter(id -> id.length() > 0).isPresent()) {
				errors.add("id must not include whitespace");
			}
			
			if (instance.id.filter(String::isEmpty).isPresent()) {
				errors.add("id must not be an empty string");
			}

			if (instance.image == null) {
				errors.add("image must be supplied");
			}

			return errors;
		}
	}
}
