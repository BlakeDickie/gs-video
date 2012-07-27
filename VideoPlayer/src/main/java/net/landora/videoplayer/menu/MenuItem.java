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

import javax.swing.event.EventListenerList;
import net.landora.videoplayer.menu.ui.PlayerMenu;

/**
 *
 * @author bdickie
 */
public abstract class MenuItem {
    
    protected EventListenerList listeners = new EventListenerList();

    public MenuItem() {
    }

    public MenuItem(Icon icon) {
        this.icon = icon;
    }

    
    
    private PlayerMenu menuComponent;

    public PlayerMenu getMenuComponent() {
        return menuComponent;
    }

    public void setMenuComponent(PlayerMenu menuComponent) {
        this.menuComponent = menuComponent;
    }
    
    private Icon icon;

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
    
    
    public static enum Icon {
        Folder,
        File,
        Network;
    }
}
