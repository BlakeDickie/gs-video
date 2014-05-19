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
package net.landora.videoplayer.menu.ui;

import javax.swing.JComponent;
import net.landora.videoplayer.menu.MenuItem;

/**
 *
 * @author bdickie
 */
public abstract class MenuItemComponent<T extends MenuItem> extends JComponent {

    protected T menuItem;

    public MenuItemComponent(T menuItem) {
        this.menuItem = menuItem;

    }

    public T getItem() {
        return menuItem;
    }

    private boolean selected;
    public static final String PROP_SELECTED = "selected";

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldSelected = this.selected;
        this.selected = selected;
        if (oldSelected != selected) {
            firePropertyChange(PROP_SELECTED, oldSelected, selected);
            repaint();
        }
    }

}
