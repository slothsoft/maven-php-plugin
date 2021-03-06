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

import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.util.cli.StreamConsumer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutableConfiguration;
import org.phpmaven.phpexec.library.IPhpExecutable;
import org.phpmaven.test.AbstractTestCase;
import org.phpmaven.test.IgnoreIfPhpMissing;

/**
 * test cases for various executions.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class ExecTest extends AbstractTestCase {

	@Rule
	public IgnoreIfPhpMissing rule = new IgnoreIfPhpMissing();

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testArgsWithConsumer() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		final File envTestPhp = new File(session.getCurrentProject().getBasedir(), "args-test.php");

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assert.assertEquals(0, exec.execute("\"" + envTestPhp.getAbsolutePath() + "\" JUNIT_ARG_TEST", envTestPhp,
				new StreamConsumer() {

			@Override
			public void consumeLine(String line) {
				// does nothing
			}
		}));
	}

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testCode() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		final String output = exec.executeCode("", "echo 'FOO';");
		Assert.assertEquals("FOO\n", output);
	}

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testCodeArgs() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		final String output = exec.executeCode("", "echo $argv[1];", "JUNIT_ARG_TEST");
		Assert.assertEquals("JUNIT_ARG_TEST\n", output);
	}

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testArgsWithConsumer2() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/empty-pom");
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		final File envTestPhp = new File(session.getCurrentProject().getBasedir(), "args-test.php");

		// assert that the environment variable is mapped correctly
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assert.assertEquals(0, exec.execute("\"" + envTestPhp.getAbsolutePath() + "\" JUNIT_ARG_TEST",
				new StreamConsumer() {

			@Override
			public void consumeLine(String line) {
				// does nothing
			}
		},
				new StreamConsumer() {
			@Override
			public void consumeLine(String line) {
				// does nothing
			}
		}));
	}

}