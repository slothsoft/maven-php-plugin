package org.phpmaven.test;

/**
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 */

public class PhpMissing implements IgnoreWhenCondition {

	private static boolean phpExePresent;

	static {
		// the php.exe does not magically come to be during the build, so we can calculate
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
	public boolean isDisabled() {
		return !phpExePresent;
	}

	@Override
	public String toString() {
		return "PhpExeMissing [phpExePresent=" + phpExePresent + "]";
	}

}
