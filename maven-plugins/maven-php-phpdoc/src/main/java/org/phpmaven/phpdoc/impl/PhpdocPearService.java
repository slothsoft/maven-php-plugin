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

package org.phpmaven.phpdoc.impl;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.phpmaven.core.IComponentFactory;
import org.phpmaven.phpdoc.IPhpdocService;
import org.phpmaven.phpdoc.IPhpdocSupport;

/**
 * Implementation of the default phpunit service.
 * 
 * @author <a href="mailto:Martin.Eisengardt@googlemail.com">Martin Eisengardt</a>
 * @since 2.0.0
 */
@Component(role = IPhpdocService.class, instantiationStrategy = "per-lookup", hint = "PEAR")
public class PhpdocPearService implements IPhpdocService {
    
    /**
     * The component factory.
     */
    @Requirement
    private IComponentFactory factory;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServiceName() {
        return "PEAR";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPhpdocSupport createDefault(MavenSession session)
        throws PlexusConfigurationException, ComponentLookupException {
        for (final Dependency dep : session.getCurrentProject().getDependencies()) {
            if ("PhpDocumentor".equals(dep.getArtifactId()) && "net.pear".equals(dep.getGroupId())) {
                return createForPhpdocVersion(dep.getVersion(), session);
            }
        }
        return createForPhpdocVersion("1.4.2", session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPhpdocSupport createForPhpdocVersion(String version, MavenSession session)
        throws PlexusConfigurationException, ComponentLookupException {
        return this.factory.lookup(
                IPhpdocSupport.class,
                "PEAR",
                IComponentFactory.EMPTY_CONFIG,
                session);
    }

}
