package cool.arch.whaleunit.api;

import java.util.Optional;

public interface ContainerStatus {
	
	Optional<Integer> externalPortFor(int internalPort);
	
	String getHostname();
	
	boolean isRunning();
	
	
	
	

}
