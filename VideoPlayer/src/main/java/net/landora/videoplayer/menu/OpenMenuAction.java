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
public class OpenMenuAction implements ActionListener {

    private Menu menu;

    public OpenMenuAction(Menu menu) {
        this.menu = menu;
    }
    
    public void actionPerformed(ActionEvent e) {
        MenuItem item = (MenuItem)e.getSource();
        item.getMenuComponent().openMenu(menu);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.menu != null ? this.menu.hashCode() : 0);
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
        final OpenMenuAction other = (OpenMenuAction) obj;
        if (this.menu != other.menu && (this.menu == null || !this.menu.equals(other.menu))) {
            return false;
        }
        return true;
    }
    
    
}
