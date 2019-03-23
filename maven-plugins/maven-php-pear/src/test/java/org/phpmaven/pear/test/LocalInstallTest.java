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

package org.phpmaven.pear.test;

import java.io.File;

import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.pear.IMavenPearUtility;
import org.phpmaven.pear.IPearConfiguration;
import org.phpmaven.test.AbstractTestCase;

/**
 * test cases for installing pear modules from maven repositories.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */

@Disabled
public class LocalInstallTest extends AbstractTestCase {

	// XXX [slothsoft]: ignoring tests is not okay

	/**
	 * Gets the maven session.
	 * @return gets the maven session.
	 * @throws Exception thrown on pear errors.
	 */
	private MavenSession getSession() throws Exception {
		// create the execution config
		final MavenSession session = this.createSessionForPhpMaven("pear/local-install");
		final ArtifactRepositoryLayout layout = lookup(ArtifactRepositoryLayout.class);
		final ArtifactRepositoryPolicy policy = new ArtifactRepositoryPolicy();
		final MavenArtifactRepository phpMavenRepos = new MavenArtifactRepository(
				"php-maven",
				"http://repos.php-maven.org/releases",
				layout,
				policy,
				policy);
		session.getRequest().getRemoteRepositories().add(phpMavenRepos);
		return session;
	}

	/**
	 * Tests if the a pear package can be installed via maven repository.
	 *
	 * @throws Exception thrown on errors
	 */
	@Test
	public void ignoretestLocalInstall() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		final MavenSession session = getSession();
		final IPearConfiguration pearConfig = factory.lookup(
				IPearConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that we are able to create the util
		final DefaultLog logger = new DefaultLog(new ConsoleLogger());
		final IMavenPearUtility util = pearConfig.getUtility(logger);

		util.installPear(false);
		util.installFromMavenRepository("net.php", "XML_fo2pdf", "0.98");

		Assertions.assertTrue(new File(util.getPhpDir(), "XML/fo2pdf.php").exists());
		Assertions.assertTrue(new File(util.getDocDir(), "XML_fo2pdf/README.fo2pdf").exists());
		Assertions.assertTrue(new File(util.getDocDir(), "XML_fo2pdf/simple.fo").exists());
	}

}