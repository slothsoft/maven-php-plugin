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

package org.phpmaven.dependency.test;

import java.io.File;
import java.util.Iterator;

import org.apache.maven.execution.MavenSession;
import org.junit.Assert;
import org.junit.Test;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.dependency.IAction;
import org.phpmaven.dependency.IActionBootstrap;
import org.phpmaven.dependency.IActionClassic;
import org.phpmaven.dependency.IActionExtract;
import org.phpmaven.dependency.IActionExtractAndInclude;
import org.phpmaven.dependency.IActionIgnore;
import org.phpmaven.dependency.IActionInclude;
import org.phpmaven.dependency.IActionPear;
import org.phpmaven.dependency.IDependency;
import org.phpmaven.dependency.IDependencyConfiguration;
import org.phpmaven.test.AbstractTestCase;

/**
 * test cases for dependency management.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */
public class BaseTest extends AbstractTestCase {


	/**
	 * Tests if the dependencies are correctly configured.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testBasic() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("dependency/simple");
		final IDependencyConfiguration depConfig = factory.lookup(
				IDependencyConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assert.assertNotNull(depConfig);

		IDependency dep = this.findDep(depConfig, "org.group1", "classic1");
		Iterator<IAction> actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		IAction action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_CLASSIC);
		Assert.assertTrue(action instanceof IActionClassic);
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "ignore1");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_IGNORE);
		Assert.assertTrue(action instanceof IActionIgnore);
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "pear");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_PEAR);
		Assert.assertTrue(action instanceof IActionPear);
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extract1");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT);
		Assert.assertTrue(action instanceof IActionExtract);
		Assert.assertEquals("/some/path", ((IActionExtract)action).getPharPath());
		Assert.assertEquals(session.getCurrentProject().getBuild().getDirectory() + "/somepath", ((IActionExtract)action).getTargetPath());
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extractAndInclude1");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT_INCLUDE);
		Assert.assertTrue(action instanceof IActionExtractAndInclude);
		Assert.assertEquals("/some/path", ((IActionExtractAndInclude)action).getPharPath());
		Assert.assertEquals(session.getCurrentProject().getBuild().getDirectory() + "/somepath", ((IActionExtractAndInclude)action).getTargetPath());
		Assert.assertEquals("/some/other/path", ((IActionExtractAndInclude)action).getIncludePath());
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "include1");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_INCLUDE);
		Assert.assertTrue(action instanceof IActionInclude);
		Assert.assertEquals("/some/path", ((IActionInclude)action).getPharPath());
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extractDefaults");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT);
		Assert.assertTrue(action instanceof IActionExtract);
		Assert.assertEquals("/", ((IActionExtract)action).getPharPath());
		Assert.assertEquals("", ((IActionExtract)action).getTargetPath());
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extractAndIncludeDefaults");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT_INCLUDE);
		Assert.assertTrue(action instanceof IActionExtractAndInclude);
		Assert.assertEquals("/", ((IActionExtractAndInclude)action).getPharPath());
		Assert.assertEquals("", ((IActionExtractAndInclude)action).getTargetPath());
		Assert.assertEquals("/", ((IActionExtractAndInclude)action).getIncludePath());
		Assert.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "includeDefaults");
		actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_INCLUDE);
		Assert.assertTrue(action instanceof IActionInclude);
		Assert.assertEquals("/", ((IActionInclude)action).getPharPath());
		Assert.assertFalse(actionIter.hasNext());
	}

	private IDependency findDep(IDependencyConfiguration config, String groupId, String artifactId) {
		for (final IDependency dep : config.getDependencies()) {
			if (dep.getGroupId().equals(groupId) && dep.getArtifactId().equals(artifactId)) return dep;
		}
		Assert.fail("dependency " + groupId + ":" + artifactId + " not found");
		return null;
	}

	/**
	 * Tests if the bootstrap configuration is parsed correctly.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	public void testBootstrap() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("dependency/bootstrap");
		final IDependencyConfiguration depConfig = factory.lookup(
				IDependencyConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assert.assertNotNull(depConfig);

		final IDependency dep = this.findDep(depConfig, "org.group1", "bootstrap1");
		final Iterator<IAction> actionIter = dep.getActions().iterator();
		Assert.assertTrue(actionIter.hasNext());
		final IAction action = actionIter.next();
		Assert.assertEquals(action.getType(), IAction.ActionType.ACTION_BOOTSTRAP);
		Assert.assertTrue(action instanceof IActionBootstrap);
		Assert.assertFalse(actionIter.hasNext());

		Assert.assertEquals(new File(session.getCurrentProject().getBuild().getDirectory(), "/foo.php").getAbsolutePath(), depConfig.getBootstrapFile().getAbsolutePath());
	}

}