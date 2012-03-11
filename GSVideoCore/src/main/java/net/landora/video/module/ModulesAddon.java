/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.module;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.preferences.PreferencesAddon;

/**
 *
 * @author bdickie
 */
public class ModulesAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.module.ModulesAddon";

    public ModulesAddon() {
        super(ID, "Module Config", PreferencesAddon.ID);
    }
    
    
    
}
