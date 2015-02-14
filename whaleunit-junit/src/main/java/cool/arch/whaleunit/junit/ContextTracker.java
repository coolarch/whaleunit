package cool.arch.whaleunit.junit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cool.arch.whaleunit.junit.docker.Container;
import cool.arch.whaleunit.junit.docker.ContainerImpl;

public class ContextTracker implements LifeCycle {
	
	private final Set<String> globallyDirtiedContainerNames = new HashSet<>();
	
	private Object test;
	
	private Map<String, Container> containers = new HashMap<>();
	
	@Override
	public void onInit(Object test, String... dirtiedContainers) {
		this.test = test;
		
		if (dirtiedContainers != null) {
			globallyDirtiedContainerNames.addAll(Arrays.asList(dirtiedContainers));
		}
		
		init();
		validate();
	}

	@Override
	public void onTestStart() {
		System.out.println("onTestStart");
	}

	@Override
	public void onTestSucceeded() {
		System.out.println("onTestSucceeded");
	}

	@Override
	public void onTestFailed() {
		System.out.println("onTestFailed");
	}

	@Override
	public void onTestEnd(String... dirtiedContainers) {
		final Set<String> dirtyContainerNames = new HashSet<>();
		
		dirtyContainerNames.addAll(globallyDirtiedContainerNames);
		
		if (dirtiedContainers != null && dirtiedContainers.length > 0) {
			dirtyContainerNames.addAll(Arrays.asList(dirtiedContainers));
		}
		
		for (final String containerName : dirtyContainerNames) {
			final Container container = containers.get(containerName);
			container.stop();
		}
		
		for (final String containerName : dirtyContainerNames) {
			final Container container = containers.get(containerName);
			container.start();
		}
	}

	@Override
	public void onCleanup() {
		for (final Container container : containers.values()) {
			container.stop();
		}

		for (final Container container : containers.values()) {
			container.destroy();
		}
	}
	
	private void init() {
		addContainer("foo");
		addContainer("bar");
		addContainer("bat");
		addContainer("baz");
	}
	
	private void validate() {
		
	}
	
	private void addContainer(final String name) {
		final Container container = new ContainerImpl(name);
		containers.put(name, container);
	}
}
