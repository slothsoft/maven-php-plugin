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

package org.phpmaven.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.artifact.resolver.filter.CumulativeScopeArtifactFilter;
import org.apache.maven.cli.MavenCli;
import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequestPopulator;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.FileUtils;
import org.apache.maven.it.util.ResourceExtractor;
import org.apache.maven.lifecycle.internal.LifecycleDependencyResolver;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuildingResult;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.configurator.ComponentConfigurator;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluator;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.configuration.xml.XmlPlexusConfiguration;
import org.codehaus.plexus.util.ReaderFactory;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.util.graph.transformer.ChainedDependencyGraphTransformer;
import org.eclipse.aether.util.graph.transformer.ConflictMarker;
import org.eclipse.aether.util.graph.transformer.JavaDependencyContextRefiner;
import org.junit.Rule;
import org.junit.Assert;

/**
 * Abstract base class for testing the modules.
 *
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin
 *         Eisengardt</a>
 * @author <a href="mailto:s.schulz@slothsoft.de">Stef Schulz</a>
 * @since 2.0.0
 */

public abstract class AbstractTestCase {

	@Rule
	public MojoRule mojoRule = new MojoRule();

	public PlexusContainer getContainer() {
		return this.mojoRule.getContainer();
	}

	public <T> T lookup(Class<T> componentClass) throws Exception {
		return this.mojoRule.lookup(componentClass);
	}

	public Mojo lookupMojo(String goal, String pluginPom) throws Exception {
		return this.mojoRule.lookupMojo(goal, pluginPom);
	}

	/**
	 * Local repository directory.
	 * 
	 * @return local repos dir
	 * @throws VerificationException thrown on verififcation errors.
	 */
	protected File getLocalReposDir() throws VerificationException {
		final Verifier verifier = new Verifier("foo", true);
		return new File(verifier.localRepo);
	}

	/**
	 * Creates a maven session with given test directory (name relative to package
	 * org/phpmaven/test/projects).
	 *
	 * @param strTestDir the relative folder containing the pom.xml to be used
	 * @return the maven session
	 * @throws Exception thrown on errors
	 */
	protected MavenSession createSimpleSession(final String strTestDir) throws Exception {
		final File testDir = prepareResources(strTestDir);
		final RepositorySystemSession systemSession = null;
		final MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		final MavenExecutionResult result = null;
		final MavenSession session = new MavenSession(getContainer(), systemSession, request, result);
		final MavenProject project = buildProject(testDir);
		session.setCurrentProject(project);
		return session;
	}

	protected <T extends Mojo> T createCurrentConfiguredMojo(Class<T> clazz, MavenSession session, String goal,
			Xpp3Dom config) throws Exception {
		File pluginPom = new File("pom.xml");
		Xpp3Dom pluginPomDom = Xpp3DomBuilder.build(ReaderFactory.newXmlReader(pluginPom));
		String artifactId = pluginPomDom.getChild("artifactId").getValue();
		String groupId = resolveFromRootThenParent(pluginPomDom, "groupId");
		String version = resolveFromRootThenParent(pluginPomDom, "version");
		return createConfiguredMojo(clazz, session, groupId, artifactId, version, goal, config);
	}

	private String resolveFromRootThenParent(Xpp3Dom pluginPomDom, String element) throws Exception {
		Xpp3Dom elementDom = pluginPomDom.getChild(element);
		if (elementDom == null) {
			Xpp3Dom pluginParentDom = pluginPomDom.getChild("parent");
			if (pluginParentDom != null) {
				elementDom = pluginParentDom.getChild(element);
				if (elementDom == null) {
					throw new Exception("unable to determine " + element);
				}
				return elementDom.getValue();
			}
			throw new Exception("unable to determine " + element);
		}
		return elementDom.getValue();
	}

	/**
	 * Creates a mojo
	 * 
	 * @param clazz
	 * @param groupId
	 * @param config
	 * @return mojo
	 */
	private <T extends Mojo> T createConfiguredMojo(Class<T> clazz, MavenSession session, String groupId,
			String artifactId, String version, String goal, Xpp3Dom config) throws Exception {
		final PlexusConfiguration plexusConfig = new XmlPlexusConfiguration(config);
		final T result = clazz.cast(this.mojoRule.lookupMojo(groupId, artifactId, version, goal, plexusConfig));

		final ExpressionEvaluator evaluator = new PluginParameterExpressionEvaluator(session,
				this.mojoRule.newMojoExecution(goal));

		Xpp3Dom configuration = null;
		final Plugin plugin = session.getCurrentProject().getPlugin(groupId + ":" + artifactId);
		if (plugin != null) {
			configuration = (Xpp3Dom) plugin.getConfiguration();
		}
		if (configuration == null) {
			configuration = new Xpp3Dom("configuration");
		}
		configuration = Xpp3Dom.mergeXpp3Dom(this.mojoRule.newMojoExecution(goal).getConfiguration(), configuration);

		final PlexusConfiguration pluginConfiguration = new XmlPlexusConfiguration(configuration);

		getContainer().lookup(ComponentConfigurator.class, "basic").configureComponent(result, pluginConfiguration,
				evaluator, getContainer().getContainerRealm());

		return result;
	}

