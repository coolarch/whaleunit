package cool.arch.whaleunit.junit;


import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

public final class WhaleUnitJUnit4ClassRunner extends BlockJUnit4ClassRunner {
	
	private final WhaleUnitRuntime runtime = WhaleUnitRuntime.create();

	public WhaleUnitJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
	
	@Override
	protected Object createTest() throws Exception {
		final Object instance = super.createTest();
		
		
		
		return instance;
	}
	
	@Override
	protected Statement withBeforeClasses(Statement statement) {
		return new PreClassStatement(super.withBeforeClasses(statement), runtime, getTestClass().getJavaClass());
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		return new PostClassStatement(super.withAfterClasses(statement), runtime);
	}

	@Override
	protected Statement withBefores(FrameworkMethod frameworkMethod, Object instance, Statement statement) {
		runtime.inject(instance);
		return new PreMethodStatement(super.withBefores(frameworkMethod, instance, statement), frameworkMethod.getMethod(), runtime, instance);
	}

	@Override
	protected Statement withAfters(FrameworkMethod frameworkMethod, Object instance, Statement statement) {
		return new PostMethodStatement(super.withAfters(frameworkMethod, instance, statement), frameworkMethod.getMethod(), runtime);
	}
}