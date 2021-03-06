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

package org.phpmaven.archetypes.test;

import org.apache.maven.it.Verifier;
import org.junit.Ignore;
import org.junit.Test;
import org.phpmaven.test.it.AbstractTestCase;

/**
 * Tests the php5-web-archetype.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
@Ignore
public class WebTest extends AbstractTestCase {

	/**
	 * tests the goal "package" after installing the archetype.
	 *
	 * @throws Exception
	 */

	@Test
	public void testPackage() throws Exception {
		final Verifier verifier = this.getPhpMavenVerifier("archetypes/web");
		this.installPhpParentPom();
		this.installArchetypes();

		verifier.addCliOption("-DarchetypeGroupId=de.slothsoft.phpmaven");
		verifier.addCliOption("-DarchetypeArtifactId=php5-web-archetype");
		verifier.addCliOption("-DarchetypeVersion=2.0.3-SNAPSHOT");
		verifier.addCliOption("-DgroupId=org.sample");
		verifier.addCliOption("-DartifactId=my-app");
		verifier.addCliOption("-Dversion=0.0.1-SNAPSHOT");
		verifier.addCliOption("-DinteractiveMode=false");

		verifier.executeGoal("archetype:generate");
		verifier.verifyErrorFreeLog();

		verifier.assertFilePresent("my-app/pom.xml");
		verifier.assertFilePresent("my-app/src/main/php/lib/org/phpmaven/library/LibraryClass.php");
		verifier.assertFilePresent("my-app/src/main/php/webapp/css/Css.php");
		verifier.assertFilePresent("my-app/src/main/php/webapp/javascript/JavaScript.php");
		verifier.assertFilePresent("my-app/src/main/php/webapp/index.php");
		verifier.assertFilePresent("my-app/src/main/resources/webapp/css/style.css");
		verifier.assertFilePresent("my-app/src/main/resources/webapp/javascript/jquery-1.7.min.js");
		verifier.assertFilePresent("my-app/src/site/apt/index.apt");
		verifier.assertFilePresent("my-app/src/site/site.xml");
		verifier.assertFilePresent("my-app/src/test/php/org/phpmaven/library/LibraryClassTest.php");

		final Verifier verifier2 = this.getVerifierWithoutPrepare("archetypes/web");
		verifier2.executeGoal("package");
		verifier2.verifyErrorFreeLog();
		verifier2.assertFilePresent("my-app/target/my-app-0.0.1-SNAPSHOT.phar");
	}

}