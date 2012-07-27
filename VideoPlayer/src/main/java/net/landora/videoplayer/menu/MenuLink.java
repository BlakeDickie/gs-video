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
    
}
