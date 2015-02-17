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

import java.io.Serializable;

import cool.arch.whaleunit.support.functional.primitives.Effect;
import cool.arch.whaleunit.support.functional.primitives.Function;
import cool.arch.whaleunit.support.functional.primitives.Predicate;

/**
 * Implementation of the optional monad.
 * @param <T> Contained type
 */
public abstract class Optional<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Optional<Object> NONE = new NoneOptional<>();
	
	/**
	 * Optionally Binds a function to the contained value, if the value is present.
	 * @param function
	 * @return
	 */
	public abstract <R> Optional<R> map(Function<T, R> function);
	
	public abstract T orNull();
	
	public abstract Optional<T> filter(Predicate<T> predicate);
	
	public abstract Optional<T> or(T defaultValue);
	
	public abstract Optional<T> or(Optional<T> defaultOptional);
	
	public abstract boolean isPresent();
	
	public abstract Optional<T> ifPresent(Effect<T> effect);
	
	@Override
	public abstract boolean equals(Object object);
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract String toString();
	
	private static <V> Optional<V> some(final V value) {
		return new SomeOptional<>(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <V> Optional<V> none() {
		return (Optional<V>) NONE;
	}
	
	@SuppressWarnings("unchecked")
	public static <V> Optional<V> of(final V value) {
		return (Optional<V>) (value == null ? none() : some(value));
	}
	
	@SuppressWarnings("unchecked")
	public static <V> Optional<V> of(final Optional<V> value) {
		return (Optional<V>) (value == null ? none() : value);
	}
	
	public static Optional<Boolean> of(final boolean value) {
		return Optional.of(Boolean.valueOf(value));
	}
	
	public static Optional<Integer> of(final int value) {
		return Optional.of(Integer.valueOf(value));
	}
	
	public static Optional<Long> of(final long value) {
		return Optional.of(Long.valueOf(value));
	}
	
	public static Optional<Character> of(final char value) {
		return Optional.of(Character.valueOf(value));
	}
	
	public static Optional<Double> of(final double value) {
		return Optional.of(Double.valueOf(value));
	}
	
	public static Optional<Float> of(final float value) {
		return Optional.of(Float.valueOf(value));
	}
}