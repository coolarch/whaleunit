package cool.arch.whaleunit.support.patterns.state;

import cool.arch.whaleunit.support.functional.Tuple2;

public interface State<S extends State<S, A, C>, A extends Enum<A>, C> {
	
	Tuple2<S, C> evaluate(C context, A input);
	
	boolean isAcceptState();

}
