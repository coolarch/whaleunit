package cool.arch.whaleunit.support.validation;

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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Stream;

import cool.arch.whaleunit.support.patterns.AbstractBuilder;
import cool.arch.whaleunit.support.patterns.AbstractBuilderImpl;

public final class SequentialValidator<T> {

	private final List<BiPredicate<T, Consumer<String>>> predicates = new LinkedList<>();

	public Stream<String> validate(final T model) {
		final List<String> errors = new LinkedList<>();

		final Iterator<BiPredicate<T, Consumer<String>>> iterator = predicates.iterator();

		boolean active = true;

		while (active && iterator.hasNext()) {
			active = iterator.next()
				.test(model, errors::add);
		}

		return errors.stream();
	}

	public static <T> Builder<T> builder() {
		return new BuilderImpl<>();
	}

	public interface Builder<T> extends AbstractBuilder<SequentialValidator<T>> {

		Builder<T> addValidator(BiPredicate<T, Consumer<String>> predicate);

	}

	static class BuilderImpl<T> extends AbstractBuilderImpl<SequentialValidator<T>> implements Builder<T> {

		protected BuilderImpl() {
			super(new SequentialValidator<>());
		}

		@Override
		protected Set<String> validate(final SequentialValidator<T> instance) {
			return new HashSet<>();
		}

		@Override
		public Builder<T> addValidator(final BiPredicate<T, Consumer<String>> predicate) {
			if (predicate != null) {
				getInstance().predicates.add(predicate);
			}

			return this;
		}
	}
}
