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

import org.apache.commons.io.FileUtils;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.phpmaven.plugin.build.PhpExtractDeps;
import org.phpmaven.plugin.build.PhpResources;
import org.phpmaven.plugin.build.PhpTestExtractDeps;
import org.phpmaven.plugin.build.PhpTestResources;
import org.phpmaven.plugin.report.PhpDocumentor;
import org.phpmaven.plugin.report.PhpUnit;
import org.phpmaven.plugin.report.PhpUnitCoverage;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test the site generation.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
@Ignore
 public class SiteTest extends AbstractTestCase {

	 /**
	  * tests the goal "site" with a project containing all default reports.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSiteOnClean() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-sites/site-all");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = createCurrentConfiguredMojo(
				 PhpTestResources.class, session,
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = createCurrentConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = createCurrentConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpUnit phpUnit = createCurrentConfiguredMojo(
				 PhpUnit.class, session,
				 "phpunit",
				 new Xpp3Dom("configuration"));
		 phpUnit.execute();

		 final PhpDocumentor phpdocumentor = createCurrentConfiguredMojo(
				 PhpDocumentor.class, session,
				 "phpdocumentor",
				 new Xpp3Dom("configuration"));
		 phpdocumentor.execute();

		 final PhpUnitCoverage coverage = createCurrentConfiguredMojo(
				 PhpUnitCoverage.class, session,
				 "phpunit-coverage",
				 new Xpp3Dom("configuration"));
		 coverage.execute();

		 // phpdocumentor report
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor/index.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor/packages.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor/default/_MyClass.php.html").exists());

		 // phpunit-coverage report
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/coverage.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/index.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/classes.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/classes_MyClass.php.html").exists());

		 // test report
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/report.html").exists());
	 }


	 /**
	  * tests the goal "site" with a project containing all default reports.
	  * Tests if the phpunit report is only respected once.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSiteDuplicateTests() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-sites/site-all");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = createCurrentConfiguredMojo(
				 PhpTestResources.class, session,
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = createCurrentConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = createCurrentConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpUnit phpUnit = createCurrentConfiguredMojo(
				 PhpUnit.class, session,
				 "phpunit",
				 new Xpp3Dom("configuration"));
		 phpUnit.execute();

		 final PhpDocumentor phpdocumentor = createCurrentConfiguredMojo(
				 PhpDocumentor.class, session,
				 "phpdocumentor",
				 new Xpp3Dom("configuration"));
		 phpdocumentor.execute();

		 final PhpUnitCoverage coverage = createCurrentConfiguredMojo(
				 PhpUnitCoverage.class, session,
				 "phpunit-coverage",
				 new Xpp3Dom("configuration"));
		 coverage.execute();

		 // second execution
		 phpUnit.execute();
		 phpdocumentor.execute();
		 coverage.execute();

		 final String content = FileUtils.readFileToString(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/report.html"));

		 // <a name="Summary"></a><p>[<a href="#Summary">Zusammenfassung</a>] [<a href="#Package_List">Pakete</a>] [<a href="#Test_Cases">Testf&#xe4;lle</a>]</p><br />
		 // <table border="0" class="bodyTable"><tr class="a"><th>Tests</th><th>Fehler</th><th>Fehlschl&#xe4;ge</th><th>Ausgelassen</th><th>Erfolgsrate</th><th>Zeit</th></tr><tr class="b"><td>1</td><td>0</td><td>0</td><td>0</td><td>100%</td><td>0,024</td></tr></table><br /><p>Hinweis: Fehlschl&#xe4;ge werden erwartet und durch Behauptungen &#xfc;berpr&#xfc;ft w&#xe4;hrend Fehler unerwartet sind.</p><br /></div><div class="section"><h2>Pakete<a name="Pakete"></a></h2><a name="Package_List"></a><p>[<a href="#Summary">Zusammenfassung</a>] [<a href="#Package_List">Pakete</a>] [<a href="#Test_Cases">Testf&#xe4;lle</a>]</p><br /><table border="0" class="bodyTable"><tr class="a"><th>Paket</th><th>Tests</th><th>Fehler</th><th>Fehlschl&#xe4;ge</th><th>Ausgelassen</th><th>Erfolgsrate</th><th>Zeit</th></tr><tr class="b"><td><a href="#"></a></td><td>1</td><td>0</td><td>0</td><td>0</td><td>100%</td><td>0,024</td></tr></table><br /><p>Hinweis: Die Paketstatistiken werden nicht rekursiv berechnet, es werden lediglich die Ergebnisse aller enthaltenen Tests aufsummiert.</p><div class="section"><h3></h3><a name="a"></a><table border="0" class="bodyTable"><tr class="a"><th></th><th>Klasse</th><th>Tests</th><th>Fehler</th><th>Fehlschl&#xe4;ge</th><th>Ausgelassen</th><th>Erfolgsrate</th><th>Zeit</th></tr><tr class="b"><td><a href="#"><img src="images/icon_success_sml.gif" alt="" /></a></td><td><a href="#"></a></td><td>0</td><td>0</td><td>0</td><td>0</td><td>0%</td><td>0,012</td></tr><tr class="a"><td><a href="#FooTest"><img src="images/icon_success_sml.gif" alt="" /></a></td><td><a href="#FooTest">FooTest</a></td><td>1</td><td>0</td><td>0</td><td>0</td><td>100%</td><td>0,012</td></tr></table></div><br /></div><div class="section"><h2>Testf&#xe4;lle<a name="Testflle"></a></h2><a name="Test_Cases"></a><p>[<a href="#Summary">Zusammenfassung</a>] [<a href="#Package_List">Pakete</a>] [<a href="#Test_Cases">Testf&#xe4;lle</a>]</p><div class="section"><h3>FooTest<a name="FooTest"></a></h3><a name="FooTest"></a><table border="0" class="bodyTable"><tr class="b"><td><img src="images/icon_success_sml.gif" alt="" /></td><td>testFoo</td><td>0,012</td></tr></table></div><br /></div>

		 Assert.assertFalse(content.matches("(?s).*<a name=\"Summary\"><\\/a><p>\\[<a href=\"#Summary\">[^<]+<\\/a>\\] \\[<a href=\"#Package_List\">[^<]+<\\/a>\\] \\[<a href=\"#Test_Cases\">[^<]+<\\/a>\\]<\\/p><br \\/><table border=\"0\" class=\"bodyTable\"><tr class=\"a\"><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><\\/tr><tr class=\"b\"><td>2<\\/td>(?s).*"));
		 Assert.assertTrue(content.matches("(?s).*<a name=\"Summary\"><\\/a><p>\\[<a href=\"#Summary\">[^<]+<\\/a>\\] \\[<a href=\"#Package_List\">[^<]+<\\/a>\\] \\[<a href=\"#Test_Cases\">[^<]+<\\/a>\\]<\\/p><br \\/><table border=\"0\" class=\"bodyTable\"><tr class=\"a\"><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><\\/tr><tr class=\"b\"><td>1<\\/td>(?s).*"));
	 }

	 /**
	  * tests the goal "site" with a clover xml report.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSiteClover() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-sites/site-all");
		 session.getUserProperties().setProperty("outputClover", "true");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = createCurrentConfiguredMojo(
				 PhpTestResources.class, session,
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = createCurrentConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = createCurrentConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpUnitCoverage coverage = createCurrentConfiguredMojo(
				 PhpUnitCoverage.class, session,
				 "phpunit-coverage",
				 new Xpp3Dom("configuration"));
		 coverage.execute();

		 // clover xml
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/phpunit-reports/clover.xml").exists());
	 }

	 /**
	  * tests the goal "site" with a bootstrap script.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSiteBootstrap() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-sites/site-all");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = createCurrentConfiguredMojo(
				 PhpTestResources.class, session,
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = createCurrentConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = createCurrentConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpUnit phpUnit = createCurrentConfiguredMojo(
				 PhpUnit.class, session,
				 "phpunit",
				 new Xpp3Dom("configuration"));
		 phpUnit.execute();

		 final PhpUnitCoverage coverage = createCurrentConfiguredMojo(
				 PhpUnitCoverage.class, session,
				 "phpunit-coverage",
				 new Xpp3Dom("configuration"));
		 coverage.execute();

		 final String content = FileUtils.readFileToString(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/report.html"));
		 Assert.assertTrue(content.matches("(?s).*<a name=\"Summary\"><\\/a><p>\\[<a href=\"#Summary\">[^<]+<\\/a>\\] \\[<a href=\"#Package_List\">[^<]+<\\/a>\\] \\[<a href=\"#Test_Cases\">[^<]+<\\/a>\\]<\\/p><br \\/><table border=\"0\" class=\"bodyTable\"><tr class=\"a\"><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><th>[^<]+<\\/th><\\/tr><tr class=\"b\"><td>1<\\/td>(?s).*"));

		 // phpunit-coverage report
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/coverage.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/index.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/classes.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/phpunit/classes_MyClass.php.html").exists());
	 }

	 /**
	  * tests the goal "site" with a project containing all default reports.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testSitePhpdocPear() throws Exception {
		 final MavenSession session = this.createSessionForPhpMaven("mojos-sites/site-phpdoc-pear");

		 final PhpResources resourcesMojo = createCurrentConfiguredMojo(
				 PhpResources.class, session,
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();
		 final PhpTestResources testResourcesMojo = createCurrentConfiguredMojo(
				 PhpTestResources.class, session,
				 "testResources",
				 new Xpp3Dom("configuration"));
		 testResourcesMojo.execute();

		 this.resolveProjectDependencies(session);
		 final PhpExtractDeps extractDepsMojo = createCurrentConfiguredMojo(
				 PhpExtractDeps.class, session,
				 "extractDependencies",
				 new Xpp3Dom("configuration"));
		 extractDepsMojo.execute();
		 final PhpTestExtractDeps extractTestDepsMojo = createCurrentConfiguredMojo(
				 PhpTestExtractDeps.class, session,
				 "extractTestDependencies",
				 new Xpp3Dom("configuration"));
		 extractTestDepsMojo.execute();

		 final PhpDocumentor phpdocumentor = createCurrentConfiguredMojo(
				 PhpDocumentor.class, session,
				 "phpdocumentor",
				 new Xpp3Dom("configuration"));
		 phpdocumentor.execute();

		 // phpdocumentor report
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor/index.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor/packages.html").exists());
		 Assert.assertTrue(new File(session.getCurrentProject().getBasedir(), "target/site/apidocs/phpdocumentor/default/_MyClass.php.html").exists());
	 }

 }