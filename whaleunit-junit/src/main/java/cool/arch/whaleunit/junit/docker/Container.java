package cool.arch.whaleunit.junit.docker;

public interface Container {
	
	void create();
	
	void start();
	
	void stop();
	
	void destroy();
	
	String getName();
	
}
