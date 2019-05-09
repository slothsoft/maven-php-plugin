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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.phpmaven.phpexec.cli.PhpExecutableConfiguration;
import org.phpmaven.phpexec.library.IPhpExecutable;
import org.phpmaven.phpexec.library.IPhpExecutableConfiguration;
import org.phpmaven.phpexec.library.PhpErrorException;
import org.phpmaven.test.IgnoreIfPhpMissing;

/**
 * test cases for PHP error reporting support.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class ErrorReportingTest {

	@Rule
	public IgnoreIfPhpMissing rule = new IgnoreIfPhpMissing();

	// TODO [slothsoft]: The old testFalse() and testEALL() didn't expect an exception; I
	// do (according to the API doc
	// https://www.php.net/manual/de/function.trigger-error.php); so if this ever stops
	// throwing an exception, I was wrong

	/**
	 * Tests if the execution is aware of detecting error while setting error_reporting in the script.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testFalse() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/false-test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		
		try {
			exec.execute(defineTestPhp);
			Assert.fail("There should have been an exception!");
		} catch (PhpErrorException e) {
			// nothing to check
		}
	}


	/**
	 * Tests if the execution is aware of detecting error.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testEALL() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		execConfig.setErrorReporting("E_ALL");

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		try {
			exec.execute(defineTestPhp);
			Assert.fail("There should have been an exception!");
		} catch (PhpErrorException e) {
			// nothing to check
		}
	}

	/**
	 * Tests if the execution is aware of detecting error.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testEALLandNotEUSERDEPRECATED() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		execConfig.setErrorReporting("E_ALL & !E_USER_DEPRECATED");

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assert.assertEquals("", exec.execute(defineTestPhp));
	}

	/**
	 * Tests if the execution is aware of detecting error.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testNULL() throws Exception {
		final IPhpExecutableConfiguration execConfig = new PhpExecutableConfiguration();
		execConfig.setErrorReporting("0");

		final File defineTestPhp = new File("target/test-classes/org/phpmaven/phpexec/test/error-reporting/test.php");

		// assert that the execution is aware of detecting the error
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assert.assertEquals("", exec.execute(defineTestPhp));
	}

}