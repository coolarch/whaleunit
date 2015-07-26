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
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * 
 */
public class VolumesFromsTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.VolumesFroms#builder()} for single volumesFromsFrom.
	 */
	@Test
	public final void testBuilderWithVolumesFrom() {
		final VolumesFrom volumesFrom = new VolumesFrom();

		final VolumesFroms result = VolumesFroms.builder()
			.with(volumesFrom)
			.build()
			.get();

		assertNotNull(result);
		assertSame(volumesFrom, result.iterator()
			.next());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.api.model.VolumesFroms#builder()} for iterable of volumesFrom.
	 */
	@Test
	public final void testBuilderWithIterable() {
		final VolumesFrom volumesFrom = new VolumesFrom();

		final List<VolumesFrom> volumesFroms = new ArrayList<>();
		volumesFroms.add(volumesFrom);

		final VolumesFroms result = VolumesFroms.builder()
			.with(volumesFroms)
			.build()
			.get();

		assertNotNull(result);
		assertSame(volumesFrom, result.iterator()
			.next());

		result.stream()
			.collect(Collectors.toList())
			.size();
	}

}
