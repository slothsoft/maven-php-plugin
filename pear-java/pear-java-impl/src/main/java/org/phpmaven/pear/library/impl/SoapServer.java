/**
 * Copyright 2010-2012 by PHP-maven.org
 * 
 * This file is part of pear-java.
 *
 * pear-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pear-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pear-java.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.phpmaven.pear.library.impl;

import java.util.ArrayList;
import java.util.List;

import org.phpmaven.pear.library.ISoapFunction;
import org.phpmaven.pear.library.ISoapServer;

/**
 * Soap server.
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 0.1.0
 */
public class SoapServer implements ISoapServer {

    /** path. */
    private String path;
    
    /**
     * functions.
     */
    private List<ISoapFunction> functions = new ArrayList<ISoapFunction>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<ISoapFunction> getFunctions() {
        return this.functions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFunction(ISoapFunction function) {
        this.functions.add(function);
    }

}
