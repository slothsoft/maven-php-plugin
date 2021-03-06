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

package org.phpmaven.phpunit;

import java.io.File;

/**
 * ENtry of phpunit requests.
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 2.0.0
 */
public interface IPhpunitEntry {
    
    /**
     * Entry types.
     */
    enum EntryType {
        /** A single test case file. */
        FILE,
        /** A test case folder. */
        FOLDER
    }
    
    /**
     * Returns the type of this entry.
     * @return entry type.
     */
    EntryType getType();
    
    /**
     * returns the java file.
     * @return java file (either file or diectory).
     */
    File getFile();

}
