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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import net.landora.video.utils.ComparisionUtils;
import net.landora.videoplayer.menu.Menu;
import net.landora.videoplayer.menu.MenuItem;
import net.landora.videoplayer.menu.MenuLink;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class PlayerMenu extends JComponent implements Scrollable, UIEventHandler {

    private MenuSkin skin;

    public PlayerMenu() {
        setOpaque(false);

        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void setLayout(LayoutManager mgr) {

    }

    public MenuSkin getSkin() {
        return skin;
    }

    public void setSkin(MenuSkin skin) {
        this.skin = skin;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return getScrollableBlockIncrement(visibleRect, orientation, direction);
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.VERTICAL) {
            return visibleRect.height;
        } else {
            return visibleRect.width;
        }
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
    }

    private Stack<MenuItem> selectedMenuItems = new Stack<MenuItem>();
    private Stack<Menu> parentMenus = new Stack<Menu>();
    private Menu currentMenu;

    public boolean openParentMenu() {
        if (parentMenus.isEmpty()) {
            return false;
        }

        Menu parent = parentMenus.pop();
        MenuItem selecteditem = selectedMenuItems.pop();

        setMenuImpl(parent, selecteditem);
        return true;
    }

    public void openMenu(Menu menu) {
        if (currentMenu == null) {
            throw new IllegalStateException("The initial menu should be applied via the setBaseMenu method.");
        }

        parentMenus.push(currentMenu);
        selectedMenuItems.push(selectedComp == null ? null : selectedComp.getItem());

        setMenuImpl(menu, null);
    }

    public void setBaseMenu(Menu menu) {
        parentMenus.clear();
        selectedMenuItems.clear();

        setMenuImpl(menu, null);
    }

    private List<MenuItemComponent<?>> components = new ArrayList<MenuItemComponent<?>>();

    private void setMenuImpl(Menu menu, MenuItem selectedMenuItem) {
        currentMenu = menu;

        currentMenu.refresh();

        removeAll();
        components.clear();

        MenuItemComponent<?> compToSelect = null;

        for (MenuItem item : currentMenu.getMenuItems()) {
            MenuItemComponent<?> comp;
            if (item instanceof MenuLink) {
                MenuLink link = (MenuLink) item;
                link.setMenuComponent(this);

                MenuLinkComponent linkComp = new MenuLinkComponent(link);
                comp = linkComp;

                skin.skinComponent(linkComp);
            } else {
                LoggerFactory.getLogger(getClass()).error("Unknown MenuItem class: " + item.getClass());
                continue;
            }

            add(comp);
            components.add(comp);

            if (ComparisionUtils.equals(item, selectedMenuItem)) {
                compToSelect = comp;
            }
        }

        invalidate();
        revalidate();

        if (compToSelect != null) {
            selectComponent(compToSelect);
        } else if (!components.isEmpty()) {
            selectComponent(components.get(0));
        } else {
            selectComponent(null);
        }

    }

    private MenuItemComponent<?> selectedComp;

    private void selectComponent(MenuItemComponent<?> comp) {
        if (selectedComp == comp) {
            return;
        }

        if (selectedComp != null) {
            selectedComp.setSelected(false);
        }

        selectedComp = comp;

        if (selectedComp != null) {
            selectedComp.setSelected(true);
            selectedComp.scrollRectToVisible(new Rectangle(selectedComp.getWidth(), selectedComp.getHeight()));
        }
    }

    public boolean focusUp(MenuItemComponent<?> caller) {

        int compLoc = -1;
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (component == caller) {
                compLoc = i;
                break;
            }
        }

        if (compLoc <= 0) {
            return false;
        }

        selectComponent(components.get(compLoc - 1));

        return true;
    }

    public boolean focusDown(MenuItemComponent<?> caller) {
        int compLoc = -1;
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (component == caller) {
                compLoc = i;
                break;
            }
        }

        if (compLoc == -1 || compLoc + 1 >= components.size()) {
            return false;
        }

        selectComponent(components.get(compLoc + 1));

        return true;
    }

    public boolean handleEvent(UIInputEvent event) {
        if ((selectedComp != null) && (selectedComp instanceof UIEventHandler)) {
            if (((UIEventHandler) selectedComp).handleEvent(event)) {
                return true;
            }
        }

        switch (event) {
            case Left:
            case Back:
            case Escape:
                return openParentMenu();

            default:
                return false;

        }
    }
}
