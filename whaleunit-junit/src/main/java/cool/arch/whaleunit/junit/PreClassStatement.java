package cool.arch.whaleunit.junit;

import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

final class PreClassStatement extends Statement {

	private final Statement childStatement;
	
	private final WhaleUnitRuntime runtime;
	
	private final Class<?> testClass;

	public PreClassStatement(Statement childStatement, final WhaleUnitRuntime runtime, final Class<?> testClass) {
		this.childStatement = childStatement;
		this.runtime = runtime;
		this.testClass = testClass;
	}

	@Override
	public void evaluate() throws Throwable {
		runtime.onInit(testClass);
		childStatement.evaluate();
	}
}
