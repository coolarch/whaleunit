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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class PortsTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.Ports#builder()} for single port.
	 */
	@Test
	public final void testBuilderWithPort() {
		final Port port = new Port();

		final Ports result = Ports.builder()
			.with(port)
			.build();

		assertNotNull(result);
		assertSame(port, result.iterator()
			.next());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.Ports#builder()} for iterable of port.
	 */
	@Test
	public final void testBuilderWithIterable() {
		final Port port = new Port();

		final List<Port> ports = new ArrayList<>();
		ports.add(port);

		final Ports result = Ports.builder()
			.with(ports)
			.build();

		assertNotNull(result);
		assertSame(port, result.iterator()
			.next());
	}

}
