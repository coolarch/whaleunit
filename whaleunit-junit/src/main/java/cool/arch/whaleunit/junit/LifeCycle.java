package cool.arch.whaleunit.junit;

public interface LifeCycle {

	void beforeClass();
    
    void afterClass();
    
    void onTestSucceeded();

    void onTestFailed();

    void onTestStart();

    void onTestEnd();
}
