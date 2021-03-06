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

import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.phpexec.library.PhpCoreException;
import org.phpmaven.phpexec.library.PhpErrorException;
import org.phpmaven.phpexec.library.PhpException;
import org.phpmaven.phpexec.library.PhpExecutionException;
import org.phpmaven.phpexec.library.PhpWarningException;

/**
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 0.1.0
 */
public class ExceptionTest {

	@Test
	public void testCoreExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpCoreException();
		Assert.assertNotNull(exc);

		exc = new PhpCoreException("foo");
		Assert.assertNotNull(exc);

		exc = new PhpCoreException(exc);
		Assert.assertNotNull(exc);

		exc = new PhpCoreException("foo", exc);
		Assert.assertNotNull(exc);
	}

	@Test
	public void testErrorExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpErrorException(new File("foo.php"), "foo");
		Assert.assertNotNull(exc);
	}

	@Test
	public void testExecutionExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpExecutionException(new File("foo.php"), "foo");
		Assert.assertNotNull(exc);
	}

	@Test
	public void testWarningExceptionConstructors() {
		// testing for code coverage
		PhpException exc = null;

		exc = new PhpWarningException(new File("foo.php"), "foo");
		Assert.assertNotNull(exc);
	}

}
