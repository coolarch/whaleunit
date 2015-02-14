package cool.arch.whaleunit.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContextTracker implements LifeCycle {
	
	private final Set<String> globallyDirtiedContainerNames = new HashSet<>();

	@Override
	public void onInit(Object test, String... dirtiedContainers) {
		if (dirtiedContainers != null) {
			globallyDirtiedContainerNames.addAll(Arrays.asList(dirtiedContainers));
		}
		
		System.out.println("onInit");
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
			System.out.println("stopping container: " + containerName);
		}
		
		for (final String containerName : dirtyContainerNames) {
			System.out.println("start container: " + containerName);
		}
		
		System.out.println("onTestEnd");
	}

	@Override
	public void onCleanup() {
		System.out.println("onCleanup");
	}
}
