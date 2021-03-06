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
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.phpmaven.core.ExecutionUtils;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutableConfiguration;
import org.phpmaven.phpexec.library.PhpException;
import org.phpmaven.phpexec.library.PhpVersion;
import org.phpmaven.test.AbstractTestCase;
import org.phpmaven.test.IgnoreIfPhpMissing;

/**
 * test cases for PHP version detection.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class GetVersionTest extends AbstractTestCase {

	@Rule
	public IgnoreIfPhpMissing rule = new IgnoreIfPhpMissing();

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testGetVersion4() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php4.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php4").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php4");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);

		Assert.assertEquals(PhpVersion.PHP4, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion5() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php5.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php5").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php5");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);


		Assert.assertEquals(PhpVersion.PHP5, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion6() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php6.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php6").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php6");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);


		Assert.assertEquals(PhpVersion.PHP6, execConfig.getPhpExecutable().getVersion());
	}
	

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion7() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php7.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php7").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php7");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);


		Assert.assertEquals(PhpVersion.PHP7, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersionUnknown() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpUnknown.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "phpUnknown").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpUnknown");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);


		Assert.assertEquals(PhpVersion.UNKNOWN, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersionIllegal() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpIllegal.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "phpIllegal").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpIllegal");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);


		try {
			execConfig.getPhpExecutable().getVersion();
			Assert.fail("Expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final PhpException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion4NotCached() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php4.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php4").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php4");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);
		execConfig.setUseCache(false);


		Assert.assertEquals(PhpVersion.PHP4, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion5NotCached() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php5.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php5").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php5");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);
		execConfig.setUseCache(false);


		Assert.assertEquals(PhpVersion.PHP5, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion6NotCached() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php6.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php6").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php6");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);
		execConfig.setUseCache(false);


		Assert.assertEquals(PhpVersion.PHP6, execConfig.getPhpExecutable().getVersion());
	}
	

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersion7NotCached() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/php7.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "php7").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/php7");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);
		execConfig.setUseCache(false);


		Assert.assertEquals(PhpVersion.PHP7, execConfig.getPhpExecutable().getVersion());
	}


	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersionUnknownNotCached() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpUnknown.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "phpUnknown").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpUnknown");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);
		execConfig.setUseCache(false);


		Assert.assertEquals(PhpVersion.UNKNOWN, execConfig.getPhpExecutable().getVersion());
	}

	/**
	 * Tests if the version can be detected.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Ignore
	public void testGetVersionIllegalNotCached() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("exec/version");
		final Xpp3Dom dom = new Xpp3Dom("configuration");
		final Xpp3Dom exec = new Xpp3Dom("executable");
		if (ExecutionUtils.isWindows()) {
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpIllegal.cmd");
		} else {
			// try chmod
			final String[] cmd = {
					"chmod",
					"777",
					new File(session.getCurrentProject().getBasedir(), "phpIllegal").getAbsolutePath()};
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			exec.setValue(session.getCurrentProject().getBasedir() + "/phpIllegal");
		}
		dom.addChild(exec);

		final IPhpExecutableConfiguration execConfig = factory.lookup(
				IPhpExecutableConfiguration.class,
				new Xpp3Dom[]{dom},
				session);
		execConfig.setUseCache(false);


		try {
			execConfig.getPhpExecutable().getVersion();
			Assert.fail("Expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final PhpException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

}