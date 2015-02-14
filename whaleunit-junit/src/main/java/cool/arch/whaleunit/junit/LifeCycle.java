package cool.arch.whaleunit.junit;

public interface LifeCycle {

	void onInit(Object test, String... dirtiedContainers);

    void onTestStart();
    
    void onTestSucceeded();

    void onTestFailed();

    void onTestEnd(String... dirtiedContainers);
    
    void onCleanup();
}
