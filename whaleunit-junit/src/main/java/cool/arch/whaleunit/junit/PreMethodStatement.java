package cool.arch.whaleunit.junit;

import java.lang.reflect.Method;

import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

final class PreMethodStatement extends Statement {

	private final Statement childStatement;

	private final Method method;
	
	private final WhaleUnitRuntime runtime;
	
	private final Object instance;

	public PreMethodStatement(Statement childStatement, Method method, final WhaleUnitRuntime runtime, Object instance) {
		this.childStatement = childStatement;
		this.method = method;
		this.runtime = runtime;
		this.instance = instance;
	}

	@Override
	public void evaluate() throws Throwable {
		runtime.onTestStart(method.getName(), instance);
		childStatement.evaluate();
	}
}
