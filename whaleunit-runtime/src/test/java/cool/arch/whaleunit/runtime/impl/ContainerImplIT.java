/**
 * 
 */
package cool.arch.whaleunit.runtime.impl;

import org.junit.Test;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;

import cool.arch.whaleunit.annotation.DefaultLoggerAdapterFactory;
import cool.arch.whaleunit.api.model.ContainerDescriptor;
import cool.arch.whaleunit.runtime.api.Container;

/**
 * 
 */
public class ContainerImplIT {

	/**
	 * Test method for {@link cool.arch.whaleunit.runtime.impl.ContainerImpl#start()}.
	 * @throws DockerCertificateException 
	 */
	@Test
	public final void testStart() throws DockerCertificateException {
		final ContainerDescriptor containerDescriptor = ContainerDescriptor.builder()
			.withId("foo")
			.withImage("ubuntu:14.04")
			.withCommand("/bin/bash")
			.build()
			.get();
		
		final DockerClient dockerClient = DefaultDockerClient.fromEnv()
			.build();

		final Container container = new ContainerImpl(new DefaultLoggerAdapterFactory(), containerDescriptor, new UniqueIdServiceImpl(new TemporalServiceImpl()), dockerClient, new SimpleExecutorServiceImpl());
		
		
		container.create();
		container.start();
		container.restart();
		container.stop();
		container.destroy();
	}
}
