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
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.core.test.comp.ISomeComponentHint;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test case for the IComponentFactory class.
 *
 * <p>Test the lookup methods with role-hints.</p>
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class LookupHintTest extends AbstractTestCase {

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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "fooBar-1").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("default-foo-1", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "fooBar-1").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("default-foo-1", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentReadsParentProjectConfig() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-child");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("OtherFoo", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
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
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint1",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("OtherFoo", component.getFoo());
		Assertions.assertEquals("default-bar-1", component.getBar());
	}

	/**
	 * Tests if the component lookup initializes the defaults.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentLookupDefaults2() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleEmptySession();
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "fooBar-2").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("default-foo-2", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup initializes the defaults.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentLookupDefaults2Array() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleEmptySession();
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "fooBar-2").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("default-foo-2", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentReadsProjectConfig2() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentReadsProjectConfig2Array() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentReadsParentProjectConfig2() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-child");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentReadsParentProjectConfig2Array() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-child");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("SpecialFoo", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms
	 * and overwrites a single value.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testComponentReadsParentProjectConfigOverwrite2() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-childoverwrite");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("OtherFoo", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

	/**
	 * Tests if the component lookup reads the project build config from parent poms
	 * and overwrites a single value.
	 *
	 * @throws Exception thrown on errors
	 * @since 2.0.1
	 */

	@Test
	public void testComponentReadsParentProjectConfigOverwrite2Array() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig-childoverwrite");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"hint2",
				(Xpp3Dom[]) null,
				session);
		Assertions.assertNotNull(component);
		// test defaults
		Assertions.assertEquals(
				new File(session.getCurrentProject().getBasedir().getAbsolutePath(), "SomeFooBar").getAbsolutePath(),
				component.getFooBar().getAbsolutePath());
		Assertions.assertEquals("OtherFoo", component.getFoo());
		Assertions.assertEquals("default-bar-2", component.getBar());
	}

}