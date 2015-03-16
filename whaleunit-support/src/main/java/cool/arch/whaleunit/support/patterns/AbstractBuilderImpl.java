package cool.arch.whaleunit.support.patterns;

/*
 * #%L
 * WhaleUnit - Support
 * %%
 * Copyright (C) 2015 CoolArch
 * %%
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * #L%
 */

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

public abstract class AbstractBuilderImpl<T> implements AbstractBuilder<T> {
	
	final T instance;
	
	protected AbstractBuilderImpl(final T instance) {
		this.instance = requireNonNull(instance, "instance shall not be null");
	}
	
	@Override
	public final Optional<T> build() {
		final Set<String> errors = validate(getInstance());
		
		if (!errors.isEmpty()) {
			final StringJoiner joiner = new StringJoiner(", ", "Instance of " + instance.getClass().getName() + " is not valid to be built: ", ".");
			errors.forEach(joiner::add);
			
			throw new IllegalStateException(joiner.toString());
		}
		
		return Optional.of(instance);
	}
	
	protected T getInstance() {
		return instance;
	}
	
	protected abstract Set<String> validate(T instance);
}
