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
import org.phpmaven.phpexec.library.PhpErrorException;
import org.phpmaven.phpexec.library.PhpException;
import org.phpmaven.phpexec.library.PhpExecutionException;
import org.phpmaven.phpexec.library.PhpWarningException;

/**
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 0.1.0
 */
public class ExceptionTest {

	@Test
	public void testCoreExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpCoreException();
		Assertions.assertNotNull(exc);

		exc = new PhpCoreException("foo");
		Assertions.assertNotNull(exc);

		exc = new PhpCoreException(exc);
		Assertions.assertNotNull(exc);

		exc = new PhpCoreException("foo", exc);
		Assertions.assertNotNull(exc);
	}

	@Test
	public void testErrorExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpErrorException(new File("foo.php"), "foo");
		Assertions.assertNotNull(exc);
	}

	@Test
	public void testExecutionExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpExecutionException(new File("foo.php"), "foo");
		Assertions.assertNotNull(exc);
	}

	@Test
	public void testWarningExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpWarningException(new File("foo.php"), "foo");
		Assertions.assertNotNull(exc);
	}

}
