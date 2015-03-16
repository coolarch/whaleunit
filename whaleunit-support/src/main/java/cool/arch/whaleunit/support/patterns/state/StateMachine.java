package cool.arch.whaleunit.support.patterns.state;

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

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import cool.arch.whaleunit.support.functional.Tuple2;

public final class StateMachine<S extends Enum<S> & State<S, A, C>, A extends Enum<A>, C> {
	
	private final AtomicReference<Tuple2<S, C>> currentState = new AtomicReference<>();
	
	private volatile boolean inEvaluation = false;
	
	private final Object lock = new Object();
	
	private StateMachine(final Class<S> stateType, final Supplier<Tuple2<S, C>> contextFactory) {
		requireNonNull(contextFactory, "contextFactory shall not be null");
		currentState.set(contextFactory.get());
	}
	
	@SuppressWarnings("unchecked")
	public StateMachine<S, A, C> evaluate(final A... inputs) {
		boolean blocked = false;
		
		synchronized (lock) {
			if (inEvaluation) {
				blocked = true;
			} else {
				inEvaluation = true;
			}
		}
		
		if (blocked) {
			return this;
		}
		
		for (final A input : inputs) {
			final Tuple2<S, C> current = currentState.get();
			final S state = current.item0();
			final C context = current.item1();
			final Tuple2<S, C> newState = state.evaluate(context, input);
			currentState.set(newState);
		}
		
		inEvaluation = false;
		
		return this;
	}
	
	public boolean isInAcceptState() {
		return currentState.get().item0().isAcceptState();
	}
	
	public static <S extends Enum<S> & State<S, A, C>, A extends Enum<A>, C> StateMachine<S, A, C> create(final Class<S> statesType,
		final Supplier<Tuple2<S, C>> contextFactory) {
		return new StateMachine<S, A, C>(statesType, contextFactory);
	}
}
