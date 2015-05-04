package cool.arch.whaleunit.runtime.impl;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.api.ContainerStatus;
import cool.arch.whaleunit.api.WhaleUnitContext;

@Service
public class WhaleUnitContextImpl implements WhaleUnitContext {

	@Override
	public ContainerStatus onContainer(String containerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
