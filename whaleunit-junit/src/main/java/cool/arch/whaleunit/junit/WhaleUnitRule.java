package cool.arch.whaleunit.junit;

import java.lang.reflect.Method;

import cool.arch.whaleunit.annotation.DirtiesContainers;
import cool.arch.whaleunit.junit.exception.TestManagementException;


public final class WhaleUnitRule extends AbstractLifecyleHookRule {
	
	private final LifeCycle tracker = new ContextTracker();

	@Override
	protected void beforeClass(Class<?> testClass) {
		final DirtiesContainers annotation = testClass.getAnnotation(DirtiesContainers.class);
		
		if (annotation == null) {
			tracker.onInit(testClass);
		} else {
			tracker.onInit(testClass, annotation.value());
		}
	}

	@Override
	protected void afterClass(Class<?> testClass) {
		tracker.onCleanup();
	}

	@Override
	protected void succeeded(Class<?> testClass, String methodName) {
		tracker.onTestSucceeded();
	}

	@Override
	protected void failed(Class<?> testClass, String methodName, Throwable e) {
		tracker.onTestFailed();
	}

	@Override
	protected void skipped(Class<?> testClass, String methodName) {
		// Intentionally do nothing
	}

	@Override
	protected void starting(Class<?> testClass, String methodName) {
		tracker.onTestStart();
	}

	@Override
	protected void finished(Class<?> testClass, String methodName) {
		try {
			final Method method = testClass.getMethod(methodName);
			
			final DirtiesContainers annotation = method.getAnnotation(DirtiesContainers.class);
			
			if (annotation == null) {
				tracker.onTestEnd();
			} else {
				tracker.onTestEnd("foo", "bar");
			}
			
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO - Try to break this
			throw new TestManagementException("Error looking up method " + methodName);
		}
	}
}
