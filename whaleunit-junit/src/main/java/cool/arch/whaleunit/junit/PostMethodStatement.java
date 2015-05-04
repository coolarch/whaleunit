package cool.arch.whaleunit.junit;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

final class PostMethodStatement extends Statement {

	private final Statement childStatement;

	private final Method method;
	
	private final WhaleUnitRuntime runtime;

	public PostMethodStatement(Statement childStatement, Method method, final WhaleUnitRuntime runtime) {
		this.childStatement = childStatement;
		this.method = method;
		this.runtime = runtime;
	}

	@Override
	public void evaluate() throws Throwable {
		final List<Throwable> errors = new LinkedList<>();

		try {
			childStatement.evaluate();
			runtime.onTestSucceeded(method.getName());
		} catch (Throwable e) {
			errors.add(e);
		}

		if (!errors.isEmpty()) {
			try {
				runtime.onTestFailed(method.getName());
			} catch (Throwable e) {
				errors.add(e);
			}

			throw (errors.size() == 1) ? errors.get(0) : new MultipleFailureException(errors);
		}
	}
}
