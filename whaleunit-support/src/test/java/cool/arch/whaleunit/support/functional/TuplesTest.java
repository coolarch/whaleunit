/**
 * 
 */
package cool.arch.whaleunit.support.functional;

/*
 * #%L WhaleUnit - Support %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
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

import org.junit.Test;

/**
 * 
 */
public class TuplesTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.support.functional.Tuples#of(java.lang.Object)}.
	 */
	@Test
	public final void testOfV0() {
		final Tuple1<String> result = Tuples.of("foo");

		assertEquals("foo", result.item0());
	}

	/**
	 * Test method for {@link cool.arch.whaleunit.support.functional.Tuples#of(java.lang.Object, java.lang.Object)}.
	 */
	@Test
	public final void testOfV0V1() {
		final Tuple2<String, String> result = Tuples.of("foo", "bar");

		assertEquals("foo", result.item0());
		assertEquals("bar", result.item1());
	}

}
