/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.videoplayer.files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import net.landora.video.module.ModulesManager;
import net.landora.video.player.VideoFilePlayer;

/**
 *
 * @author bdickie
 */
public class PlayFileAction implements ActionListener {

    private File file;

    public PlayFileAction(File file) {
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for (VideoFilePlayer player : ModulesManager.getInstance().getConfiguredModules(VideoFilePlayer.class)) {
            if (player.canPlayFile(file)) {
                player.playFile(file);
                return;
            }
        }
    }
    
    
    
}
