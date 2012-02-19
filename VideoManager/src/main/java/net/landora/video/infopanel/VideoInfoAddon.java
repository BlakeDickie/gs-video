/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.infopanel;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.manager.ManagerAddon;

/**
 *
 * @author bdickie
 */
public class VideoInfoAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.infopanel.VideoInfoAddon";
    
    public VideoInfoAddon() {
        super(ID, "Video Info", ManagerAddon.ID);
    }

    @Override
    public void start() {
        ManagerAddon.getInstance().addInfoPanel(new VideoInfoPanel());
    }
    
    
}
