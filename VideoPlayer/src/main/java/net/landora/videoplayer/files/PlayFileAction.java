/**
 * Copyright (C) 2012 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.videoplayer.files;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.landora.video.module.ModulesManager;
import net.landora.video.player.VideoFilePlayer;
import net.landora.videoplayer.BackgroundTask;

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
        BackgroundTask<Boolean> task = new BackgroundTask<Boolean>(new PlayFileCallable(), "Playing Video");
        try {
            task.runTask();
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PlayFileAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private class PlayFileCallable implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            for (VideoFilePlayer player : ModulesManager.getInstance().getConfiguredModules(VideoFilePlayer.class)) {
                if (player.canPlayFile(file)) {
                    player.playFile(file);
                    return true;
                }
            }
            return false;
        }
    }
}
