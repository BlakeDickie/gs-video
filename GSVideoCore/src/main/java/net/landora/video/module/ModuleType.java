/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.module;


/**
 *
 * @author bdickie
 */
public final class ModuleType {
    
    private String moduleTypeName;
    private Class<? extends ModuleInterface> serviceClass;

    ModuleType(String moduleTypeName, Class<? extends ModuleInterface> serviceClass) {
        this.moduleTypeName = moduleTypeName;
        this.serviceClass = serviceClass;
    }

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public Class<? extends ModuleInterface> getServiceClass() {
        return serviceClass;
    }
    
}
