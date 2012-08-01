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
package net.landora.videoplayer.menu;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.landora.videoplayer.BackgroundTask;

/**
 *
 * @author bdickie
 */
public abstract class BackgroundRefreshMenu extends Menu {

    @Override
    public final void refresh() {
        BackgroundTask task = new BackgroundTask(Executors.callable(new RefreshRunnable()), getRefreshDescription());
        try {
            task.runTask();
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BackgroundRefreshMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected abstract void refreshImpl();
    protected abstract String getRefreshDescription();
    
    private class RefreshRunnable implements Runnable {

        @Override
        public void run() {
            refreshImpl();
        }
        
    }
    
}
