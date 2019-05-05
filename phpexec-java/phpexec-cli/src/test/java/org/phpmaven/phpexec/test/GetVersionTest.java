/**
 * Copyright 2010-2012 by PHP-maven.org
 *
 * This file is part of phpexec-java.
 *
 * phpexec-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * phpexec-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with phpexec-java.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.phpmaven.phpexec.test;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phpmaven.phpexec.cli.PhpExecutableConfiguration;
import org.phpmaven.phpexec.library.IPhpExecutableConfiguration;
import org.phpmaven.phpexec.library.PhpException;
import org.phpmaven.phpexec.library.PhpVersion;

/**
 * test cases for PHP version detection.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class GetVersionTest {

	/**
	 * Returns true if the operating system is windows.
	 * @return true if this is windows.
	 * @since 2.0.1
	 */
	private static boolean isWindows() {
		final String os2 = System.getProperty("os.name");
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) return true;
		return false;
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion4() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php4.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php4").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php4");
		}
		Assertions.assertEquals(PhpVersion.PHP4, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion5() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php5.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php5").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php5");
		}
		Assertions.assertEquals(PhpVersion.PHP5, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion6() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php6.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php6").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php6");
		}
		Assertions.assertEquals(PhpVersion.PHP6, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion7() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php7.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php7").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php7");
		}
		Assertions.assertEquals(PhpVersion.PHP7, execConfig.getPhpExecutable().getVersion());
	}


	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersionUnknown() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpUnknown.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/phpUnknown").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpUnknown");
		}
		Assertions.assertEquals(PhpVersion.UNKNOWN, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersionIllegal() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpIllegal.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/phpIllegal").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpIllegal");
		}
		try {
			execConfig.getPhpExecutable().getVersion();
			Assertions.fail("Expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final PhpException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion4NotCached() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php4.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php4").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php4");
		}
		execConfig.setUseCache(false);
		Assertions.assertEquals(PhpVersion.PHP4, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion5NotCached() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php5.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php5").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php5");
		}
		execConfig.setUseCache(false);
		Assertions.assertEquals(PhpVersion.PHP5, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion6NotCached() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php6.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php6").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php6");
		}
		execConfig.setUseCache(false);
		Assertions.assertEquals(PhpVersion.PHP6, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersion7NotCached() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php7.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/php7").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/php7");
		}
		execConfig.setUseCache(false);
		Assertions.assertEquals(PhpVersion.PHP7, execConfig.getPhpExecutable().getVersion());
	}
	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersionUnknownNotCached() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpUnknown.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/phpUnknown").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpUnknown");
		}
		execConfig.setUseCache(false);
		Assertions.assertEquals(PhpVersion.UNKNOWN, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testGetVersionIllegalNotCached() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		if (isWindows()) {
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpIllegal.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File("target/test-classes/org/phpmaven/phpexec/test/version/phpIllegal").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			execConfig.setExecutable("target/test-classes/org/phpmaven/phpexec/test/version/phpIllegal");
		}
		execConfig.setUseCache(false);

		try {
			execConfig.getPhpExecutable().getVersion();
			Assertions.fail("Expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final PhpException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

}