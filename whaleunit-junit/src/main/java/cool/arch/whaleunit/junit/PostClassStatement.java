package cool.arch.whaleunit.junit;

import java.util.LinkedList;
import java.util.List;

import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

final class PostClassStatement extends Statement {

	private final Statement childStatement;
	
	private final WhaleUnitRuntime runtime;

	public PostClassStatement(Statement childStatement, final WhaleUnitRuntime runtime) {
		this.childStatement = childStatement;
		this.runtime = runtime;
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new LinkedList<>();

		try {
			childStatement.evaluate();
		} catch (Throwable e) {
			errors.add(e);
		}

		try {
			runtime.onCleanup();
		} catch (Exception e) {
			errors.add(e);
		}

		if (!errors.isEmpty()) {
			throw (errors.size() == 1) ? errors.get(0) : new MultipleFailureException(errors);
		}
	}
}
