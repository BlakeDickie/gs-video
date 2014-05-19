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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import net.landora.videoplayer.menu.MenuLink;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.painter.Painter;

/**
 *
 * @author bdickie
 */
public class MenuLinkComponent extends MenuItemComponent<MenuLink> implements UIEventHandler {

    private MenuLink link;
    private JXLabel label;

    private Painter backgroundPainter;
    private Painter selectedBackgroundPainter;

    public MenuLinkComponent(MenuLink link) {
        super(link);

        this.link = link;

        label = new JXLabel(link.getName());
        label.setOpaque(false);

        setLayout(new BorderLayout());
        add(label);

        setOpaque(false);

    }

    private PlayerMenu getPlayerMenu() {
        return link.getMenuComponent();
    }

    public MenuLink getLink() {
        return link;
    }

    @Override
    public void setFont(Font font) {
        label.setFont(font);
    }

    @Override
    public Font getFont() {
        return label.getFont();
    }

    public void setFontColor(Color color) {
        label.setForeground(color);
    }

    public Color getFontColor() {
        return label.getForeground();
    }

    public Painter getBackgroundPainter() {
        return backgroundPainter;
    }

    public void setBackgroundPainter(Painter backgroundPainter) {
        this.backgroundPainter = backgroundPainter;

        repaint();
    }

    public Painter getSelectedBackgroundPainter() {
        return selectedBackgroundPainter;
    }

    public void setSelectedBackgroundPainter(Painter selectedBackgroundPainter) {
        this.selectedBackgroundPainter = selectedBackgroundPainter;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Painter p = null;
        if (isSelected()) {
            p = getSelectedBackgroundPainter();
        }
        if (p == null) {
            p = getBackgroundPainter();
        }

        if (p != null) {
            p.paint((Graphics2D) g, this, getWidth(), getHeight());
        }
    }

    public boolean handleEvent(UIInputEvent event) {
        switch (event) {
            case Up:
                return getPlayerMenu().focusUp(this);

            case Down:
                return getPlayerMenu().focusDown(this);

            case Right:
            case Enter:
                link.fireActionPreformed();
                return true;

            default:
                return false;
        }
    }

}
