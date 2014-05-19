/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.module;

import net.landora.video.utils.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author bdickie
 */
public abstract class AbstractModule implements ModuleInterface {

    protected Logger log = LoggerFactory.getLogger( getClass() );

    public synchronized boolean isModuleUsable() {
        if ( !cacheLoaded ) {
            loadCache();
        }

        return usableCache;
    }

    public synchronized boolean isModuleConfigured() {
        if ( !cacheLoaded ) {
            loadCache();
        }

        return configuredCache;
    }

    public synchronized String getModuleError() {
        if ( !cacheLoaded ) {
            loadCache();
        }

        return errorCache;
    }

    private boolean cacheLoaded;
    private boolean usableCache;
    private boolean configuredCache;
    private String errorCache;

    protected synchronized void clearCache() {
        cacheLoaded = false;
        errorCache = null;
    }

    private synchronized void loadCache() {
        MutableObject<Boolean> usable = new MutableObject<Boolean>( Boolean.FALSE );
        MutableObject<Boolean> configured = new MutableObject<Boolean>( Boolean.FALSE );
        MutableObject<String> errorMessage = new MutableObject<String>( null );

        loadModuleState( usable, configured, errorMessage );

        usableCache = usable.getValue();
        configuredCache = configured.getValue();
        errorCache = errorMessage.getValue();

        cacheLoaded = true;

    }

    protected abstract void loadModuleState( MutableObject<Boolean> usable, MutableObject<Boolean> configured, MutableObject<String> errorMessage );

    protected class PropertyChangeCacheClear implements PropertyChangeListener {

        public void propertyChange( PropertyChangeEvent evt ) {
            clearCache();
        }

    }
}
