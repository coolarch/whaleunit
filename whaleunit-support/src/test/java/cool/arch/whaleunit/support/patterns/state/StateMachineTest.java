/**
 * 
 */
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

import static cool.arch.whaleunit.support.patterns.state.StateMachineTest.Alphabet.A;
import static cool.arch.whaleunit.support.patterns.state.StateMachineTest.Alphabet.B;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cool.arch.whaleunit.support.functional.Tuple2;
import cool.arch.whaleunit.support.functional.Tuples;

/**
 * 
 */
public class StateMachineTest {
	
	/**
	 * Test method for {@link cool.arch.whaleunit.support.patterns.state.StateMachine#create(java.lang.Class)}.
	 */
	@Test
	public final void testCreate() {
		final StateMachine<States, Alphabet, Object> machine = StateMachine.create(States.class, () -> Tuples.of(States.EVEN_EVEN, new Object()));
		
		machine.evaluate(A, B, A, B);
		
		final boolean result = machine.isInAcceptState();
		
		assertTrue(result);
		
	}
	
	public static enum Alphabet {
		A, B;
	}
	
	public static enum States implements State<States, Alphabet, Object> {
		
		EVEN_EVEN {
			@Override
			public Tuple2<States, Object> evaluate(final Object context, final Alphabet input) {
				switch (input) {
					case A:
						return Tuples.of(ODD_EVEN, false);
						
					case B:
						return Tuples.of(EVEN_ODD, false);
				}
				
				return Tuples.of(EVEN_EVEN, context);
			}
			
			@Override
			public boolean isAcceptState() {
				return true;
			}
		},
		
		EVEN_ODD {
			@Override
			public Tuple2<States, Object> evaluate(final Object context, final Alphabet input) {
				switch (input) {
					case A:
						return Tuples.of(ODD_ODD, false);
						
					case B:
						return Tuples.of(EVEN_EVEN, false);
				}
				
				return Tuples.of(EVEN_ODD, context);
			}
			
			@Override
			public boolean isAcceptState() {
				return false;
			}
		},
		
		ODD_EVEN {
			@Override
			public Tuple2<States, Object> evaluate(final Object context, final Alphabet input) {
				switch (input) {
					case A:
						return Tuples.of(EVEN_EVEN, false);
						
					case B:
						return Tuples.of(ODD_ODD, false);
				}
				
				return Tuples.of(ODD_EVEN, context);
			}
			
			@Override
			public boolean isAcceptState() {
				return false;
			}
		},
		
		ODD_ODD {
			@Override
			public Tuple2<States, Object> evaluate(final Object context, final Alphabet input) {
				switch (input) {
					case A:
						return Tuples.of(EVEN_ODD, false);
						
					case B:
						return Tuples.of(ODD_EVEN, false);
				}
				
				return Tuples.of(ODD_ODD, context);
			}
			
			@Override
			public boolean isAcceptState() {
				return false;
			}
		};
	}
}
