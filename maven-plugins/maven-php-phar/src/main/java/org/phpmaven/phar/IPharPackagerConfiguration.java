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

package org.phpmaven.phar;

import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.exec.IPhpExecutableConfiguration;

/**
 * Phar tooling.
 * 
 * <p>
 * This tooling helps to create, package and extract phar archives. It uses an available php
 * executable to perform any phar action.
 * </p>
 * 
 * <p>
 * Create an instance via {@link IComponentFactory}.
 * </p>
 * 
 * <p>
 * Configuration of the phar tooling can be done via either the goal you are executing
 * or via plugin configuration. Example of a configuration via build plugin:<br>
 * <pre>
 * &lt;build&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;plugins&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;plugin&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.phpmaven&lt;/groupId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;maven-php-phar&lt;/artifactId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;configuration&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;executableConfig&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;executable&gt;/some/php.exe&lt;/executable&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/executableConfig&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/configuration&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/plugin&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;/plugins&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &lt;/build&gt;<br>
 * </pre>
 * <p>
 * This example will use an alternative php executable for all phar actions.
 * </p>
 * 
 * <p>
 * Available options:
 * </p>
 * 
 * <table border="1" summary="Commands">
 * <tr><th>Name</th><th>Command line option</th><th>Property</th><th>Default</th><th>Description</th></tr>
 * <tr>
 *   <td>executableConfig</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>Alternative configuration used every time the php executable is invoked for
 *       phar related actions. See {@link IPhpExecutableConfiguration} for details.
 *   </td>
 * </tr>
 * <tr>
 *   <td>packager</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>Name of the phar packager to be used. Defaults to "PHP_EXE".
 *   </td>
 * </tr>
 * <tr>
 *   <td>pharConfig</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>Alternative phar configuration used every time a phar is built.
 *       See {@link IPharPackagingRequest} for details.
 *   </td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 2.0.0
 */
public interface IPharPackagerConfiguration {

    /**
     * Returns the packager that will be used.
     * 
     * @return the phar packager
     * 
     * @throws PlexusConfigurationException thrown if there is a configuration problem.
     * @throws ComponentLookupException thrown if there is a configuration problem.
     */
    IPharPackager getPharPackager()  throws PlexusConfigurationException, ComponentLookupException;
    
    /**
     * Returns the name of the packager.
     * @return packager name
     */
    String getPackager();
    
    /**
     * Sets the name of the packager.
     * @param packager packager.
     */
    void setPackager(String packager);

}
