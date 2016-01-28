package cool.arch.whaleunit.support.validation;

import org.junit.Test;

public class SequentialValidatorTest {

	/**
	 * Test method for {@link cool.arch.whaleunit.support.validation.SequentialValidator#validate(java.lang.Object, java.util.stream.Collector)}.
	 */
	@Test
	public void testValidate() {
		final SequentialValidator<String> validator = SequentialValidator.<String>builder()
			.withValidator((model, errors) -> true)
			.build();
	}
}
