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

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.landora.video.info.ExtensionUtils;
import net.landora.video.utils.FileSorter;
import net.landora.videoplayer.menu.BackgroundRefreshMenu;
import net.landora.videoplayer.menu.MenuItem;
import net.landora.videoplayer.menu.MenuItem.Icon;
import net.landora.videoplayer.menu.MenuLink;
import org.apache.commons.io.filefilter.FileFilterUtils;

/**
 *
 * @author bdickie
 */
public class GeneralDirectoryMenu extends BackgroundRefreshMenu {

    private File dir;

    public GeneralDirectoryMenu(File dir) {
        this.dir = dir;
    }
    
    private List<MenuLink> links;
    
    @Override
    protected void refreshImpl() {
        links = new ArrayList<MenuLink>();
        
        
        File[] children = dir.listFiles((FileFilter)FileFilterUtils.directoryFileFilter());
        if (children != null && children.length > 0) {
            Arrays.sort(children, new FileSorter());
            for(File child: children)
                links.add(new MenuLink(Icon.Folder, child.getName(), new GeneralDirectoryMenu(child)));
        }
        
        children = dir.listFiles((FileFilter)FileFilterUtils.notFileFilter(FileFilterUtils.directoryFileFilter()));
        if (children != null & children.length != 0) {
            Arrays.sort(children, new FileSorter());

            for(File child: children) {
                if (child.isHidden() || !child.isFile() || !ExtensionUtils.isVideoExtension(child))
                    continue;
                
                links.add(new MenuLink(Icon.File, child.getName(), new PlayFileAction(child)));
            }
        }
    }

    @Override
    protected String getRefreshDescription() {
        return "Reading Directory";
    }

    @Override
    public List<? extends MenuItem> getMenuItems() {
        return links;
    }
    
}
