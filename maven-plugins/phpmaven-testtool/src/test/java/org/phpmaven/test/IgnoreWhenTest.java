package org.phpmaven.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Stef Schulz <s.schulz@slothsoft.de>
 */

public class IgnoreWhenTest {

	private static boolean testExecuteCalled = false;

	@Test
	@Disabled
	public void testDisabled() {
		Assertions.fail("Should not be called!");
	}

	public static class IgnoreAlways implements IgnoreWhenCondition {

		@Override
		public boolean isDisabled() {
			return true;
		}
	}

	@Test
	@IgnoreWhen(IgnoreAlways.class)
	public void testIgnore() {
		Assertions.fail("Should not be called!");
	}

	public static class IgnoreNever implements IgnoreWhenCondition {

		@Override
		public boolean isDisabled() {
			return false;
		}
	}

	@Test
	@IgnoreWhen(IgnoreNever.class)
	public void testExecute() {
		IgnoreWhenTest.testExecuteCalled = true;
	}

	@AfterAll
	public static void tearDown() {
		Assertions.assertTrue(testExecuteCalled, "This method should have been executed!");
	}
}
