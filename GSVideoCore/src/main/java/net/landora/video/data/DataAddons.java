/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.data;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.preferences.PreferencesAddon;

/**
 *
 * @author bdickie
 */
public class DataAddons extends AbstractAddon {
    public static final String ID = "net.landora.video.data";
    
    public DataAddons() {
        super(ID, "Databases", PreferencesAddon.ID);
    }
}
