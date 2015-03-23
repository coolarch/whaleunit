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

import java.util.HashSet;
import java.util.Set;

import cool.arch.whaleunit.support.patterns.AbstractBuilderImpl;

class GroupBuilderImpl<T extends AbstractIterableGroup<U>, U> extends AbstractBuilderImpl<T> implements
	GroupBuilder<T, U> {

	GroupBuilderImpl(final T group) {
		super(group);
	}

	@Override
	public GroupBuilder<T, U> with(final Iterable<U> items) {
		if (items != null) {
			items.forEach(item -> getInstance().add(item));
		}

		return this;
	}

	@Override
	public GroupBuilder<T, U> with(final U item) {
		if (item != null) {
			getInstance().add(item);
		}

		return this;
	}

	@Override
	protected Set<String> validate(final T instance) {
		return new HashSet<>();
	}
}
