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
import java.io.FileInputStream;

import org.apache.maven.execution.MavenSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.plugin.build.FileHelper;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test extraction with reverse index jars
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.0
 */
@Disabled
 public class PostVisitJar extends AbstractTestCase {

	 /**
	  * tests the extraction of a reverse-index jar.
	  *
	  * @throws Exception
	  */

	 @Test
	 public void testExtract() throws Exception {
		 final MavenSession session = this.createSimpleSession("post-visit");
		 final File pvJar = new File(session.getCurrentProject().getBasedir(), "postvisit.jar");
		 final File fileDir = new File(session.getCurrentProject().getBasedir(), "files");

		 FileHelper.unjar(new FileInputStream(pvJar), fileDir.getParentFile());

		 Assertions.assertTrue(fileDir.exists());
		 Assertions.assertTrue(new File(fileDir, "folderA/folderC/fileC.txt").exists());
	 }

 }