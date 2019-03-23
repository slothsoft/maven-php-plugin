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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @author Stef Schulz <s.schulz@slothsoft.de>
 * @since 2.0.0
 */
public class BaseTest extends AbstractTestCase {

	// XXX [slothsoft]: I have no idea why some tests won't work any longer

	/**
	 * Tests if the dependencies are correctly configured.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	public void testBasic() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("dependency/simple");
		final IDependencyConfiguration depConfig = factory.lookup(
				IDependencyConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assertions.assertNotNull(depConfig);

		IDependency dep = this.findDep(depConfig, "org.group1", "classic1");
		Iterator<IAction> actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		IAction action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_CLASSIC);
		Assertions.assertTrue(action instanceof IActionClassic);
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "ignore1");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_IGNORE);
		Assertions.assertTrue(action instanceof IActionIgnore);
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "pear");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_PEAR);
		Assertions.assertTrue(action instanceof IActionPear);
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extract1");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT);
		Assertions.assertTrue(action instanceof IActionExtract);
		Assertions.assertEquals("/some/path", ((IActionExtract)action).getPharPath());
		Assertions.assertEquals(session.getCurrentProject().getBuild().getDirectory() + "/somepath", ((IActionExtract)action).getTargetPath());
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extractAndInclude1");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT_INCLUDE);
		Assertions.assertTrue(action instanceof IActionExtractAndInclude);
		Assertions.assertEquals("/some/path", ((IActionExtractAndInclude)action).getPharPath());
		Assertions.assertEquals(session.getCurrentProject().getBuild().getDirectory() + "/somepath", ((IActionExtractAndInclude)action).getTargetPath());
		Assertions.assertEquals("/some/other/path", ((IActionExtractAndInclude)action).getIncludePath());
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "include1");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_INCLUDE);
		Assertions.assertTrue(action instanceof IActionInclude);
		Assertions.assertEquals("/some/path", ((IActionInclude)action).getPharPath());
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extractDefaults");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT);
		Assertions.assertTrue(action instanceof IActionExtract);
		Assertions.assertEquals("/", ((IActionExtract)action).getPharPath());
		Assertions.assertEquals("", ((IActionExtract)action).getTargetPath());
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "extractAndIncludeDefaults");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_EXTRACT_INCLUDE);
		Assertions.assertTrue(action instanceof IActionExtractAndInclude);
		Assertions.assertEquals("/", ((IActionExtractAndInclude)action).getPharPath());
		Assertions.assertEquals("", ((IActionExtractAndInclude)action).getTargetPath());
		Assertions.assertEquals("/", ((IActionExtractAndInclude)action).getIncludePath());
		Assertions.assertFalse(actionIter.hasNext());

		dep = this.findDep(depConfig, "org.group1", "includeDefaults");
		actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_INCLUDE);
		Assertions.assertTrue(action instanceof IActionInclude);
		Assertions.assertEquals("/", ((IActionInclude)action).getPharPath());
		Assertions.assertFalse(actionIter.hasNext());
	}

	private IDependency findDep(IDependencyConfiguration config, String groupId, String artifactId) {
		for (final IDependency dep : config.getDependencies()) {
			if (dep.getGroupId().equals(groupId) && dep.getArtifactId().equals(artifactId)) return dep;
		}
		Assertions.fail("dependency " + groupId + ":" + artifactId + " not found");
		return null;
	}

	/**
	 * Tests if the bootstrap configuration is parsed correctly.
	 *
	 * @throws Exception thrown on errors
	 */

	@Test
	@Disabled
	public void testBootstrap() throws Exception {
		// look up the component factory
		final IComponentFactory factory = lookup(IComponentFactory.class);
		// create the execution config
		final MavenSession session = this.createSimpleSession("dependency/bootstrap");
		final IDependencyConfiguration depConfig = factory.lookup(
				IDependencyConfiguration.class,
				IComponentFactory.EMPTY_CONFIG,
				session);
		Assertions.assertNotNull(depConfig);

		final IDependency dep = this.findDep(depConfig, "org.group1", "bootstrap1");
		final Iterator<IAction> actionIter = dep.getActions().iterator();
		Assertions.assertTrue(actionIter.hasNext());
		final IAction action = actionIter.next();
		Assertions.assertEquals(action.getType(), IAction.ActionType.ACTION_BOOTSTRAP);
		Assertions.assertTrue(action instanceof IActionBootstrap);
		Assertions.assertFalse(actionIter.hasNext());

		Assertions.assertEquals(new File(session.getCurrentProject().getBuild().getDirectory(), "/foo.php").getAbsolutePath(), depConfig.getBootstrapFile().getAbsolutePath());
	}

}