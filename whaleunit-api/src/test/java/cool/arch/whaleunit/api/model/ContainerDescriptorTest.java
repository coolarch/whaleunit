/**
 * 
 */
package cool.arch.whaleunit.api.model;

/*
 * #%L WhaleUnit - API %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 */
public class ContainerDescriptorTest extends ContainerDescriptorSpec {

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#builder()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Deprecated
	public final void testBuilderAllPresent() throws Exception {
		final ContainerDescriptor container = ContainerDescriptor.builder()
			.withId("foo")
			.withCommand(new ArrayList<String>() {
				{
					add("/bin/bash");
				}
			})
			.withDnses(Dnses.builder()
				.build())
			.withDomainName("arch.cool")
			.withEntryPoint("/bin/sh")
			.withEnvironments(Environments.builder()
				.build())
			.withExposes(Exposes.builder()
				.build())
			.withHostname("whaleunit.arch.cool")
			.build();

		assertNotNull(container);

		assertEquals("[/bin/bash]", container.getCommand()
			.get()
			.toString());
		assertTrue(container.getDnses()
			.isPresent());
		assertEquals("arch.cool", container.getDomainName()
			.get());
		assertEquals("/bin/sh", container.getEntryPoint()
			.get());
		assertTrue(container.getEnvironments()
			.isPresent());
		assertTrue(container.getExposes()
			.isPresent());
		assertEquals("whaleunit.arch.cool", container.getHostname()
			.get());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getCommand()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testGetCommand() throws Exception {
		given().aBuilder()
			.when()
			.anIdIsDefined("foo")
			.aCommandIsDefined("/bin/bash")
			.theContainerIsBuilt()
			.then()
			.theCommandIs("[/bin/bash]")
			.noExceptionsThrown();
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getDnses()}.
	 */
	@Test
	@Ignore
	public final void testGetDnses() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getDomainName()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testGetDomainName() throws Exception {
		given().aBuilder()
			.when()
			.anIdIsDefined("foo")
			.aDomainNameIsDefined("arch.cool")
			.theContainerIsBuilt()
			.then()
			.theDomainNameIs("arch.cool")
			.noExceptionsThrown();
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getEntryPoint()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testGetEntryPoint() throws Exception {
		given().aBuilder()
			.when()
			.anIdIsDefined("foo")
			.anEntryPointIsDefined("/bin/bash")
			.theContainerIsBuilt()
			.then()
			.theEntryPointIs("/bin/bash")
			.noExceptionsThrown();
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getEnvironments()}.
	 */
	@Test
	@Ignore
	public final void testGetEnvironments() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getExposes()}.
	 */
	@Test
	@Ignore
	public final void testGetExposes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getHostname()}.
	 */
	@Test
	@Ignore
	public final void testGetHostname() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getImage()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testGetImage() throws Exception {
		given().aBuilder()
			.when()
			.anIdIsDefined("foo")
			.anImageIsDefined("bar")
			.aDomainNameIsDefined("arch.cool")
			.theContainerIsBuilt()
			.then()
			.theImageIs("bar")
			.noExceptionsThrown();
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getLinks()}.
	 */
	@Test
	@Ignore
	public final void testGetLinks() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getMemoryLimitMegs()}.
	 */
	@Test
	@Ignore
	public final void testGetMemoryLimitMegs() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getNet()}.
	 */
	@Test
	@Ignore
	public final void testGetNet() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getNetContainer()}.
	 */
	@Test
	@Ignore
	public final void testGetNetContainer() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getPorts()}.
	 */
	@Test
	@Ignore
	public final void testGetPorts() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getUser()}.
	 */
	@Test
	@Ignore
	public final void testGetUser() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getVolumes()}.
	 */
	@Test
	@Ignore
	public final void testGetVolumes() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getVolumesFrom()}.
	 */
	@Test
	@Ignore
	public final void testGetVolumesFrom() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link cool.arch.whaleunit.api.model.ContainerDescriptor#getWorkingDirectory()}.
	 */
	@Test
	@Ignore
	public final void testGetWorkingDirectory() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.ContainerDescriptor#isPrivileged()}
	 * .
	 */
	@Test
	@Ignore
	public final void testIsPrivileged() {
		fail("Not yet implemented"); // TODO
	}
}
