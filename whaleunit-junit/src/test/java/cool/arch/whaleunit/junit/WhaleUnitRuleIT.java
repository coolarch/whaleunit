/**
 *
 */
package cool.arch.whaleunit.junit;

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

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;

import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.annotation.ContainerStartedPredicate;
import cool.arch.whaleunit.annotation.WhaleUnit;
import cool.arch.whaleunit.api.WhaleUnitContext;
import cool.arch.whaleunit.api.model.ContainerDescriptor;
import cool.arch.whaleunit.loader.programmatic.ProgrammaticContainerDescriptors;

/**
 *
 */
@DirtiesContainers({
	"foo"
})
@WhaleUnit
@ProgrammaticContainerDescriptors(
	sources = {
		WhaleUnitRuleIT.DescriptorSupplier.class
	})
@RunWith(WhaleUnitJUnit4ClassRunner.class)
public class WhaleUnitRuleIT {

	private WhaleUnitContext context;

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#testExecution()}.
	 * @throws IOException 
	 */
	@Test
	public void testOne() throws IOException {
		System.out.println("one start");
		assertNotNull(context);

		final String url = "http://" + context.onContainer("foo")
			.getHostname() + ":" + context.onContainer("foo")
			.externalTcpPortFor(80)
			.orElse(80);
		final URL urlConnection = new URL(url);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.openConnection()
			.getInputStream()));

		System.out.println(url);

		reader.lines()
			.forEach(System.out::println);

		System.out.println("one end");
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#testExecution()}.
	 */
	@Test
	public void testTwo() {
		System.out.println("two start");

		assertNotNull(context);
		context.onContainer("foo")
			.externalTcpPortFor(80)
			.ifPresent(System.out::println);

		System.out.println("two end");
	}

	@Test
	@DirtiesContainers("bar")
	public void testThree() {
		System.out.println("three start");
		System.out.println("three end");
	}

	@ContainerStartedPredicate("foo")
	public boolean isFooStarted() {
		return true;
	}

	@ContainerStartedPredicate("bar")
	public boolean isBarStarted() {
		return true;
	}

	public static class DescriptorSupplier implements Supplier<Collection<ContainerDescriptor>> {

		@Override
		public Collection<ContainerDescriptor> get() {
			final List<ContainerDescriptor> descriptors = new LinkedList<>();

			final ContainerDescriptor descriptor0 = ContainerDescriptor.builder()
				.withId("foo")
				.withImage("tutum/apache-php")
				.build();

			if (descriptor0 != null) {
				descriptors.add(descriptor0);
			}

			final ContainerDescriptor descriptor1 = ContainerDescriptor.builder()
				.withId("bar")
				.withImage("ubuntu:14.04")
				.withCommand(Lists.newArrayList("/bin/sleep", "10"))
				.build();

			if (descriptor1 != null) {
				descriptors.add(descriptor1);
			}

			return descriptors;
		}
	}
}
