/**
 * 
 */
package cool.arch.whaleunit.junit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 */
public class WhaleUnitRuleTest {

	@Rule
	@ClassRule
	public static final WhaleUnitRule whaleUnitRule = new WhaleUnitRule();


	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#testExecution()}.
	 */
	@Test
	public void testExecution() {
		System.out.println("During 1");
	}
	
	@Test
	public void secondTest() {
		System.out.println("During 2");
	}
}
