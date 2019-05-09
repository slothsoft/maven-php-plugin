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
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.core.test.srv.ISomeService;
import org.phpmaven.test.AbstractTestCase;

/**
 * Test case for the IComponentFactory class.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.1
 */
public class LookupServiceTest extends AbstractTestCase {

	/**
	 * Tests if the service lookup.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testService() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the session
		final MavenSession session = createSimpleEmptySession();
		// lookup the sample
		final ISomeService[] services = factory.getServiceImplementations(ISomeService.class, session);
		Assert.assertNotNull(services);
		Assert.assertEquals(2, services.length);
		Assert.assertTrue("foo".equals(services[0].getServiceName()) || "foo".equals(services[1].getServiceName()));
		Assert.assertTrue("bar".equals(services[0].getServiceName()) || "bar".equals(services[1].getServiceName()));
	}

}