/**
 * Copyright 2010-2012 by PHP-maven.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.phpmaven.core.test;

import java.io.File;
import java.lang.reflect.Constructor;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.ExecutionUtils;
import org.phpmaven.test.AbstractTestCase;

/**
 * Base test cases for the ExecutionUtils.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.1
 */
public class ExecutionUtilsTest extends AbstractTestCase {

	/**
	 * Tests the constructor (for code coverage).
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testCtor() throws Exception {
		final Constructor<ExecutionUtils> ctor = ExecutionUtils.class.getDeclaredConstructor();
		ctor.setAccessible(true);
		Assert.assertNotNull(ctor.newInstance());
	}

	/**
	 * Tests printing the directory (command ls on unix and dir on windows).
	 * @throws Exception
	 */

	@Test
	public void testExecuteLs() throws Exception {
		final String os2 = System.getProperty("os.name");
		String command = "ls";
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) {
			command = "dir";
		}
		Assert.assertNotNull(ExecutionUtils.executeCommand(null, command));
	}

	/**
	 * Tests printing the directory (command ls on unix and dir on windows).
	 * @throws Exception
	 */

	@Test
	public void testExecuteLsWithLogger() throws Exception {
		final String os2 = System.getProperty("os.name");
		String command = "ls";
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) {
			command = "dir";
		}
		final Log log = new DefaultLog(new ConsoleLogger());
		Assert.assertNotNull(ExecutionUtils.executeCommand(log, command));
	}

	/**
	 * Tests printing the directory (command ls on unix and dir on windows).
	 * @throws Exception
	 */

	@Test
	public void testExecuteWithoutWorkdirCreation() throws Exception {
		final String os2 = System.getProperty("os.name");
		String command = "ls";
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) {
			command = "dir";
		}

		final MavenSession session = createSimpleEmptySession();
		final File dir = new File(session.getCurrentProject().getBasedir(), "some/other/dir");
		Assert.assertFalse(dir.exists());
		dir.mkdirs();
		Assert.assertTrue(dir.exists());
		Assert.assertNotNull(ExecutionUtils.executeCommand(null, command, dir));
	}

	/**
	 * Tests printing the directory (command ls on unix and dir on windows).
	 * @throws Exception
	 */

	@Test
	public void testExecuteWithWorkdirCreation() throws Exception {
		final String os2 = System.getProperty("os.name");
		String command = "ls";
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) {
			command = "dir";
		}

		final MavenSession session = createSimpleEmptySession();
		final File dir = new File(session.getCurrentProject().getBasedir(), "some/other/dir");
		Assert.assertFalse(dir.exists());
		Assert.assertNotNull(ExecutionUtils.executeCommand(null, command, dir));
		Assert.assertTrue(dir.exists());
	}

	/**
	 * Tests printing the directory (command ls on unix and dir on windows).
	 * @throws Exception
	 */

	@Test
	public void testExecuteLsWithInvalidParam() throws Exception {
		final String os2 = System.getProperty("os.name");
		String command = "ls --foo";
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) {
			command = "dir /foo";
		}

		try {
			Assert.assertNotNull(ExecutionUtils.executeCommand(null, command));
			Assert.fail("expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final CommandLineException ex) {
			// exception was expected
		}
		// CHECKSTYLE:ON
	}

	/**
	 * Tests printing the directory (command ls on unix and dir on windows).
	 * @throws Exception
	 */

	@Test
	public void testExecuteLsWithInvalidParamAndLogger() throws Exception {
		final String os2 = System.getProperty("os.name");
		String command = "ls --foo";
		if (os2 != null && os2.toLowerCase().indexOf("windows") != -1) {
			command = "dir /foo";
		}
		final Log log = new DefaultLog(new ConsoleLogger());

		try {
			Assert.assertNotNull(ExecutionUtils.executeCommand(log, command));
			Assert.fail("expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final CommandLineException ex) {
			// exception was expected
		}
		// CHECKSTYLE:ON
	}

}