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
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.core.test.comp.ISomeComponent;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test case for the IComponentFactory class.
 *
 * <p>Test the lookup methods.</p>
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class LookupTest extends AbstractTestCase {

	/**
	 * Tests if the component lookup failes with unknown classes.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentLookupFailed() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleEmptySession();
		// lookup the sample
		try {
			factory.lookup(LookupTest.class, (Xpp3Dom) null, session);
			Assert.fail("Expected exception not thrown");
			// CHECKSTYLE:OFF
			// checkstyle does not like empty catches
		} catch (final ComponentLookupException ex) {
			// ignore; we expect this exception
		}
		// CHECKSTYLE:ON
	}

	/**
	 * Tests if the component lookup initializes the defaults.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentLookupDefaults() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleEmptySession();
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "fooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("default-foo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

	/**
	 * Tests if the component lookup initializes the defaults.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentLookupDefaultsArray() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleEmptySession();
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom[]) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "fooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("default-foo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentReadsProjectConfig() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig");
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("SpecialFoo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentReadsProjectConfigArray() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig");
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom[]) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("SpecialFoo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms.
	 *
	 * @throws Exception thrown on errors
	 */
	public void testComponentReadsParentProjectConfig() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-child");
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("SpecialFoo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentReadsParentProjectConfigArray() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-child");
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom[]) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("SpecialFoo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

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
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("OtherFoo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms
	 * and overwrites a single value.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentReadsParentProjectConfigOverwriteArray() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-childoverwrite");
		// lookup the sample
		final ISomeComponent component = factory.lookup(ISomeComponent.class, (Xpp3Dom[]) null, session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assert.assertEquals("OtherFoo", component.getFoo());
		Assert.assertEquals("default-bar", component.getBar());
	}

}