	private MavenProject buildProject(final File projectDir) throws Exception {
		final File projectFile = new File(projectDir, "pom.xml");
		final ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest();
		buildingRequest.setProcessPlugins(false);
		buildingRequest.setResolveDependencies(false);

		DefaultRepositorySystemSession repositorySession = new DefaultRepositorySystemSession();
		repositorySession.setLocalRepositoryManager(new SimpleLocalRepositoryManager(getLocalReposDir()));
		buildingRequest.setRepositorySession(repositorySession);

		final MavenProject project = this.mojoRule.lookup(ProjectBuilder.class).build(projectFile, buildingRequest)
				.getProject();
		return project;
	}

	/**
	 * Creates a maven session with an empty project pom.
	 * 
	 * @return the maven session
	 * @throws Exception thrown on errors
	 */
	protected MavenSession createSimpleEmptySession() throws Exception {
		return createSimpleSession("empty");
	}

	/**
	 * Creates a maven session with given test directory (name relative to this
	 * class package).
	 *
	 * @param strTestDir the relative folder containing the pom.xml to be used
	 * @return the maven session
	 * @throws Exception thrown on errors
	 */
	protected MavenSession createSessionForPhpMaven(final String strTestDir) throws Exception {
		return createSessionForPhpMaven(strTestDir, false);
	}

	/**
	 * Creates a maven session with given test directory (name relative to this
	 * class package).
	 *
	 * @param strTestDir the relative folder containing the pom.xml to be used
	 * @return the maven session
	 * @throws Exception thrown on errors
	 */
	protected MavenSession createSessionForPhpMaven(final String strTestDir, boolean resolveDependencies)
			throws Exception {
		return createSessionForPhpMaven(strTestDir, resolveDependencies, false);
	}

	protected static final class MavenData {
		ProjectBuildingRequest projectBuildingRequest;
		MavenExecutionRequest executionRequest;
	}

	protected MavenData createProjectBuildingRequest() throws Exception {
		final File localReposFile = this.getLocalReposDir();

		final SimpleLocalRepositoryManager localRepositoryManager = new SimpleLocalRepositoryManager(localReposFile);

		final DefaultRepositorySystemSession repositorySession = new DefaultRepositorySystemSession();

		Map<String, String> systemProperties = new HashMap<>(repositorySession.getSystemProperties());
		for (final Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
			systemProperties.put(entry.getKey().toString(), entry.getValue().toString());
		}
		systemProperties.put("java.version", System.getProperty("java.version"));
		repositorySession.setSystemProperties(systemProperties);

		repositorySession.setDependencyGraphTransformer(
				new ChainedDependencyGraphTransformer(new ConflictMarker(), new JavaDependencyContextRefiner()));
		final MavenExecutionRequest request = new DefaultMavenExecutionRequest();
		final MavenExecutionRequestPopulator populator = this.mojoRule.lookup(MavenExecutionRequestPopulator.class);
		populator.populateDefaults(request);

		final SettingsBuildingRequest settingsRequest = new DefaultSettingsBuildingRequest();
		settingsRequest.setGlobalSettingsFile(MavenCli.DEFAULT_GLOBAL_SETTINGS_FILE);
		settingsRequest.setUserSettingsFile(MavenCli.DEFAULT_USER_SETTINGS_FILE);
		settingsRequest.setSystemProperties(request.getSystemProperties());
		settingsRequest.setUserProperties(request.getUserProperties());
		final SettingsBuilder settingsBuilder = this.mojoRule.lookup(SettingsBuilder.class);
		final SettingsBuildingResult settingsResult = settingsBuilder.build(settingsRequest);
		final MavenExecutionRequestPopulator executionRequestPopulator = this.mojoRule
				.lookup(MavenExecutionRequestPopulator.class);
		executionRequestPopulator.populateFromSettings(request, settingsResult.getEffectiveSettings());

		final ArtifactRepositoryLayout layout = this.mojoRule.lookup(ArtifactRepositoryLayout.class);
		final ArtifactRepositoryPolicy policy = new ArtifactRepositoryPolicy();
		final MavenArtifactRepository repos = new MavenArtifactRepository("local",
				localReposFile.toURI().toURL().toString(), layout, policy, policy);

		request.setLocalRepository(repos);
		request.setSystemProperties(new Properties(System.getProperties()));
		request.getSystemProperties().put("java.version", System.getProperty("java.version"));
		repositorySession.setLocalRepositoryManager(localRepositoryManager);

		final ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest();
		buildingRequest.setLocalRepository(request.getLocalRepository());
		buildingRequest.setRepositorySession(repositorySession);
		buildingRequest.setSystemProperties(request.getSystemProperties());
		buildingRequest.getRemoteRepositories().addAll(request.getRemoteRepositories());
		buildingRequest.setProfiles(request.getProfiles());
		buildingRequest.setActiveProfileIds(request.getActiveProfiles());
		buildingRequest.setProcessPlugins(false);
		buildingRequest.setResolveDependencies(false);

		final MavenData data = new MavenData();
		data.executionRequest = request;
		data.projectBuildingRequest = buildingRequest;

		return data;
	}

