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

package org.phpmaven.project.features.test;

import java.util.Iterator;

import org.apache.maven.execution.MavenSession;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.project.IPhpFeature;
import org.phpmaven.project.IPhpFeatures;
import org.phpmaven.test.AbstractTestCase;

/**
 * test cases for PHP project support with features.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class BaseTest extends AbstractTestCase {

	/**
	 * Tests if the features class can be created
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testCreation() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);

		// create the features class
		final MavenSession session = this.createSimpleSession(
				"project/features/ok");
		final IPhpFeatures features = factory.lookup(
				IPhpFeatures.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that it is not null
		Assert.assertNotNull(features);
	}

	/**
	 * Tests if the features class can be created
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testOK() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);

		// create the features class
		final MavenSession session = this.createSimpleSession(
				"project/features/ok");
		final IPhpFeatures features = factory.lookup(
				IPhpFeatures.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that it is not null
		Assert.assertNotNull(features);

		final Iterator<IPhpFeature> iter = features.getFeatures().iterator();
		Assert.assertTrue(iter.hasNext());
		final IPhpFeature feature = iter.next();
		Assert.assertFalse(iter.hasNext());
		Assert.assertTrue(feature instanceof TestFoo);

		final Iterator<TestFoo> iter2 = features.getFeatures(TestFoo.class).iterator();
		Assert.assertTrue(iter2.hasNext());
		final TestFoo foo = iter2.next();
		Assert.assertFalse(iter2.hasNext());
		Assert.assertNotNull(foo);
	}

	/**
	 * Tests if the features class can be created
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testFailed() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);

		// create the features class
		final MavenSession session = this.createSimpleSession(
				"project/features/failed");
		final IPhpFeatures features = factory.lookup(
				IPhpFeatures.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that it is not null
		Assert.assertNotNull(features);

		try {
			features.getFeatures();
			Assert.fail("Expected exception not thrown");
		} catch (final IllegalStateException ex) {
			// expected
		}
	}

}