package cool.arch.whaleunit.junit.exception;

public class TestManagementException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestManagementException() {
		super();
	}
	
	public TestManagementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public TestManagementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TestManagementException(String message) {
		super(message);
	}
	
	public TestManagementException(Throwable cause) {
		super(cause);
	}
}
