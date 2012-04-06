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

package net.landora.video.filebrowser;

import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.manager.ManagerAddon;

/**
 *
 * @author bdickie
 */
public class FileBrowserAddon extends AbstractAddon {
    private static final String ID = "net.landora.video.filebrowser.FileBrowserAddon";
    
    public FileBrowserAddon() {
        super(ID, "File Browser", ManagerAddon.ID);
    }

    @Override
    public void start() {
        if (VideoManagerApp.getInstance().getProfile().isManager()) {
            ManagerAddon.getInstance().addContentPanel(new FileBrowserPanel());
            ManagerAddon.getInstance().addContentPanel(new DuplicatePanel());
        }
    }
    
    
}
