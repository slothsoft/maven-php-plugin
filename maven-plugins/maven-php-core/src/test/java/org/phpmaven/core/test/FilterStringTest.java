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

package org.phpmaven.core.test;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test case for the IComponentFactory class.
 *
 * <p>Test the getBuildConfig methods.</p>
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class FilterStringTest extends AbstractTestCase {

	/**
	 * Tests if the component lookup reads the project build config from parent poms
	 * and overwrites a single value.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentReadsParentProjectConfigOverwrite() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-childoverwrite");
		final File file = factory.filterString(
				session,
				"${project.basedir}/SomeFooBar",
				File.class);
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				file.getAbsolutePath());
	}

	/**
	 * Tests if the filterString method is handling null.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testNull() throws Exception {
		// look up
		final IComponentFactory factory = lookup(IComponentFactory.class);

		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-childoverwrite");
		final File file = factory.filterString(
				session,
				"${fooBarBaz}",
				File.class);
		Assert.assertNull(file);
	}

	/**
	 * Tests if the filterString method is handling failures.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFailure() throws Exception {
		// look up
		final IComponentFactory factory = lookup(IComponentFactory.class);

		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-childoverwrite");
		try {
			factory.filterString(
					session,
					"${project.basedir}/SomeFooBar",
					File[].class);
			Assert.fail("Expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final ExpressionEvaluationException ex) {
			// this exception is expected
		}
		// CHECKSTYLE:ON
	}

}