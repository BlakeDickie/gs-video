/**
 * Copyright (C) 2012-2014 Blake Dickie
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
package net.landora.videoplayer;

import java.awt.Window;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;

/**
 *
 * @author bdickie
 */
public class BackgroundTask<V> {

    private Callable<V> background;
    private String description;

    private static Executor executor = Executors.newCachedThreadPool();

    public BackgroundTask(Callable<V> background, String description) {
        this.background = background;
        this.description = description;
    }

    public V runTask() throws InvocationTargetException {
        Window win = VideoPlayerAddon.getInstance().getFrame();

        dialog = new BackgroundTaskDialog(win, description);

        executor.execute(new BackgroundRunnable());

        dialog.setLocationRelativeTo(win);
        dialog.setVisible(true);

        if (error != null) {
            throw new InvocationTargetException(error);
        } else {
            return result;
        }
    }

    private BackgroundTaskDialog dialog;

    private V result;
    private Throwable error;

    private class BackgroundRunnable implements Runnable {

        @Override
        public void run() {
            try {
                result = background.call();
            } catch (Throwable t) {
                error = t;
            }

            SwingUtilities.invokeLater(new HideDialogRunnable());
        }
    }

    private class HideDialogRunnable implements Runnable {

        @Override
        public void run() {
            dialog.setVisible(false);
        }

    }
}
