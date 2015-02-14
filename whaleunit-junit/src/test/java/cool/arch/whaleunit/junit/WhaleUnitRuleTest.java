/**
 * 
 */
package cool.arch.whaleunit.junit;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import cool.arch.whaleunit.annotation.DirtiesContainers;

/**
 *
 */
@DirtiesContainers({"bat"})
public class WhaleUnitRuleTest {

	@Rule
	@ClassRule
	public static final WhaleUnitRule whaleUnitRule = new WhaleUnitRule();


	/**
	 * Test method for {@link cool.arch.whaleunit.junit.WhaleUnitRule#testExecution()}.
	 */
	@Test
	@DirtiesContainers({"baz"})
	public void testOne() {
		System.out.println("one");
	}
	
	@Test
	public void testTwo() {
		System.out.println("two");
	}
	
	@Test
	@DirtiesContainers({"foo", "bar"})
	public void testThree() {
		System.out.println("three");
	}
}
