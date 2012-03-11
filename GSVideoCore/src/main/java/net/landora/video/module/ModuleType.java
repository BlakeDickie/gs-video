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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModuleType other = (ModuleType) obj;
        if (this.serviceClass != other.serviceClass && (this.serviceClass == null || !this.serviceClass.equals(other.serviceClass))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.serviceClass != null ? this.serviceClass.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getModuleTypeName();
    }
 
}
