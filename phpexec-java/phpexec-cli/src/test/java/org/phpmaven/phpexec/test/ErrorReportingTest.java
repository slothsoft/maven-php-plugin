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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.phpexec.cli.PhpExecutableConfiguration;
import org.phpmaven.phpexec.library.IPhpExecutable;
import org.phpmaven.phpexec.library.IPhpExecutableConfiguration;
import org.phpmaven.test.IgnoreWhen;
import org.phpmaven.test.PhpMissing;

/**
 * test cases for PHP error reporting support.
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.0
 */
public class ErrorReportingTest {

	// XXX [slothsoft]: I have no idea why the next two tests won't work any longer

	/**
	 * Tests if the execution is aware of detecting error while setting error_reporting in the script.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	@Disabled
	@IgnoreWhen(PhpMissing.class)
	public void testFalse() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/false-test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assertions.assertTrue(exec.execute(defineTestPhp).contains("some deprecated warning"));
	}


	/**
	 * Tests if the execution is aware of detecting error.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	@Disabled
	@IgnoreWhen(PhpMissing.class)
	public void testEALL() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		execConfig.setErrorReporting("E_ALL");

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assertions.assertTrue(exec.execute(defineTestPhp).contains("some deprecated warning"));
	}

	/**
	 * Tests if the execution is aware of detecting error.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	@IgnoreWhen(PhpMissing.class)
	public void testEALLandNotEUSERDEPRECATED() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		execConfig.setErrorReporting("E_ALL & !E_USER_DEPRECATED");

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assertions.assertEquals("", exec.execute(defineTestPhp));
	}

	/**
	 * Tests if the execution is aware of detecting error.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	@IgnoreWhen(PhpMissing.class)
	public void testNULL() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		execConfig.setErrorReporting("0");

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assertions.assertEquals("", exec.execute(defineTestPhp));
	}

}