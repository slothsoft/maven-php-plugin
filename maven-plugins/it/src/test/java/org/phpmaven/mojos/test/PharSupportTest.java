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

package org.phpmaven.mojos.test;

import java.io.File;
import java.util.List;

import org.apache.maven.it.Verifier;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.phpmaven.test.it.AbstractTestCase;

/**
 * Testing the phar support.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
@Ignore
public class PharSupportTest extends AbstractTestCase {


	/**
	 * tests the goal "install" with simple phar file.
	 *
	 * @throws Exception
	 */

	@Test
	public void testGoalTestWithSimplePhar() throws Exception {
		final Verifier verifier = this.getPhpMavenVerifier("mojos-phar/phar-simple");

		// delete the pom from previous runs
		verifier.deleteArtifact("de.slothsoft.phpmaven.test", "phar-simple", "0.0.1", "pom");
		verifier.deleteArtifact("de.slothsoft.phpmaven.test", "phar-simple", "0.0.1", "phar");

		// execute testing
		verifier.executeGoal("install");

		// verify no error was thrown
		verifier.verifyErrorFreeLog();

		// reset the streams
		verifier.resetStreams();

		verifier.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-simple", "0.0.1", "pom");
		verifier.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-simple", "0.0.1", "phar");
	}

	/**
	 * tests the goal "install" with simple phar file.
	 *
	 * @throws Exception
	 */

	@Test
	public void testListFiles() throws Exception {
		final Verifier verifier = this.getPhpMavenVerifier("mojos-phar/phar-simple");

		// delete the pom from previous runs
		verifier.deleteArtifact("de.slothsoft.phpmaven.test", "phar-simple", "0.0.1", "pom");
		verifier.deleteArtifact("de.slothsoft.phpmaven.test", "phar-simple", "0.0.1", "phar");

		// execute testing
		verifier.executeGoal("package");

		// verify no error was thrown
		verifier.verifyErrorFreeLog();

		// reset the streams
		verifier.resetStreams();

		verifier.setAutoclean(false);
		verifier.addCliOption("-Dphar=target/phar-simple-0.0.1.phar");
		verifier.executeGoal("de.slothsoft.phpmaven:maven-php-plugin:list-phar-files");
		@SuppressWarnings("unchecked")
		final List<String> lines = verifier.loadFile(verifier.getBasedir(), verifier.getLogFileName(), false);
		boolean found = false;
		for (final String line : lines) {
			if (line.startsWith("[INFO] " + File.separatorChar + "MyClass.php")) {
				found = true;
			}
		}

		// verify no error was thrown
		verifier.verifyErrorFreeLog();

		// reset the streams
		verifier.resetStreams();

		Assert.assertTrue(found);
	}

	/**
	 * tests the goal "install" with autoprepend file.
	 *
	 * @throws Exception
	 */

	@Test
	public void testGoalTestWithAutoprepend() throws Exception {
		final Verifier verifier = this.getPhpMavenVerifier("mojos-phar/phar-autoprepend");

		// delete the pom from previous runs
		verifier.deleteArtifact("de.slothsoft.phpmaven.test", "phar-autoprepend", "0.0.1", "pom");
		verifier.deleteArtifact("de.slothsoft.phpmaven.test", "phar-autoprepend", "0.0.1", "phar");

		// execute testing
		verifier.executeGoal("install");

		// verify no error was thrown
		verifier.verifyErrorFreeLog();

		// reset the streams
		verifier.resetStreams();

		verifier.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-autoprepend", "0.0.1", "pom");
		verifier.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-autoprepend", "0.0.1", "phar");
	}

	/**
	 * tests the goal "install" with dependencies to another phar.
	 *
	 * @throws Exception
	 */

	@Test
	public void testGoalTestWithDependencies() throws Exception {
		final Verifier verifierDep1 = this.getPhpMavenVerifier("mojos-phar/phar-with-dep1");

		// delete the pom from previous runs
		verifierDep1.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep1", "0.0.1", "pom");
		verifierDep1.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep1", "0.0.1", "phar");

		// execute testing
		verifierDep1.executeGoal("install");

		// verify no error was thrown
		verifierDep1.verifyErrorFreeLog();

		// reset the streams
		verifierDep1.resetStreams();

		verifierDep1.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep1", "0.0.1", "pom");
		verifierDep1.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep1", "0.0.1", "phar");

		final Verifier verifierDep2 = this.getPhpMavenVerifier("mojos-phar/phar-with-dep2");

		// delete the pom from previous runs
		verifierDep2.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep2", "0.0.1", "pom");
		verifierDep2.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep2", "0.0.1", "phar");

		// execute testing
		verifierDep2.executeGoal("install");

		// verify no error was thrown
		verifierDep2.verifyErrorFreeLog();

		// reset the streams
		verifierDep2.resetStreams();

		verifierDep2.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep2", "0.0.1", "pom");
		verifierDep2.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep2", "0.0.1", "phar");

		verifierDep2.assertFilePresent("target/php-deps/MyClass.php");
	}

	/**
	 * tests the goal "install" with dependencies to another phar that provides a folder structure.
	 *
	 * @throws Exception
	 */

	@Test
	public void testGoalTestWithDependenciesInFolders() throws Exception {
		final Verifier verifierDep1 = this.getPhpMavenVerifier("mojos-phar/phar-with-dep1-folders");

		// delete the pom from previous runs
		verifierDep1.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep1-folders", "0.0.1", "pom");
		verifierDep1.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep1-folders", "0.0.1", "phar");

		// execute testing
		verifierDep1.executeGoal("install");

		// verify no error was thrown
		verifierDep1.verifyErrorFreeLog();

		// reset the streams
		verifierDep1.resetStreams();

		verifierDep1.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep1-folders", "0.0.1", "pom");
		verifierDep1.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep1-folders", "0.0.1", "phar");

		final Verifier verifierDep2 = this.getPhpMavenVerifier("mojos-phar/phar-with-dep2-folders");

		// delete the pom from previous runs
		verifierDep2.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep2-folders", "0.0.1", "pom");
		verifierDep2.deleteArtifact("de.slothsoft.phpmaven.test", "phar-with-dep2-folders", "0.0.1", "phar");

		// execute testing
		verifierDep2.executeGoal("install");

		// verify no error was thrown
		verifierDep2.verifyErrorFreeLog();

		// reset the streams
		verifierDep2.resetStreams();

		verifierDep2.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep2-folders", "0.0.1", "pom");
		verifierDep2.assertArtifactPresent("de.slothsoft.phpmaven.test", "phar-with-dep2-folders", "0.0.1", "phar");

		verifierDep2.assertFilePresent("target/php-deps/folderA/MyClassA.php");
		verifierDep2.assertFilePresent("target/php-deps/folderB/MyClassB.php");
	}

}
