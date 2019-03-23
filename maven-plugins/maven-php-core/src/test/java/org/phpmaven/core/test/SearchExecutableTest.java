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

import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.ExecutionUtils;
import org.phpmaven.test.AbstractTestCase;


/**
 * Test case for the searchExecutable method.
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.1
 */
public class SearchExecutableTest extends AbstractTestCase {

	/**
	 * Tests to find simple executables.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFindExecutableWindows() throws Exception {
		final String oldLib = System.getProperty("java.library.path");
		final String oldOs = System.getProperty("os.name");
		final MavenSession session = createSimpleSession("core/with-exec");
		try {
			System.setProperty("os.name", "Windows");
			System.setProperty("java.library.path",
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test1/folderA" + File.pathSeparator +
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test1/folderB");

			final File foo = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderA/foo");
			final File bar = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderB/bar");
			final File baz = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderA/baz");

			Assertions.assertEquals(foo.getAbsolutePath(), ExecutionUtils.searchExecutable(null,  "foo"));
			Assertions.assertEquals(bar.getAbsolutePath(), ExecutionUtils.searchExecutable(null,  "bar"));
			Assertions.assertEquals(baz.getAbsolutePath(), ExecutionUtils.searchExecutable(null,  "baz"));
		} finally {
			System.setProperty("os.name", oldOs);
			System.setProperty("java.library.path", oldLib);
		}
	}

	/**
	 * Tests to find simple executables.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFindExecutableUnix() throws Exception {
		final String oldLib = System.getProperty("java.library.path");
		final String oldOs = System.getProperty("os.name");
		final MavenSession session = createSimpleSession("core/with-exec");
		try {
			System.setProperty("os.name", "Unix/Linux");
			System.setProperty("java.library.path",
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test1/folderA" + File.pathSeparator +
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test1/folderB");

			final File foo = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderA/foo");
			final File bar = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderB/bar");
			final File baz = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderA/baz");

			Assertions.assertEquals(foo.getAbsolutePath(), ExecutionUtils.searchExecutable(null,  "foo"));
			Assertions.assertEquals(bar.getAbsolutePath(), ExecutionUtils.searchExecutable(null,  "bar"));
			Assertions.assertEquals(baz.getAbsolutePath(), ExecutionUtils.searchExecutable(null,  "baz"));
		} finally {
			System.setProperty("os.name", oldOs);
			System.setProperty("java.library.path", oldLib);
		}
	}

	/**
	 * Tests to find simple executables.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFindExecutableWithLog() throws Exception {
		final String oldLib = System.getProperty("java.library.path");
		final String oldOs = System.getProperty("os.name");
		final MavenSession session = createSimpleSession("core/with-exec");
		try {
			System.setProperty("os.name", "Unix/Linux");
			System.setProperty("java.library.path",
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test1/folderA" + File.pathSeparator +
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test1/folderB");

			final File foo = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderA/foo");
			final File bar = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderB/bar");
			final File baz = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test1/folderA/baz");

			final Log log = new DefaultLog(new ConsoleLogger());

			Assertions.assertEquals(foo.getAbsolutePath(), ExecutionUtils.searchExecutable(log,  "foo"));
			Assertions.assertEquals(bar.getAbsolutePath(), ExecutionUtils.searchExecutable(log,  "bar"));
			Assertions.assertEquals(baz.getAbsolutePath(), ExecutionUtils.searchExecutable(log,  "baz"));
		} finally {
			System.setProperty("os.name", oldOs);
			System.setProperty("java.library.path", oldLib);
		}
	}

	/**
	 * Tests to find simple executables.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFindExecutableWindowsExtensions() throws Exception {
		final String oldLib = System.getProperty("java.library.path");
		final String oldOs = System.getProperty("os.name");
		final MavenSession session = createSimpleSession("core/with-exec");
		try {
			System.setProperty("os.name", "Windows");
			System.setProperty("java.library.path",
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test2/folderA" + File.pathSeparator +
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test2/folderB");

			final File foo = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test2/folderA/foo");
			final File bar = new File(session.getCurrentProject().getBasedir().getAbsolutePath(),
					"test2/folderB/bar.cmd");
			final File baz = new File(session.getCurrentProject().getBasedir().getAbsolutePath(),
					"test2/folderA/baz.exe");

			final Log log = new DefaultLog(new ConsoleLogger());

			Assertions.assertEquals(foo.getAbsolutePath(), ExecutionUtils.searchExecutable(log,  "foo"));
			Assertions.assertEquals(bar.getAbsolutePath(), ExecutionUtils.searchExecutable(log,  "bar"));
			Assertions.assertEquals(baz.getAbsolutePath(), ExecutionUtils.searchExecutable(log,  "baz"));
		} finally {
			System.setProperty("os.name", oldOs);
			System.setProperty("java.library.path", oldLib);
		}
	}

	/**
	 * Tests to find simple executables.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFindAbsolute() throws Exception {
		final String oldLib = System.getProperty("java.library.path");
		final String oldOs = System.getProperty("os.name");
		final MavenSession session = createSimpleSession("core/with-exec");
		try {
			System.setProperty("os.name", "Windows");
			System.setProperty("java.library.path",
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test2/folderA" + File.pathSeparator +
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test2/folderB");

			final File foo = new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "test2/folderA/foo");

			Assertions.assertEquals(foo.getAbsolutePath(), ExecutionUtils.searchExecutable(null, foo.getAbsolutePath()));
		} finally {
			System.setProperty("os.name", oldOs);
			System.setProperty("java.library.path", oldLib);
		}
	}

	/**
	 * Tests to find simple not non-existing executables/ return null.
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFindNull() throws Exception {
		final String oldLib = System.getProperty("java.library.path");
		final String oldOs = System.getProperty("os.name");
		final MavenSession session = createSimpleSession("core/with-exec");
		try {
			System.setProperty("os.name", "Windows");
			System.setProperty("java.library.path",
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test2/folderA" + File.pathSeparator +
					session.getCurrentProject().getBasedir().getAbsolutePath() + "/test2/folderB");

			Assertions.assertNull(ExecutionUtils.searchExecutable(null, "foobarbaz"));
		} finally {
			System.setProperty("os.name", oldOs);
			System.setProperty("java.library.path", oldLib);
		}
	}

}
