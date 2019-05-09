package org.phpmaven.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 */

public class IgnoreIfPhpMissing implements TestRule {

	private static boolean phpExePresent;

	static {
		// the php.exe does not magically come to be during the build, so we can
		// calculate
		// it's present once
		try {
			final Runtime runtime = Runtime.getRuntime();
			final Process process = runtime.exec("php -v");
			phpExePresent = process.waitFor() == 0;
		} catch (final Exception e) {
			e.printStackTrace();
			phpExePresent = false;
		}
		System.out.println("PHP.exe is present: " + phpExePresent);
	}

	@Override
	public Statement apply(Statement base, Description description) {
		if (phpExePresent) {
			return base;
		}
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				// skip
			}
		};
	}

	@Override
	public String toString() {
		return "PhpMissingRule [phpExePresent=" + phpExePresent + "]";
	}
}
