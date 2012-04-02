/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.landora.video.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.landora.video.addons.AddonManager;

/**
 *
 * @author bdickie
 */
public class ModulesManager {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static ModulesManager instance = new ModulesManager();
    }

    public static ModulesManager getInstance() {
        if (AddonManager.getInstance().getAddonInstance(ModulesAddon.class) == null)
            throw new IllegalStateException("ModulesAddon not started.");
        return SingletonHolder.instance;
    }
    // </editor-fold>  
    
    private ModulesManager() {

        controllerMap = new HashMap<Class<? extends ModuleInterface>, ModuleTypeController>();
    }

    private Map<Class<? extends ModuleInterface>, ModuleTypeController> controllerMap;
    
    public synchronized <T extends ModuleInterface> void registerModuleInterface(Class<T> clazz, String desc) {
        controllerMap.put(clazz, new ModuleTypeController(new ModuleType(desc, clazz)));
    }
    
    public <T extends ModuleInterface> void addModule(Class<T> clazz, T module) {
        getController(clazz).addModule(module);
    }
    
    private synchronized <T extends ModuleInterface> ModuleTypeController<T> getController(Class<T> clazz) {
        ModuleTypeController controller = controllerMap.get(clazz);
        if (controller == null) {
            throw new IllegalStateException("Module class not registered: " + clazz);
        }
        return controller;
    }
    
    
    public <T extends ModuleInterface> List<T> getConfiguredModules(Class<T> clazz) {
        return getController(clazz).getConfiguredModules();
    }
    
    
    public <T extends ModuleInterface> List<T> getModules(Class<T> clazz) {
        return getController(clazz).getModules();
    }
    
    
    public <T extends ModuleInterface> List<T> getUsableModules(Class<T> clazz) {
        return getController(clazz).getUsableModules();
    }


    List<ModuleType> getConfiguredTypes() {
        List<ModuleType> result = new ArrayList<ModuleType>(controllerMap.size());
        for(ModuleTypeController controller: controllerMap.values())
            result.add(controller.getModuleType());
        return result;
    }
    
    private Set<String> disabledModules;
    
    synchronized boolean isModuleDisabled(ModuleInterface module) {
        if (disabledModules == null)
            disabledModules = new HashSet<String>(ModulesPreference.DisabledModules.getStringList());
        return disabledModules.contains(module.getModuleName());
    }
    
    synchronized void setDisabledMofules(Collection<ModuleInterface> modules) {
        Set<String> ids = new HashSet<String>();
        for(ModuleInterface module: modules)
            ids.add(module.getModuleName());
        disabledModules = ids;
        ModulesPreference.DisabledModules.setStringList(new ArrayList<String>(ids));
    }
}
