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

import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.core.test.comp.ISomeComponentHint;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test case for the IComponentFactory class.
 *
 * <p>Test the lookup methods with role-hints and without build configuration.</p>
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.1
 */
public class LookupWithoutConfigTest extends AbstractTestCase {

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
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"without-config",
				(Xpp3Dom) null,
				session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertNull(component.getFooBar());
		Assert.assertNull(component.getFoo());
		Assert.assertNull(component.getBar());
	}

	/**
	 * Tests if the component lookup initializes the defaults.
	 *
	 * @throws Exception thrown on errors
	 */
	public void testComponentLookupDefaultsArray() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleSession("core/pom-with-buildconfig");
		// lookup the sample
		final ISomeComponentHint component = factory.lookup(
				ISomeComponentHint.class,
				"without-config",
				(Xpp3Dom[]) null,
				session);
		Assert.assertNotNull(component);
		// test defaults
		Assert.assertNull(component.getFooBar());
		Assert.assertNull(component.getFoo());
		Assert.assertNull(component.getBar());
	}

}