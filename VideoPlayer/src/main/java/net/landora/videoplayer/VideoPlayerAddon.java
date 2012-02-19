/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoplayer;

import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class VideoPlayerAddon extends AbstractAddon {
    public static final String ID = "net.landora.videoplayer.VideoPlayerAddon";

    public VideoPlayerAddon() {
        super(ID, "Video Player", UIAddon.ID, PreferencesAddon.ID);
    }

    @Override
    public void load() {
        VideoManagerApp.getInstance().addProfile(new VideoPlayerProfile());
    }
    
    
}
