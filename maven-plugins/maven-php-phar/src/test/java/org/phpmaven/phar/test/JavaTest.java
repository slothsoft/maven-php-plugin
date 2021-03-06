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

package org.phpmaven.phar.test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutableConfiguration;
import org.phpmaven.phar.IPharPackager;
import org.phpmaven.phar.IPharPackagerConfiguration;
import org.phpmaven.phar.IPharPackagingRequest;
import org.phpmaven.phpexec.library.IPhpExecutable;
import org.phpmaven.test.AbstractTestCase;
import org.phpmaven.test.IgnoreIfPhpMissing;

/**
 * test cases for the Java-variant PHAR packager.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class JavaTest extends AbstractTestCase {

	@Rule
	public IgnoreIfPhpMissing rule = new IgnoreIfPhpMissing();

	/**
	 * Tests if the execution configuration can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testECCreation() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("phar/simple");
		final IPharPackagerConfiguration pharConfig = factory.lookup(
				IPharPackagerConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that it is not null
		Assert.assertNotNull(pharConfig);
		// assert that we are able to create the packager
		pharConfig.setPackager("JAVA");
		final IPharPackager exec = pharConfig.getPharPackager();
		Assert.assertNotNull(exec);
		// assert that we are able to create the request
		final IPharPackagingRequest request = factory.lookup(
				IPharPackagingRequest.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assert.assertNotNull(request);
	}

	/**
	 * Tests the compression of large phars (large file flag only).
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testLargePhar() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("phar/large-phar");
		final File pharFile = new File(session.getCurrentProject().getBasedir(), "phar1.phar");
		delete(pharFile);
		final IPharPackagerConfiguration pharConfig = factory.lookup(
				IPharPackagerConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		pharConfig.setPackager("JAVA");
		final IPharPackager exec = pharConfig.getPharPackager();
		final IPharPackagingRequest request = factory.lookup(
				IPharPackagingRequest.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		// prepare the request
		request.setStub("die('HELLO STUB!');");
		request.addFile("/some/file.php", new File(session.getCurrentProject().getBasedir(), "testphar.php"));
		request.addDirectory("/", new File(session.getCurrentProject().getBasedir(), "phar1"));
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir(), "target").getAbsolutePath(),
				request.getTargetDirectory().getAbsolutePath());
		request.setTargetDirectory(session.getCurrentProject().getBasedir());
		request.setFilename(pharFile.getName());

		// package
		final DefaultLog logger = new DefaultLog(new ConsoleLogger());
		exec.packagePhar(request, logger);
		Assert.assertTrue(pharFile.exists());

		final IPhpExecutableConfiguration phpConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		phpConfig.setAdditionalPhpParameters("-d suhosin.executor.include.whitelist=\"phar\"");
		// check the phar
		final IPhpExecutable phpExec = phpConfig.getPhpExecutable();
		Assert.assertEquals("INSIDE FILE.PHP\n", phpExec.execute(new File(
				session.getCurrentProject().getBasedir(), "testphar.php")));
	}

	/**
	 * Tests if the execution configuration can be created
	 * with an unknown executable set.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testPackage() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("phar/simple");
		final File pharFile = new File(session.getCurrentProject().getBasedir(), "phar1.phar");
		delete(pharFile);
		final IPharPackagerConfiguration pharConfig = factory.lookup(
				IPharPackagerConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// TODO As soon as java packager supports the other methods (list etc.) change it.
		final IPharPackager exec = pharConfig.getPharPackager();
		pharConfig.setPackager("JAVA");
		final IPharPackager javaExec = pharConfig.getPharPackager();
		final IPharPackagingRequest request = factory.lookup(
				IPharPackagingRequest.class,
				IComponentFactory.EMPTY_CONFIG,
				session);

		// prepare the request
		request.setStub("die('HELLO STUB!');");
		request.addFile("/some/file.php", new File(session.getCurrentProject().getBasedir(), "testphar.php"));
		request.addDirectory("/", new File(session.getCurrentProject().getBasedir(), "phar1"));
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir(), "target").getAbsolutePath(),
				request.getTargetDirectory().getAbsolutePath());
		request.setTargetDirectory(session.getCurrentProject().getBasedir());
		request.setFilename(pharFile.getName());

		// package
		final DefaultLog logger = new DefaultLog(new ConsoleLogger());
		javaExec.packagePhar(request, logger);
		Assert.assertTrue(pharFile.exists());

		final IPhpExecutableConfiguration phpConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		phpConfig.setAdditionalPhpParameters("-d suhosin.executor.include.whitelist=\"phar\"");
		// check the phar
		final IPhpExecutable phpExec = phpConfig.getPhpExecutable();
		Assert.assertEquals("INSIDE FILE.PHP\n", phpExec.execute(new File(
				session.getCurrentProject().getBasedir(), "testphar.php")));

		// read stub
		// XXX: PHP seems to add an " ?>" at the end of the stub. Thats not a problem at all.
		//      Have a look if this is a feature and not a "bug" that may be fixed in later php versions.
		Assert.assertEquals("<?php die('HELLO STUB!'); __HALT_COMPILER(); ?>\n", exec.readStub(pharFile, logger));

		// read contents
		final Set<String> files = new HashSet<String>();
		for (final String file : exec.listFiles(pharFile, logger)) {
			files.add(file);
		}
		Assert.assertEquals(3, files.size());
		Assert.assertTrue(files.contains("/data/some.txt") || files.contains("\\data\\some.txt"));
		Assert.assertTrue(files.contains("/includes1/file.php") || files.contains("\\includes1\\file.php"));
		Assert.assertTrue(files.contains("/some/file.php") || files.contains("\\some\\file.php"));

		// test extraction
		final File someTxt = new File(session.getCurrentProject().getBasedir(), "testdir/data/some.txt");
		final File includePhp = new File(session.getCurrentProject().getBasedir(), "testdir/includes1/file.php");
		final File someFilePhp = new File(session.getCurrentProject().getBasedir(), "testdir/some/file.php");
		delete(someTxt);
		delete(includePhp);
		delete(someFilePhp);
		exec.extractPharTo(
				pharFile,
				new File(session.getCurrentProject().getBasedir(), "testdir"),
				logger);
		Assert.assertTrue(someTxt.exists());
		Assert.assertTrue(includePhp.exists());
		Assert.assertTrue(someFilePhp.exists());
	}

	private void delete(final File someTxt) {
		if (someTxt.exists()) {
			someTxt.delete();
		}
		Assert.assertFalse(someTxt.exists());
	}

}