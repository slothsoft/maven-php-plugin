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
import java.io.StringReader;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.phar.IPharPackagerConfiguration;
import org.phpmaven.plugin.phar.ConvertPharMojo;
import org.phpmaven.test.AbstractTestCase;

/**
 * Testing the phar support with dependency extraction.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */

 public class ConvertPharTest extends AbstractTestCase {

	 /**
	  * tests the goal "convert-phar"
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoal() throws Exception {
		 final MavenSession session = this.createSimpleSession("mojos-phar/convert-phar");
		 final File phar = new File(session.getCurrentProject().getBasedir(), "phar-with-dep1-folders-0.0.1.phar");
		 final File phar2 = new File(session.getCurrentProject().getBasedir(), "phar-with-dep1-folders-0.0.1-2.phar");
		 final File zip = new File(session.getCurrentProject().getBasedir(), "phar-with-dep1-folders-0.0.1.zip");
		 final File jar = new File(session.getCurrentProject().getBasedir(), "phar-with-dep1-folders-0.0.1.jar");

		 Assert.assertTrue(phar.exists());
		 Assert.assertFalse(phar2.exists());
		 Assert.assertFalse(zip.exists());
		 Assert.assertFalse(jar.exists());

		 Xpp3Dom config = Xpp3DomBuilder.build(new StringReader(
				 "<configuration>" +
						 "<from>"+phar.getAbsolutePath()+"</from>" +
						 "<to>"+zip.getAbsolutePath()+"</to>" +
				 "</configuration>"));
		 ConvertPharMojo convertMojo = createCurrentConfiguredMojo(
				 ConvertPharMojo.class, session,
				 "convert-phar",
				 config);
		 convertMojo.execute();

		 config = Xpp3DomBuilder.build(new StringReader(
				 "<configuration>" +
						 "<from>"+zip.getAbsolutePath()+"</from>" +
						 "<to>"+jar.getAbsolutePath()+"</to>" +
				 "</configuration>"));
		 convertMojo = createCurrentConfiguredMojo(
				 ConvertPharMojo.class, session,
				 "convert-phar",
				 config);
		 convertMojo.execute();

		 Assert.assertTrue(jar.exists());

		 config = Xpp3DomBuilder.build(new StringReader(
				 "<configuration>" +
						 "<from>"+jar.getAbsolutePath()+"</from>" +
						 "<to>"+phar2.getAbsolutePath()+"</to>" +
				 "</configuration>"));
		 convertMojo = createCurrentConfiguredMojo(
				 ConvertPharMojo.class, session,
				 "convert-phar",
				 config);
		 convertMojo.execute();

		 Assert.assertTrue(phar2.exists());

		 // list files
		 final IPharPackagerConfiguration pharConfig = lookup(IComponentFactory.class).lookup(
				 IPharPackagerConfiguration.class, IComponentFactory.EMPTY_CONFIG, session);
		 final Iterable<String> files = pharConfig.getPharPackager().listFiles(phar2, new DefaultLog(new ConsoleLogger()));

		 assertIterableCount(files, 3);
		 assertIterableContains(files, File.separatorChar + "folderA" + File.separatorChar + "MyClassA.php");
		 assertIterableContains(files, File.separatorChar + "folderB" + File.separatorChar + "MyClassB.php");
		 assertIterableContains(files, File.separatorChar + "META-INF" + File.separatorChar + "MANIFEST.MF");
	 }

 }
