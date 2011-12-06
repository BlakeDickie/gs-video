/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfomodule;

import net.landora.videoinfo.data.preferences.Preferences;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        
        System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
    }

    @Override
    public void close() {
        Preferences.getInstance().save();
    }
    
    
}
