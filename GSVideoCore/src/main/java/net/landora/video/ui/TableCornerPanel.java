/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.ui;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JTable;

/**
 *
 * @author bdickie
 */
public class TableCornerPanel extends JComponent {

    private JTable table;

    public TableCornerPanel( JTable table ) {
        this.table = table;
    }

    @Override
    protected void paintComponent( Graphics g ) {
        g.setColor( table.getBackground() );
        g.fillRect( 0, 0, getWidth(), getHeight() );

        if ( table.getShowVerticalLines() ) {
            g.setColor( table.getGridColor() );
            g.drawLine( getWidth() - 1, 0, getWidth() - 1, getHeight() - 1 );
        }

        if ( table.getShowHorizontalLines() ) {
            g.setColor( table.getGridColor() );
            g.drawLine( 0, getHeight() - 1, getWidth() - 1, getHeight() - 1 );
        }

    }
}
