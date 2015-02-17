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

class SomeOptional<V> extends Optional<V> {
	
	private static final long serialVersionUID = 1L;
	
	private final V value;
	
	SomeOptional(final V value) {
		this.value = value;
	}
	
	@Override
	public <R> Optional<R> map(final Function<V, R> function) {
		return Optional.of(function.apply(value));
	}
	
	@Override
	public V orNull() {
		return value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Optional<V> filter(final Predicate<V> predicate) {
		if (predicate == null) {
			return this;
		}
		
		return (Optional<V>) (predicate.test(value) ? this : Optional.none());
	}
	
	@Override
	public Optional<V> or(final V defaultValue) {
		return this;
	}
	
	@Override
	public Optional<V> or(final Optional<V> defaultOptional) {
		return this;
	}
	
	@Override
	public boolean isPresent() {
		return true;
	}
	
	@Override
	public Optional<V> ifPresent(final Effect<V> effect) {
		if (effect != null) {
			effect.apply(value);
		}
		
		return this;
	}
	
	@Override public boolean equals(final Object object) {
		if (object instanceof SomeOptional) {
			final SomeOptional<?> other = (SomeOptional<?>) object;
			
			return value.equals(other.value);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 0x4dba2659 + value.hashCode();
	}
	
	@Override
	public String toString() {
		return "Optional.of(" + value + ")";
	}
}
