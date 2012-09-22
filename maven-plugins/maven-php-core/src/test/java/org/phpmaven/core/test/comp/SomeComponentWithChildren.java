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

package org.phpmaven.core.test.comp;

import java.io.File;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Configuration;
import org.codehaus.plexus.component.annotations.Requirement;
import org.phpmaven.core.BuildPluginConfiguration;
import org.phpmaven.core.ConfigurationParameter;


/**
 * A sample component.
 * 
 * @author Martin Eisengardt <Martin.Eisengardt@googlemail.com>
 * @since 2.0.0
 */
@Component(role = ISomeComponentWithChildren.class, instantiationStrategy = "per-lookup")
@BuildPluginConfiguration(groupId = "org.phpmaven", artifactId = "maven-php-plugin-test")
public class SomeComponentWithChildren implements ISomeComponentWithChildren {
    
    /** foo. */
    @Configuration(name = "foo", value = "default-foo")
    private String foo;
    
    /** bar. */
    @Configuration(name = "bar", value = "default-bar")
    @ConfigurationParameter(name = "bar", expression = "${bar}")
    private String bar;

    /** foobar. */
    @ConfigurationParameter(name = "fooBar", expression = "${project.basedir}/fooBar")
    private File fooBar;

    /**
     * deep
     */
    @Requirement
	private ISomeComponent deep;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFoo() {
        return this.foo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setFoo(String foo) {
        this.foo = foo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getBar() {
        return this.bar;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setBar(String bar) {
        this.bar = bar;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public File getFooBar() {
        return this.fooBar;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setFooBar(File fooBar) {
        this.fooBar = fooBar;
    }

	@Override
	public ISomeComponent getDeep() {
		return this.deep;
	}

	@Override
	public void setDeep(ISomeComponent deep) {
		this.deep = deep;
	}
    
}