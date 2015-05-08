package cool.arch.whaleunit.api;

import java.util.Optional;

public interface ContainerStatus {

	Optional<Integer> externalTcpPortFor(int internalPort);

	String getHostname();

	boolean isRunning();

}
