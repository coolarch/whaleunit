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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.annotation.WhaleUnit;
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
public class WhaleUnitRuleIT {

	@Rule
	@ClassRule
	public static final WhaleUnitRule whaleUnitRule = new WhaleUnitRule();

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#testExecution()}.
	 */
	@Test
	@DirtiesContainers("foo")
	public void testOne() {
		System.out.println("one start");

		try {
			Thread.sleep(15000);
		} catch (final InterruptedException e) {
			// Intentionally do nothing
		}

		System.out.println("one end");
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#testExecution()}.
	 */
	@Test
	public void testTwo() {
		System.out.println("two start");

		try {
			Thread.sleep(15000);
		} catch (final InterruptedException e) {
			// Intentionally do nothing
		}

		System.out.println("two end");
	}

	public static class DescriptorSupplier implements Supplier<Collection<ContainerDescriptor>> {

		@Override
		public Collection<ContainerDescriptor> get() {
			final List<ContainerDescriptor> descriptors = new LinkedList<>();

			ContainerDescriptor.builder()
				.withId("foo")
				.withImage("ubuntu:14.04")
				.withCommand("/bin/ls")
				.build()
				.ifPresent(descriptors::add);

			return descriptors;
		}
	}
}
