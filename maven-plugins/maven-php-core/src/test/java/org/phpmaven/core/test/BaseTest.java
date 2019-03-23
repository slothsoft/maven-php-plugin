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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.test.AbstractTestCase;

/**
 * Base test cases for the component factory.
 *
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.0
 */
public class BaseTest extends AbstractTestCase {

	/**
	 * Tests if the Component factory can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	public void testCFCreation() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// assert that it is not null
		Assertions.assertNotNull(factory);
	}

	/**
	 * Tests if the empty dom configuration does not contain anything.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	public void testConfigEmpty() throws Exception {
		// assert that it is not null and empty
		Assertions.assertNotNull(IComponentFactory.EMPTY_CONFIG);
		Assertions.assertEquals(0, IComponentFactory.EMPTY_CONFIG.length);
	}

}