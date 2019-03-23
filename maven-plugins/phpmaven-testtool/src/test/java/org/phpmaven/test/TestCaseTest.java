package org.phpmaven.test;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.PlexusContainer;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

/**
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 */

public class TestCaseTest extends AbstractTestCase {

	@Test
	public void testGetLocalReposDir() throws Exception {
		final File localReposDir = getLocalReposDir();
		Assert.assertNotNull(localReposDir);
		Assert.assertTrue(localReposDir.exists());
	}

	@Test
	public void testCreateSimpleEmptySession() throws Exception {
		final MavenSession session = createSimpleEmptySession();
		Assert.assertNotNull(session);
	}

	@Test
	public void testGetContainer() throws Exception {
		final PlexusContainer container = getContainer();
		Assert.assertNotNull(container);
	}
}
