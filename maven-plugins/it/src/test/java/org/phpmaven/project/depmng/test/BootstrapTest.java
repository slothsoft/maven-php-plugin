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

package org.phpmaven.project.depmng.test;

import java.util.List;

import org.apache.maven.it.Verifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.test.it.AbstractTestCase;

/**
 * test cases for PHP project support with dependency management.
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.0
 */
@Disabled
public class BootstrapTest extends AbstractTestCase {

	/**
	 * Tests if the bootstrap.php is invoked
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testBootstrap() throws Exception {
		// look up the component factory
		this.installPhpmavenProjectToRepos("maven-php-plugin");
		this.installPhpParentPom();

		Verifier verifier = this.getPhpMavenVerifier("project/depmng/lib1");

		verifier.executeGoal("install");
		verifier.verifyErrorFreeLog();
		verifier.resetStreams();
		verifier.assertArtifactPresent("org.phpmaven.test", "lib1", "0.0.1", "pom");
		verifier.assertArtifactPresent("org.phpmaven.test", "lib1", "0.0.1", "phar");

		verifier = this.getPhpMavenVerifier("project/depmng/proj1");

		verifier.executeGoal("install");
		verifier.verifyErrorFreeLog();
		verifier.resetStreams();
		verifier.assertArtifactPresent("org.phpmaven.test", "proj1", "0.0.1", "pom");
		verifier.assertArtifactPresent("org.phpmaven.test", "proj1", "0.0.1", "phar");

		verifier = this.getPhpMavenVerifier("project/depmng/bproj");

		verifier.addCliOption("-X");
		verifier.executeGoal("compile");
		verifier.verifyErrorFreeLog();
		verifier.resetStreams();

		// check for bootstrap invocation
		@SuppressWarnings("unchecked")
		final List<String> lines = verifier.loadFile(verifier.getBasedir() + "/target/php-deps", "foo.txt", false);
		boolean found1 = false;
		boolean found2 = false;
		for (final String line : lines) {
			if (line.contains("string(5) \"proj1\"")) {
				found1 = true;
			}
			if (line.contains("string(4) \"lib1\"")) {
				found2 = true;
			}
		}
		Assertions.assertTrue(found1);
		Assertions.assertTrue(found2);
	}

}