/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.preferences;

import net.landora.video.addons.AbstractAddon;

/**
 *
 * @author bdickie
 */
public class PreferencesAddon extends AbstractAddon {
    public static final String ID = "net.landroa.PreferencesAddon";

    public PreferencesAddon() {
        super(ID, "Preferences");
    }

    @Override
    public void stop() {
        super.stop();
        Preferences.getInstance().save();
    }
    
    
}
