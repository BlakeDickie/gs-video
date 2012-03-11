/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.properties;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.module.ModulesAddon;
import net.landora.video.module.ModulesManager;

/**
 *
 * @author bdickie
 */
public class PropertiesAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.properties.PropertiesAddon";

    public PropertiesAddon() {
        super(ID, "Media Properties", ModulesAddon.ID);
    }
    
    
    
    public static PropertiesAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(PropertiesAddon.class);
    }

    @Override
    public void start() {
        ModulesManager.getInstance().registerModuleInterface(DVDReader.class, "DVD Information");
        ModulesManager.getInstance().registerModuleInterface(VideoFileReader.class, "Video File Information");
    }
    
    
}
