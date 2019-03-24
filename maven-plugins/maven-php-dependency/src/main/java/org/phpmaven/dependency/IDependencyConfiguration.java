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

package org.phpmaven.dependency;

import java.io.File;

import org.phpmaven.core.BuildPluginConfiguration;
import org.phpmaven.core.IComponentFactory;

/**
 * A php dependency configuration.
 * 
 * <p>
 * This configuration is used to manipulate the dependencies of phpmaven. The classic way
 * is to extract dependencies to the folder target/php-deps. With dependencies configuration
 * you can choose an alternative way from the following lists:</p>
 * <dl>
 * <dt>classic</dt>
 * <dd>Simply extract the dependency directly into target/php-deps {@link IActionClassic}</dd>
 * <dt>extract</dt>
 * <dd>Extract the phar file or a sub-folder of it to an alternative folder {@link IActionExtract}</dd>
 * <dt>include</dt>
 * <dd>Put the phar file itself or a sub folder within it to the include path (no extraction at all) {@link IActionInclude}</dd>
 * <dt>pear</dt>
 * <dd>use the pear installer for installation of the phar. Requires a phpmaven pear project. {@link IActionPear}</dd>
 * <dt>ignore</dt>
 * <dd>Ignores the dependency while preparing your project. Maven will check if the dependency is legal but
 * wont't try to install or extract the dependency. This would require to manually setup your dependencies
 * in a bootstrap file. {@link IActionIgnore}</dd>
 * </dl>
 * 
 * <p>
 * Create an instance via {@link IComponentFactory}.
 * </p>
 * 
 * <p>
 * NOTICE: The dependency cannot be used to pass additional dependencies. If you specify dependencies that are
 * not known they are ignored with a warning.
 * </p>
 * 
 * <p>
 * Configuration of the php dependencies can be done via plugin configuration. Example of a configuration
 * via build plugin:<br>
 * <pre>
 * &lt;build&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;plugins&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;plugin&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.phpmaven&lt;/groupId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;maven-php-dependency&lt;/artifactId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;configuration&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;dependencies&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;dependency&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.mygroup&lt;/groupId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;MyLib&lt;/artifactId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;actions&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;include/&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/actions&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/dependency&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/dependencies&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/configuration&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/plugin&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;/plugins&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &lt;/build&gt;<br>
 * </pre>
 * <p>
 * This example will set the dependency org.mygroup:MyLib to be placed on the include path rather than being extracted.
 * </p>
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 2.0.1
 */
@BuildPluginConfiguration(groupId = "de.slothsoft.phpmaven", artifactId = "maven-php-dependency")
public interface IDependencyConfiguration {
    
    /**
     * Returns the dependencies declarations.
     * @return dependencies.
     */
    Iterable<IDependency> getDependencies();
    
    /**
     * A bootstrap file that will be invoked on bootstrap dependencies.
     * @return bootstrap php file.
     */
    File getBootstrapFile();
    
    /**
     * Returns the default actions of this project
     * @return default actions
     */
    Iterable<IAction> getDefaults();

}
