/**
 * Copyright (C) 2012 Blake Dickie
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
package net.landora.videoplayer.basicskin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.geom.Point2D;
import net.landora.video.utils.UIUtils;
import net.landora.videoplayer.menu.ui.MenuLinkComponent;
import net.landora.videoplayer.menu.ui.MenuSkin;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.Painters;
import org.jdesktop.swingx.painter.PinstripePainter;
import org.jdesktop.swingx.painter.RectanglePainter;

/**
 *
 * @author bdickie
 */
public class BasicSkin extends MenuSkin {

    public BasicSkin() {
        super("GS Basic");
        
    }

    @Override
    public void initSkin() {
        String family = UIUtils.getFirstAvaliableFamily("Corbel", "Arial");
        
        if (family == null)
            family = Font.SANS_SERIF;
        
        mainFont = new Font(family, Font.BOLD, 20);
        secondaryFont = new Font(family, Font.BOLD, 16);
    }
    
    private Font mainFont;
    private Font secondaryFont;

    private Painter createBackgroundPainter() {
        Color blue = new Color(0x417DDD);
        Color translucent = new Color(blue.getRed(), blue.getGreen(), blue.getBlue(), 0);
        GradientPaint blueToTranslucent = new GradientPaint(
                new Point2D.Double(.4, 0),
                blue,
                new Point2D.Double(1, 0),
                translucent);
        MattePainter veil = new MattePainter(blueToTranslucent);
        veil.setPaintStretched(true);
        Painter pinstripes = new PinstripePainter(45);
        Painter backgroundPainter = new RectanglePainter(blue, null);
        Painter p = new CompoundPainter(backgroundPainter, pinstripes, veil);
        return p;
    }

    @Override
    public void skinComponent(MenuLinkComponent comp) {
        comp.setBackgroundPainter(Painters.EMPTY_PAINTER);
        comp.setSelectedBackgroundPainter(createBackgroundPainter());
        comp.setFontColor(Color.WHITE);
        comp.setFont(mainFont);
    }

    @Override
    public Painter getScrollPainter() {
        return new MattePainter(
                new GradientPaint(0, 0, Color.black, 100, 100, Color.GRAY),
                true);
    }
    
    @Override
    public Painter getWindowPainter() {
        return new MattePainter(Color.BLACK);
    }
}
