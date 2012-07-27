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

import java.util.ArrayList;
import java.util.List;
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectory;
import net.landora.videoplayer.menu.BackgroundRefreshMenu;
import net.landora.videoplayer.menu.MenuItem;
import net.landora.videoplayer.menu.MenuLink;

/**
 *
 * @author bdickie
 */
public class FilesMenu extends BackgroundRefreshMenu {

    public FilesMenu() {
    }
    
    private List<MenuLink> links;
    
    @Override
    public void refreshImpl() {
        links = new ArrayList<MenuLink>();
        
        LocalPathManager mng = LocalPathManager.getInstance();
        
        for(SharedDirectory dir: mng.getAvaliableLocalPaths()) {
            links.add(new MenuLink(MenuItem.Icon.Folder, dir.getName(), new GeneralDirectoryMenu(mng.getLocalPath(dir))));
        }
        
    }

    @Override
    protected String getRefreshDescription() {
        return "Finding File Roots";
    }
    
    

    @Override
    public List<? extends MenuItem> getMenuItems() {
        return links;
    }
    
}
