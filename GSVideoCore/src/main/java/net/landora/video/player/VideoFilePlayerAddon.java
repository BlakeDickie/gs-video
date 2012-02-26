/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.player;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.module.ModulesManager;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class VideoFilePlayerAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.player.VideoFilePlayerAddon";

    public VideoFilePlayerAddon() {
        super(ID, "Video Player Backend Addon", PreferencesAddon.ID, UIAddon.ID);
    }

    @Override
    public void start() {
        ModulesManager.getInstance().registerModuleInterface(VideoFilePlayer.class, "Video Player Backend");
        
        UIAddon.getInstance().addAction(new PlayFileAction());
    }
    
    
}
