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
package net.landora.video.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bdickie
 */
public class TableRowHeader extends JComponent {
    
    private JTable table;
    private TableCellRenderer renderer;
    private List<?> values;
    private CellRendererPane rendererPane;

    public TableRowHeader(JTable table, List<?> values, TableCellRenderer renderer) {
        this.table = table;
        this.values = values;
        
        this.renderer = (renderer == null ? new DefaultTableCellRenderer() : renderer);
        rendererPane = new CellRendererPane();
        add(rendererPane);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(table.getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        int y = 0;
        Dimension interspacing = table.getIntercellSpacing();
        
        for(int row = 0; row < table.getRowCount(); row++) {
            int height = table.getRowHeight(row);
            Component comp = renderer.getTableCellRendererComponent(table, values.get(row), false, false, row, 0);
            
            rendererPane.paintComponent(g, comp, this, 0, y, getWidth() - interspacing.width, height - interspacing.height);
            
            if (table.getShowHorizontalLines()) {
                g.setColor(table.getGridColor());
                
                g.drawLine(0, y + height - 1, getWidth(), y + height - 1);
            }
            y += height;
        }
        
        if (table.getShowVerticalLines()) {
            g.setColor(table.getGridColor());
            g.drawLine(getWidth() - 1, 0, getWidth() - 1, y - 1);
        }
        
        rendererPane.removeAll();
    }

    @Override
    public Dimension getPreferredSize() {
        int height = 0;
        int width = 10;
        Dimension interspacing = table.getIntercellSpacing();
        for(int row = 0; row < table.getRowCount(); row++) {
            height += table.getRowHeight(row);
            
            Component comp = renderer.getTableCellRendererComponent(table, values.get(row), false, false, row, 0);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        
        return new Dimension(width + interspacing.width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
    
    
}
