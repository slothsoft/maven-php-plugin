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
package org.phpmaven.httpd.config;

/**
 * A line in config file (single for directives or multiple for sections.
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 2.0.1
 */
class ConfigFileLine implements IConfigFileLine {
    
    /**
     * The text content of the line.
     */
    private String text;
    
    /**
     * Constructor.
     * @param text the text
     */
    public ConfigFileLine(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return this.text + "\n";
    }
    
}
