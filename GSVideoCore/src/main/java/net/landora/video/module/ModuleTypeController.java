/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.module;

import java.util.*;

/**
 *
 * @author bdickie
 */
final class ModuleTypeController<T extends ModuleInterface> {

    private ModuleType moduleType;
    private ServiceLoader<T> loader;
    private List<T> registeredModules;
    
    ModuleTypeController(ModuleType moduleType) {
        this.moduleType = moduleType;
        registeredModules = new ArrayList<T>();
    }
    
    public synchronized void addModule(T module) {
        registeredModules.add(module);
    }
    
    public synchronized List<T> getModules() {
        List<T> modules = new ArrayList<T>(registeredModules);
        Collections.sort(modules, new ModulePrioritySorter());
        return modules;
    }
    
    public List<T> getUsableModules() {
        List<T> modules = getModules();
        ListIterator<T> i = modules.listIterator();
        while(i.hasNext())
            if (!i.next().isModuleUsable())
                i.remove();
        return modules;
    }
    
    public List<T> getConfiguredModules() {
        List<T> modules = getUsableModules();
        ListIterator<T> i = modules.listIterator();
        while(i.hasNext())
            if (!i.next().isModuleConfigured())
                i.remove();
        return modules;
    }
    
    
    private static class ModulePrioritySorter implements Comparator<ModuleInterface> {

        public int compare(ModuleInterface o1, ModuleInterface o2) {
            int p1 = o1.getModulePriority();
            int p2 = o2.getModulePriority();
            if (p1 < p2)
                return 1;
            else if (p1 > p2)
                return -1;
            else
                return 0;
        }
        
    }
}
