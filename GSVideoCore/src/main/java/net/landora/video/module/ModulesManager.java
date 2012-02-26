/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
