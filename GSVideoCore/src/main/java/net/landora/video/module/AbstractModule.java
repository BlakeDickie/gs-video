/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.module;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.landora.video.utils.MutableObject;

/**
 *
 * @author bdickie
 */
public abstract class AbstractModule implements ModuleInterface {

    public synchronized boolean isModuleUsable() {
        if (!cacheLoaded)
            loadCache();
        
        return usableCache;
    }
    
    public synchronized boolean isModuleConfigured() {
        if (!cacheLoaded)
            loadCache();
        
        return configuredCache;
    }
    
    
    public synchronized String getModuleError() {
        if (!cacheLoaded)
            loadCache();
        
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
        MutableObject<Boolean> usable = new MutableObject<Boolean>(Boolean.FALSE);
        MutableObject<Boolean> configured = new MutableObject<Boolean>(Boolean.FALSE);
        MutableObject<String> errorMessage = new MutableObject<String>(null);
        
        loadModuleState(usable, configured, errorMessage);
        
        usableCache = usable.getValue();
        configuredCache = configured.getValue();
        errorCache = errorMessage.getValue();
        
        cacheLoaded = true;
        
    }
    
    protected abstract void loadModuleState(MutableObject<Boolean> usable, MutableObject<Boolean> configured, MutableObject<String> errorMessage);
    
    protected class PropertyChangeCacheClear implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            clearCache();
        }
        
    }
}
