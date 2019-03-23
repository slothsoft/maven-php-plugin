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

import org.apache.maven.execution.MavenSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutableConfiguration;
import org.phpmaven.phpexec.library.IPhpExecutable;
import org.phpmaven.phpexec.library.PhpException;
import org.phpmaven.test.AbstractTestCase;
import org.phpmaven.test.IgnoreWhen;
import org.phpmaven.test.PhpMissing;

/**
 * test cases for PHP support.
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.0
 */
public class BaseTest extends AbstractTestCase {


	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	@IgnoreWhen(PhpMissing.class)
	public void testECCreation() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleEmptySession();
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that it is not null
		Assertions.assertNotNull(execConfig);
		// assert that we are able to create the executable
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		Assertions.assertNotNull(exec.getStrVersion());
		Assertions.assertNotNull(exec.getVersion());
	}

	/**
	 * Tests if the execution configuration can be created
	 * with an unknown executable set.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	@IgnoreWhen(PhpMissing.class)
	public void testUnknownExecutable() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleEmptySession();
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		execConfig.setExecutable("/foo/bar/php");
		// assert that we are able to create the executable
		final IPhpExecutable exec = execConfig.getPhpExecutable();
		try {
			exec.getStrVersion();
			Assertions.fail("Exception expected");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final PhpException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

	/**
	 * Tests if the exec configuration can be created
	 * with an unknown interpreter set.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	@IgnoreWhen(PhpMissing.class)
	public void testUnknownInterpreter() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleEmptySession();
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assertions.assertEquals("PHP_EXE", execConfig.getInterpreter());
		execConfig.setInterpreter("foo-bar-php");
		// assert that we are able to create the executable
		try {
			execConfig.getPhpExecutable();
			Assertions.fail("Exception expected");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final IllegalStateException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

	/**
	 * Tests if isUseCache is active per default.
	 * @throws Exception
	 */

	@Test
	@Disabled
	@IgnoreWhen(PhpMissing.class)
	public void testIsUseCacheActive() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleEmptySession();
		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assertions.assertTrue(execConfig.isUseCache());
		execConfig.setUseCache(false);
		Assertions.assertFalse(execConfig.isUseCache());
	}

	// TODO: test additionalPhpParameters
	// TODO: test flushPHPOutput
	// TODO: test temporaryScriptFile

}