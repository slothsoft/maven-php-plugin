package org.phpmaven.test;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.configuration.PlexusConfiguration;

/**
 * @author Stef Schulz <s.schulz@slothsoft.de>
 */

class MojoTestCase extends AbstractMojoTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	public PlexusContainer getContainer() {
		return super.getContainer();
	}

	@Override
	public <T> T lookup(Class<T> componentClass) throws Exception {
		return super.lookup(componentClass);
	}

	@Override
	public Mojo lookupMojo(String goal, String pluginPom) throws Exception {
		return super.lookupMojo(goal, pluginPom);
	}

	@Override
	public Mojo lookupEmptyMojo(String goal, String pluginPom) throws Exception {
		return super.lookupEmptyMojo(goal, pluginPom);
	}

	@Override
	public Mojo lookupMojo(String goal, File pom) throws Exception {
		return super.lookupMojo(goal, pom);
	}

	@Override
	public Mojo lookupEmptyMojo(String goal, File pom) throws Exception {
		return super.lookupEmptyMojo(goal, pom);
	}

	@Override
	public Mojo lookupMojo(String groupId, String artifactId, String version, String goal,
			PlexusConfiguration pluginConfiguration) throws Exception {
		return super.lookupMojo(groupId, artifactId, version, goal, pluginConfiguration);
	}

	@Override
	public Mojo lookupConfiguredMojo(MavenProject project, String goal) throws Exception {
		return super.lookupConfiguredMojo(project, goal);
	}

	@Override
	public Mojo lookupConfiguredMojo(MavenSession session, MojoExecution execution)
			throws Exception, ComponentConfigurationException {
		return super.lookupConfiguredMojo(session, execution);
	}

	@Override
	public MavenSession newMavenSession(MavenProject project) {
		return super.newMavenSession(project);
	}

	@Override
	public MojoExecution newMojoExecution(String goal) {
		return super.newMojoExecution(goal);
	}

	@Override
	public Object lookup(String componentKey) throws Exception {
		return super.lookup(componentKey);
	}

	@Override
	public Object lookup(String role, String roleHint) throws Exception {
		return super.lookup(role, roleHint);
	}

	@Override
	public <T> T lookup(Class<T> componentClass, String roleHint) throws Exception {
		return super.lookup(componentClass, roleHint);
	}

}
