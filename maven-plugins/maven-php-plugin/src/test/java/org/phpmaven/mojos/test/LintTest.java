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

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.plugin.build.PhpResources;
import org.phpmaven.plugin.build.PhpTestResources;
import org.phpmaven.test.AbstractTestCase;

/**
 * Testing the lint check.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */

 public class LintTest extends AbstractTestCase {

	 /**
	  * Tests the goal "compile" with simple error within the sources.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSimpleFailure() throws Exception {
		 final MavenSession session = this.createSimpleSession("mojos-lint/check-lint");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 try {
			 resourcesMojo.execute();
			 Assert.fail("Build failure expected");
		 } catch (final MojoExecutionException ex) {
			 Assert.assertEquals("Lint check failures.", ex.getMessage());
		 }
	 }

	 /**
	  * tests the goal "compile" with simple error in test class.
	  * Will expected to work because compile does not look at the test classes.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testCompileOkWithTestFailure() throws Exception {
		 final MavenSession session = this.createSimpleSession("mojos-lint/check-linttests");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
	 }

	 /**
	  * tests the goal "test-compile" with simple error in test class.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSimpletestFailure() throws Exception {
		 final MavenSession session = this.createSimpleSession("mojos-lint/check-linttests");

		 final PhpTestResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpTestResources.class, session,
				 "testResources",
				 new Xpp3Dom("configuration"));
		 try {
			 resourcesMojo.execute();
			 Assert.fail("Build failure expected");
		 } catch (final MojoExecutionException ex) {
			 Assert.assertEquals("Lint check failures.", ex.getMessage());
		 }
	 }

 }
