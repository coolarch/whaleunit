package cool.arch.whaleunit.junit;

import org.junit.AssumptionViolatedException;
import org.junit.runner.Description;

public class WhaleUnitRule extends AbstractLifecyleHookRule {

	@Override
	protected void succeeded(Description description) {
		System.out.println("succeeded");
	}

	@Override
	protected void failed(Throwable e, Description description) {
		System.out.println("failed");
	}

	@Override
	protected void skipped(AssumptionViolatedException e, Description description) {
		System.out.println("skipped");
	}

	@Override
	protected void starting(Description description) {
		System.out.println("starting");
	}

	@Override
	protected void finished(Description description) {
		System.out.println("finished");
	}

	@Override
	protected void beforeClass() {
		System.out.println("beforeClass");
	}

	@Override
	protected void afterClass() {
		System.out.println("afterClass");
	}
}
