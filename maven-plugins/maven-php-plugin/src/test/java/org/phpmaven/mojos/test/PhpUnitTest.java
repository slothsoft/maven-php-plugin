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

import java.io.StringReader;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.plugin.build.PhpExtractDeps;
import org.phpmaven.plugin.build.PhpResources;
import org.phpmaven.plugin.build.PhpTest;
import org.phpmaven.plugin.build.PhpTestExtractDeps;
import org.phpmaven.plugin.build.PhpTestResources;
import org.phpmaven.plugin.php.PhpUnitTestfileWalker;
import org.phpmaven.test.AbstractTestCase;

/**
 * Testcase for php-maven mojos being present.
 *
 * Tests: http://maven.apache.org/plugin-developers/plugin-testing.html
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
@Disabled
 public class PhpUnitTest extends AbstractTestCase {

	 /**
	  * tests the goal "test" with simple autoloader (autoprepend file).
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithAutoprependFile() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-autoprepend");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with bootstrap file (passing phpunit options).
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithBootstrapFile() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-bootstrap");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final Xpp3Dom config = Xpp3DomBuilder.build(new StringReader(
				 "<configuration>" +
						 "<phpUnitArguments>--bootstrap maven-autoloader.php</phpUnitArguments>" +
				 "</configuration>"));
		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 config);
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with bootstrap file (passing phpunit options).
	  *
	  * @see http://trac.php-maven.org/ticket/59
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithBootstrap2File() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-bootstrap2");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with simple test.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithTests() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-oktests");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with simple test.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWith2Tests() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-oktests-multiple");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" without any test.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithNoTests() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-notests");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" without any test; complaining.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithNoTestsFailing() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-notests");
		 session.getUserProperties().setProperty("failIfNoTests", "true");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 try {
			 test.execute();
			 Assertions.fail("Build failure expected");
		 } catch (final MojoFailureException ex) {
			 Assertions.assertEquals(PhpUnitTestfileWalker.FAIL_ON_NO_TEST_TEXT, ex.getMessage());
		 }
	 }

	 /**
	  * tests the goal "test" with failing tests and skip tests set to true.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestFailingSkipped() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-failing");
		 session.getUserProperties().setProperty("skipTests", "true");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with failing tests and skip tests set to true.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestFailingSkipped2() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-failing");
		 session.getUserProperties().setProperty("maven.test.skip", "true");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with failing tests and ignore failures set to true.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestFailingIgnored() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-failing");
		 session.getUserProperties().setProperty("maven.test.failure.ignore", "true");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 test.execute();
	 }

	 /**
	  * tests the goal "test" with failing tests; complaining.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestFailing() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-phpunit/test-failing");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = this.createConfiguredMojo(
				 PhpTestResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = this.createConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = this.createConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpTest test = this.createConfiguredMojo(
				 PhpTest.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "0.9.0-SNAPSHOT",
				 "test",
				 new Xpp3Dom("configuration"));
		 try {
			 test.execute();
			 Assertions.fail("Expected exception not thrown");
		 } catch (final MojoExecutionException ex) {
			 Assertions.assertEquals("Test failures", ex.getMessage());
		 }
	 }


 }
