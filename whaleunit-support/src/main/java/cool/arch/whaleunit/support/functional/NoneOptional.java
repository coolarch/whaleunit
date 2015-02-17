package cool.arch.whaleunit.support.functional;

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

import cool.arch.whaleunit.support.functional.primitives.Effect;
import cool.arch.whaleunit.support.functional.primitives.Function;
import cool.arch.whaleunit.support.functional.primitives.Predicate;

class NoneOptional<V> extends Optional<V> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public <R> Optional<R> map(final Function<V, R> function) {
		return none();
	}
	
	@Override
	public V orNull() {
		return null;
	}
	
	@Override
	public Optional<V> filter(final Predicate<V> predicate) {
		return this;
	}
	
	@Override
	public Optional<V> or(final V defaultValue) {
		return of(defaultValue);
	}
	
	@Override
	public Optional<V> or(final Optional<V> defaultOptional) {
		return defaultOptional;
	}
	
	@Override
	public boolean isPresent() {
		return false;
	}
	
	@Override
	public Optional<V> ifPresent(final Effect<V> effect) {
		return this;
	}
	
	@Override
	public boolean equals(final Object object) {
		return object == this;
	}
	
	@Override
	public int hashCode() {
		return 0x4dba2659;
	}
	
	@Override
	public String toString() {
		return "Optional.none()";
	}
}
