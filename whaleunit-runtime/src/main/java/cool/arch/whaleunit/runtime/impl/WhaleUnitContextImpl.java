package cool.arch.whaleunit.runtime.impl;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.api.ContainerStatus;
import cool.arch.whaleunit.api.WhaleUnitContext;
import cool.arch.whaleunit.runtime.api.Containers;

@Service
public class WhaleUnitContextImpl implements WhaleUnitContext {
	
	private final Containers containers;

	@Inject
	public WhaleUnitContextImpl(final Containers containers) {
		this.containers = containers;
	}

	@Override
	public ContainerStatus onContainer(String containerId) {
		return containers.lookup(containerId);
	}
}
