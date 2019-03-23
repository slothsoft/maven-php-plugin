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

package org.phpmaven.phpunit.test;

import org.apache.maven.execution.MavenSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.phpunit.IPhpunitConfiguration;
import org.phpmaven.phpunit.IPhpunitSupport;
import org.phpmaven.test.AbstractTestCase;

/**
 * test cases for PHPUNIT support.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class BaseTest extends AbstractTestCase {

	/**
	 * Tests if the phpunit support can be created.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	public void testPhpunitCreation() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("phpunit/empty-pom");
		final IPhpunitConfiguration config = factory.lookup(
				IPhpunitConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		// assert that it is not null
		Assertions.assertNotNull(config);
		// assert that we are able to create the phpunit support
		final IPhpunitSupport phpunit = config.getPhpunitSupport("3.3.0");
		Assertions.assertNotNull(phpunit);
	}

}