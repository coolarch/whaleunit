package cool.arch.whaleunit.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.AssumptionViolatedException;
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
                	beforeClass();
                    try {
                        base.evaluate();
                    } finally {
                        afterClass();
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
            succeeded(description);
        } catch (Throwable e) {
            errors.add(e);
        }
    }

    private void failedQuietly(Throwable e, Description description, List<Throwable> errors) {
        try {
            failed(e, description);
        } catch (Throwable e1) {
            errors.add(e1);
        }
    }

    private void skippedQuietly(org.junit.internal.AssumptionViolatedException e, Description description, List<Throwable> errors) {
        try {
            skipped((AssumptionViolatedException) e, description);
        } catch (Throwable e1) {
            errors.add(e1);
        }
    }

    private void startingQuietly(Description description, List<Throwable> errors) {
        try {
            starting(description);
        } catch (Throwable e) {
            errors.add(e);
        }
    }

    private void finishedQuietly(Description description, List<Throwable> errors) {
        try {
            finished(description);
        } catch (Throwable e) {
            errors.add(e);
        }
    }
    
    protected abstract void beforeClass();
    
    protected abstract void afterClass();
    
    /**
     * Invoked when a test succeeds
     */
    protected abstract void succeeded(Description description);

    /**
     * Invoked when a test fails
     */
    protected abstract void failed(Throwable e, Description description);

    /**
     * Invoked when a test is skipped due to a failed assumption.
     */
    protected abstract void skipped(AssumptionViolatedException e, Description description);

    /**
     * Invoked when a test is about to start
     */
    protected abstract void starting(Description description);

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected abstract void finished(Description description);
}