/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.module;

/**
 *
 * @author bdickie
 */
public interface ModuleInterface {
    public static final int MODULE_PRIORITY_PREFERRED = 10;
    public static final int MODULE_PRIORITY_FALLBACK = 0;
    public static final int MODULE_PRIORITY_GENERAL = 5;
    
    public String getModuleName();
    public String getModuleDescription();
    public boolean isModuleUsable();
    public boolean isModuleConfigured();
    public String getModuleError();
    public int getModulePriority();
}
