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

package org.phpmaven.php.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.execution.MavenSession;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutableConfiguration;
import org.phpmaven.phpexec.library.IPhpExecutable;
import org.phpmaven.phpexec.library.PhpErrorException;
import org.phpmaven.phpexec.library.PhpWarningException;
import org.phpmaven.test.AbstractTestCase;

/**
 * test cases for PHP support.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class IncludesTest extends AbstractTestCase {

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testExisting() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		final File includeTestPhp = new File(session.getCurrentProject().getBasedir(), "includes-test.php");
		execConfig.getIncludePath().add(
				new File(session.getCurrentProject().getBasedir(), "includes").getAbsolutePath());

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assert.assertEquals("SUCCESS_EXISTING\n", exec.execute(includeTestPhp));
	}

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testExistingPut() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		final File includeTestPhp = new File(session.getCurrentProject().getBasedir(), "includes-test.php");
		final List<String> includes = new ArrayList<String>();
		includes.add(
				new File(session.getCurrentProject().getBasedir(), "includes").getAbsolutePath());
		execConfig.setIncludePath(includes);

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assert.assertEquals("SUCCESS_EXISTING\n", exec.execute(includeTestPhp));
	}

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testFailing() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		final File includeTestPhp = new File(session.getCurrentProject().getBasedir(), "includes-test.php");

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		try {
			// we will either expect a php warning or a php error.
			// depends on php.ini and php version.
			exec.execute(includeTestPhp);
			Assert.fail("Exception expected");
		} catch (final PhpWarningException ex) {
			// ignore; we expect this exception
			Assert.assertTrue(ex.getMessage().contains("Warning: require_once(existing.php)"));
		} catch (final PhpErrorException ex) {
			// ignore; we expect this exception
			Assert.assertTrue(ex.getMessage().contains("Fatal error: require_once()"));
		}
	}
//
//    /**
//     * Tests if the execution configuration can be created.
//     *
//     * @throws Exception thrown on errors
//     */
//    public void testFailingIgnored() throws Exception {
//        // look up the component factory
//        final IComponentFactory factory = lookup(IComponentFactory.class);
//        // create the execution config
//        final MavenSession session = this.createSession("empty-pom");
//        final IPhpExecutableConfiguration execConfig = factory.lookup(
//                IPhpExecutableConfiguration.class,
//                IComponentFactory.EMPTY_CONFIG,
//                session);
//
//        final File includeTestPhp = new File(session.getCurrentProject().getBasedir(), "includes-test.php");
//        execConfig.setIgnoreIncludeErrors(true);
//
//        // assert that the environment variable is mapped correctly
//        final IPhpExecutable exec = execConfig.getPhpExecutable();
//        // TODO exec.execute(includeTestPhp);
//        // TODO currently does not work because the php.exe returns non-zero error code at cli.
//        // there should be no exception thrown.
//    }

}