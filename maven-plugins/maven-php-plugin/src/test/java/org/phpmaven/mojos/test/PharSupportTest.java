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

import org.apache.maven.execution.MavenSession;
import org.apache.maven.monitor.logging.DefaultLog;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.phar.IPharPackagerConfiguration;
import org.phpmaven.plugin.build.PhpPhar;
import org.phpmaven.plugin.build.PhpResources;
import org.phpmaven.test.AbstractTestCase;

/**
 * Testing the phar support.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
@Disabled
 public class PharSupportTest extends AbstractTestCase {


	 /**
	  * tests the phar packaging with simple content
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testGoalTestWithSimplePhar() throws Exception {
		 final MavenSession session = this.createSimpleSession("mojos-phar/phar-support");

		 final PhpResources resourcesMojo = this.createConfiguredMojo(
				 PhpResources.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "2.0.3-SNAPSHOT",
				 "resources",
				 new Xpp3Dom("configuration"));
		 resourcesMojo.execute();

		 final PhpPhar pharMojo = this.createConfiguredMojo(
				 PhpPhar.class, session,
				 "de.slothsoft.phpmaven", "maven-php-plugin", "2.0.3-SNAPSHOT",
				 "phar",
				 new Xpp3Dom("configuration"));
		 pharMojo.execute();

		 final File phar = new File(session.getCurrentProject().getBasedir(), "target/phar-simple-0.0.1.phar");
		 Assertions.assertTrue(phar.exists());

		 // list files
		 final IPharPackagerConfiguration pharConfig = lookup(IComponentFactory.class).lookup(
				 IPharPackagerConfiguration.class, IComponentFactory.EMPTY_CONFIG, session);
		 final Iterable<String> files = pharConfig.getPharPackager().listFiles(phar, new DefaultLog(new ConsoleLogger()));

		 assertIterableCount(files, 1);
		 assertIterableContains(files, File.separatorChar + "MyClass.php");
	 }

 }
