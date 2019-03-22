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

package org.phpmaven.phpexec.library.test;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phpmaven.phpexec.library.PhpCoreException;
import org.phpmaven.phpexec.library.PhpExecutionException;

/**
 * test cases for PHP support.
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @since 0.1.8
 */
public class Exception2Test {

	/**
	 * Tests some things on php execution exception.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testExecException() throws Exception {
		PhpExecutionException ex = new PhpExecutionException(null, "FOO");
		Assertions.assertEquals("\nFOO", ex.getMessage());
		final File fooFile = new File("foo.php");
		ex = new PhpExecutionException(fooFile, "FOO");
		Assertions.assertTrue(ex.getMessage().contains("FOO"));
		Assertions.assertTrue(ex.getMessage().contains(fooFile.getAbsolutePath()));
	}

	/**
	 * Tests some things on php core exception.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void testCoreException() throws Exception {
		// constructors for code coverage
		PhpCoreException ex = new PhpCoreException();
		ex = new PhpCoreException("FOO", new Exception());
		ex = new PhpCoreException(new Exception());

		ex = new PhpCoreException("some meaningful error");
		Assertions.assertNull(ex.getAppendedOutput());
		Assertions.assertEquals("some meaningful error", ex.getMessage());
		ex.appendOutput("FOOBAR");
		Assertions.assertEquals("FOOBAR", ex.getAppendedOutput());
		Assertions.assertTrue(ex.getMessage().contains("FOOBAR"));
		Assertions.assertTrue(ex.getMessage().contains("some meaningful error"));
		ex.appendOutput("BAZ");
		Assertions.assertEquals("BAZ", ex.getAppendedOutput());
		Assertions.assertFalse(ex.getMessage().contains("FOOBAR"));
		Assertions.assertTrue(ex.getMessage().contains("BAZ"));
		Assertions.assertTrue(ex.getMessage().contains("some meaningful error"));
	}

}