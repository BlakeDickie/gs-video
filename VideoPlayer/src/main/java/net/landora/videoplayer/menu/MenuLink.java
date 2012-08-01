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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 *
 * @author bdickie
 */
public class MenuLink extends MenuItem {
    private String name;

    public MenuLink() {
    }

    public MenuLink(String name) {
        this.name = name;
    }

    public MenuLink(String name, ActionListener l) {
        this(name);
        
        if (l != null)
            addActionListener(l);
    }
    
    public MenuLink(String name, Menu linkMenu) {
        this(name, new OpenMenuAction(linkMenu));
    }

    public MenuLink(Icon icon, String name) {
        super(icon);
        this.name = name;
    }

    public MenuLink(Icon icon, String name, ActionListener l) {
        this(icon, name);
        
        if (l != null)
            addActionListener(l);
    }
    
    public MenuLink(Icon icon, String name, Menu linkMenu) {
        this(icon, name, new OpenMenuAction(linkMenu));
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Menu: " + getName();
    }
    
    public final void addActionListener(ActionListener l) {
        listeners.add(ActionListener.class, l);
    }
    
    public final void removeActionListener(ActionListener l) {
        listeners.add(ActionListener.class, l);
    }
    
    public void fireActionPreformed() {
        ActionEvent evt = null;
        
        for(ActionListener l: listeners.getListeners(ActionListener.class)) {
            if (evt == null) {
                evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "preformed");
            }
            
            l.actionPerformed(evt);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.name != null ? this.name.hashCode() : 0);
        
        ActionListener[] l = listeners.getListeners(ActionListener.class);
        hash = 13 * hash + (l != null ? Arrays.hashCode(l) : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuLink other = (MenuLink) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        ActionListener[] l1 = listeners.getListeners(ActionListener.class);
        ActionListener[] l2 = listeners.getListeners(ActionListener.class);
        if ((l1 == null) ? (l2 != null) : !Arrays.equals(l1, l2)) {
            return false;
        }
        return true;
    }
    
    
    
}
