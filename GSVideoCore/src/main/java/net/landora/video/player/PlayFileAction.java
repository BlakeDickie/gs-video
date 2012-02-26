/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.player;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import net.landora.video.module.ModulesManager;
import net.landora.video.ui.UIAction;

/**
 *
 * @author bdickie
 */
public class PlayFileAction extends UIAction<File> {

    public PlayFileAction() {
        super(File.class, "Play File");
    }

    

    @Override
    public void actionPerformed(ActionEvent evt, List<File> context) {
        File file = context.get(0);
        
        for (VideoFilePlayer player : ModulesManager.getInstance().getConfiguredModules(VideoFilePlayer.class)) {
            if (player.canPlayFile(file)) {
                player.playFile(file);
                return;
            }
        }
    }
    
}
