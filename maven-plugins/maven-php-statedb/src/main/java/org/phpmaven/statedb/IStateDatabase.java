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

package org.phpmaven.statedb;

import java.io.File;
import java.io.Serializable;

import org.phpmaven.core.IComponentFactory;

/**
 * Helper to generate persistent states during multiple invocations of maven lifecycle executions.
 * 
 * <p>
 * Create an instance via {@link IComponentFactory}.
 * </p>
 * 
 * <p>
 * Configuration is done via build plugin. Example of a configuration via build plugin:<br>
 * <pre>
 * &lt;build&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;plugins&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;plugin&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.phpmaven&lt;/groupId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;maven-php-statedb&lt;/artifactId&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;configuration&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;dbfile&gt;${project.build.directory}/phpmaven.alternative.state.db&lt;/dbfile&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/configuration&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;/plugin&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;/plugins&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &lt;/build&gt;<br>
 * </pre>
 * <p>
 * This example will use an alternative filename of the state database.
 * </p>
 * 
 * <p>
 * Available options:
 * </p>
 * 
 * <table border="1" summary="Commands">
 * <tr><th>Name</th><th>Command line option</th><th>Property</th><th>Default</th><th>Description</th></tr>
 * <tr>
 *   <td>dbfile</td>
 *   <td>-</td>
 *   <td>-</td>
 *   <td>${project.build.directory}/phpmaven.state.db</td>
 *   <td>The file to put the persistent state</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 2.0.1
 */
public interface IStateDatabase {
    
    /**
     * Returns the database file.
     * @return database file
     */
    File getDbfile();
    
    /**
     * Deletes the database.
     */
    void delete();
    
    /**
     * Reloads the database.
     */
    void reload();
    
    /**
     * Returns an element from persistent database
     * @param groupId The group id
     * @param artifact The artifact Id
     * @param key The key for the stored element
     * @param clazz Class of the returned element.
     * @param <T> element type
     * @return element or null if it was not found in database.
     */
    <T extends Serializable> T get(String groupId, String artifact, String key, Class<T> clazz);
    
    /**
     * Sets the data and saves the database to disc
     * @param groupId The group id
     * @param artifactId The artifact Id
     * @param key The key for the stored element
     * @param data The element data
     */
    void set(String groupId, String artifactId, String key, Serializable data);
    
    /**
     * Removes given key from database.
     * @param groupId The group id
     * @param artifactId The artifact Id
     * @param key The key for the stored element
     */
    void remove(String groupId, String artifactId, String key);
    
}
