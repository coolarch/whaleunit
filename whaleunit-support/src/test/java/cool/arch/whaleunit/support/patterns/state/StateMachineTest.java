/**
 * 
 */
package cool.arch.whaleunit.support.patterns.state;

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
