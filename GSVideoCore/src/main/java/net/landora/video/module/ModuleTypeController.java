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

    public ModuleType getModuleType() {
        return moduleType;
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
        ModulesManager manager = ModulesManager.getInstance();
        ListIterator<T> i = modules.listIterator();
        while(i.hasNext()) {
            T module = i.next();
            if (!module.isModuleConfigured() || manager.isModuleDisabled(module))
                i.remove();
        }
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