	/**
	 * Creates a maven session with given test directory (name relative to this
	 * class package).
	 *
	 * @param strTestDir the relative folder containing the pom.xml to be used
	 * @return the maven session
	 * @throws Exception thrown on errors
	 */
	protected MavenSession createSessionForPhpMaven(final String strTestDir, boolean resolveDependencies,
			boolean processPlugins) throws Exception {
		final File testDir = preparePhpMavenLocalRepos(strTestDir);
		final MavenExecutionResult result = null;
		final MavenData data = this.createProjectBuildingRequest();
		final ProjectBuildingRequest buildingRequest = data.projectBuildingRequest;
		final MavenExecutionRequest request = data.executionRequest;

		final File projectFile = new File(testDir, "pom.xml");
		final MavenProject project = this.mojoRule.lookup(ProjectBuilder.class).build(projectFile, buildingRequest)
				.getProject();

		final MavenSession session = new MavenSession(getContainer(), buildingRequest.getRepositorySession(), request,
				result);
		session.setCurrentProject(project);

		return session;
	}

	/**
	 * Prepares a local repository and installs the php-maven projects.
	 * 
	 * @param strTestDir The local test directory for the project to be tested
	 * @return the file path to the local test installation
	 * @throws Exception
	 */
	private File preparePhpMavenLocalRepos(final String strTestDir) throws Exception {
		final File testDir = prepareResources(strTestDir);
		return testDir;
	}

	private File prepareResources(final String strTestDir) throws IOException {

		final String tempDirPath = System.getProperty("maven.test.tmpdir", System.getProperty("java.io.tmpdir"));
		final File testDir = new File(tempDirPath, "org/phpmaven/test/projects/" + strTestDir);
		// try to delete it 5 times (sometimes it silently fails but succeeds the
		// second invocation)
		for (int i = 0; i < 5; i++) {
			try {
				FileUtils.deleteDirectory(testDir);
			} catch (final IOException ex) {
				if (i == 5)
					throw ex;
			}
		}
		System.out.println(testDir);
		ResourceExtractor.extractResourcePath(getClass(), "/org/phpmaven/test/projects/" + strTestDir,
				new File(tempDirPath), true);

		return testDir;
	}

	protected void resolveProjectDependencies(MavenSession session) throws Exception {
		final List<String> scopesToResolve = new ArrayList<String>();
		scopesToResolve.add(Artifact.SCOPE_COMPILE);
		scopesToResolve.add(Artifact.SCOPE_TEST);
		final List<String> scopesToCollect = new ArrayList<String>();
		final LifecycleDependencyResolver lifeCycleDependencyResolver = this.mojoRule
				.lookup(LifecycleDependencyResolver.class);
		session.getCurrentProject().setArtifacts(null);
		lifeCycleDependencyResolver.resolveProjectDependencies(session.getCurrentProject(), scopesToCollect,
				scopesToResolve, session, false, Collections.<Artifact>emptySet());
		session.getCurrentProject().setArtifactFilter(new CumulativeScopeArtifactFilter(scopesToResolve));
	}

	// TODO [slothsoft]: maybe these belong in util class

	protected void assertIterableCount(Iterable<?> iter, int count) {
		int result = 0;
		for (@SuppressWarnings("unused")
		final Object elm : iter) {
			result++;
		}
		Assert.assertEquals(count, result);
	}

	protected <T> void assertIterableContains(Iterable<T> iter, T element) {
		boolean found = false;
		for (final T elm : iter) {
			if (elm.equals(element)) {
				found = true;
				break;
			}
		}
		if (!found) {
			Assert.fail("Element " + element + " not found.");
		}
	}

}