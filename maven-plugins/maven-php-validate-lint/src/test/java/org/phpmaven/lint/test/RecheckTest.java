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

package org.phpmaven.lint.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.lint.ILintChecker;
import org.phpmaven.lint.ILintExecution;
import org.phpmaven.test.AbstractTestCase;

/**
 * Base test cases for the line check.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.3
 */
public class RecheckTest extends AbstractTestCase {

	/**
	 * Tests if validation is ok.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testValidationOk() throws Exception {
		final MavenSession session = this.createSimpleSession("lint/empty-pom");
		final IComponentFactory factory = lookup(IComponentFactory.class);
		final DefaultLog logger = new DefaultLog(new ConsoleLogger());

		ILintChecker checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(new File(session.getCurrentProject().getBasedir(), "success.php"));
		Iterable<ILintExecution> result = checker.run(logger);
		Assert.assertFalse(result.iterator().hasNext());

		// second check makes use of state db
		checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(new File(session.getCurrentProject().getBasedir(), "success.php"));
		result = checker.run(logger);
		Assert.assertFalse(result.iterator().hasNext());
	}

	/**
	 * Tests if validation is failing on parse errors.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testValidationFailed() throws Exception {
		final MavenSession session = this.createSimpleSession("lint/empty-pom");
		final IComponentFactory factory = lookup(IComponentFactory.class);
		final DefaultLog logger = new DefaultLog(new ConsoleLogger());
		final File failedFile = new File(session.getCurrentProject().getBasedir(), "failed.php");

		ILintChecker checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(failedFile);
		Iterable<ILintExecution> result = checker.run(logger);
		Iterator<ILintExecution> iter = result.iterator();
		Assert.assertTrue(iter.hasNext());
		ILintExecution failure = iter.next();
		Assert.assertFalse(iter.hasNext());
		Assert.assertEquals(failedFile, failure.getFile());
		Assert.assertNotNull(failure.getException());

		// second check makes use of state db
		checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(failedFile);
		result = checker.run(logger);
		iter = result.iterator();
		Assert.assertTrue(iter.hasNext());
		failure = iter.next();
		Assert.assertFalse(iter.hasNext());
		Assert.assertEquals(failedFile, failure.getFile());
		Assert.assertNotNull(failure.getException());
	}

	/**
	 * Tests if validation is failing with succeeding files and parse errors.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testMultiFailure() throws Exception {
		final MavenSession session = this.createSimpleSession("lint/empty-pom");
		final IComponentFactory factory = lookup(IComponentFactory.class);
		final DefaultLog logger = new DefaultLog(new ConsoleLogger());
		final File failedFile = new File(session.getCurrentProject().getBasedir(), "multiple/failed.php");
		final File failed2File = new File(session.getCurrentProject().getBasedir(), "multiple/failed2.php");

		ILintChecker checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(new File(session.getCurrentProject().getBasedir(), "multiple/success.php"));
		checker.addFileToCheck(failedFile);
		checker.addFileToCheck(failed2File);
		Iterable<ILintExecution> result = checker.run(logger);
		Map<File, ILintExecution> failures = new HashMap<File, ILintExecution>();
		for (final ILintExecution failure : result) {
			failures.put(failure.getFile(), failure);
		}
		Assert.assertEquals(2, failures.size());
		Assert.assertNotNull(failures.get(failedFile));
		Assert.assertNotNull(failures.get(failed2File));

		// second check makes use of state db
		checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(new File(session.getCurrentProject().getBasedir(), "multiple/success.php"));
		checker.addFileToCheck(failedFile);
		checker.addFileToCheck(failed2File);
		result = checker.run(logger);
		failures = new HashMap<File, ILintExecution>();
		for (final ILintExecution failure : result) {
			failures.put(failure.getFile(), failure);
		}
		Assert.assertEquals(2, failures.size());
		Assert.assertNotNull(failures.get(failedFile));
		Assert.assertNotNull(failures.get(failed2File));

		// third check to test override of the state db with new source file
		final FileOutputStream fos = new FileOutputStream(failedFile);
		fos.write("<?php echo 'FOO';".getBytes());
		fos.flush();
		fos.close();
		failedFile.setLastModified(failedFile.lastModified() + 1000); // ensure the last modified is changed and causes a recheck
		checker = factory.lookup(ILintChecker.class, IComponentFactory.EMPTY_CONFIG, session);
		checker.addFileToCheck(new File(session.getCurrentProject().getBasedir(), "multiple/success.php"));
		checker.addFileToCheck(failedFile);
		checker.addFileToCheck(failed2File);
		result = checker.run(logger);
		failures = new HashMap<File, ILintExecution>();
		for (final ILintExecution failure : result) {
			failures.put(failure.getFile(), failure);
		}
		Assert.assertEquals(1, failures.size());
		Assert.assertNull(failures.get(failedFile));
		Assert.assertNotNull(failures.get(failed2File));
	}

}