package cool.arch.whaleunit.support.validation;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/*
 * #%L WhaleUnit - Support %% Copyright (C) 2015 - 2016 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

public class SequentialValidatorTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.support.validation.SequentialValidator#validate(java.lang.Object, java.util.stream.Collector)}.
	 */
	@Test
	public void testValidate() {
		final SequentialValidator<String> validator = SequentialValidator.<String> builder()
			.addValidator((model, errors) -> {
				errors.accept("first error for " + model);

				return true;
			})
			.addValidator((model, errors) -> {
				errors.accept("second error for " + model);

				return false;
			})
			.addValidator((model, errors) -> {
				errors.accept("third error for " + model);

				return true;
			})
			.build();

		final List<String> errors = validator.validate("foo").collect(toList());

		assertEquals(2, errors.size());
		assertEquals("first error for foo", errors.get(0));
		assertEquals("second error for foo", errors.get(1));
	}
}
