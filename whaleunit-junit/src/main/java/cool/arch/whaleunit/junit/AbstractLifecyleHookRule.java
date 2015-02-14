package cool.arch.whaleunit.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public abstract class AbstractLifecyleHookRule implements TestRule {

	public Statement apply(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				if (description.getMethodName() == null) {
					beforeClass(description.getTestClass());
					try {
						base.evaluate();
					} finally {
						afterClass(description.getTestClass());
					}

					return;
				}

				List<Throwable> errors = new ArrayList<Throwable>();

				startingQuietly(description, errors);

				try {
					base.evaluate();
					succeededQuietly(description, errors);
				} catch (org.junit.internal.AssumptionViolatedException e) {
					errors.add(e);
					skippedQuietly(e, description, errors);
				} catch (Throwable e) {
					errors.add(e);
					failedQuietly(e, description, errors);
				} finally {
					finishedQuietly(description, errors);
				}

				MultipleFailureException.assertEmpty(errors);
			}
		};
	}

	private void succeededQuietly(Description description, List<Throwable> errors) {
		try {
			succeeded(description.getTestClass(), description.getMethodName());
		} catch (Throwable e) {
			errors.add(e);
		}
	}

	private void failedQuietly(Throwable e, Description description, List<Throwable> errors) {
		try {
			failed(description.getTestClass(), description.getMethodName(), e);
		} catch (Throwable e1) {
			errors.add(e1);
		}
	}

	private void skippedQuietly(org.junit.internal.AssumptionViolatedException e, Description description, List<Throwable> errors) {
		try {
			skipped(description.getTestClass(), description.getMethodName());
		} catch (Throwable e1) {
			errors.add(e1);
		}
	}

	private void startingQuietly(Description description, List<Throwable> errors) {
		try {
			starting(description.getTestClass(), description.getMethodName());
		} catch (Throwable e) {
			errors.add(e);
		}
	}

	private void finishedQuietly(Description description, List<Throwable> errors) {
		try {
			finished(description.getTestClass(), description.getMethodName());
		} catch (Throwable e) {
			errors.add(e);
		}
	}

	protected abstract void beforeClass(Class<?> testClass);

	protected abstract void afterClass(Class<?> testClass);

	/**
	 * Invoked when a test succeeds
	 */
	protected abstract void succeeded(Class<?> testClass, String methodName);

	/**
	 * Invoked when a test fails
	 */
	protected abstract void failed(Class<?> testClass, String methodName, Throwable e);

	/**
	 * Invoked when a test is skipped due to a failed assumption.
	 */
	protected abstract void skipped(Class<?> testClass, String methodName);

	/**
	 * Invoked when a test is about to start
	 */
	protected abstract void starting(Class<?> testClass, String methodName);

	/**
	 * Invoked when a test method finishes (whether passing or failing)
	 */
	protected abstract void finished(Class<?> testClass, String methodName);
}