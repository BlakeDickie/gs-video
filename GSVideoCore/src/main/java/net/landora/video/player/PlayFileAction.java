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
