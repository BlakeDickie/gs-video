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
package net.landora.video.filebrowser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bdickie
 */
public class TableRowHeaderRenderer extends JLabel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Font font = table.getFont();
        
        Color background = table.getBackground();
        Color foreground = table.getForeground();
        
        if (value instanceof StyledValue) {
            StyledValue sv = (StyledValue)value;
            if (sv.getBackground() != null)
                background = sv.getBackground();
            if (sv.getForeground() != null)
                foreground = sv.getForeground();
            if (sv.getStyle() != null)
                font = font.deriveFont(sv.getStyle());
        }
        
        setFont(font);
        setBackground(background);
        setForeground(foreground);
        setText(String.valueOf(value));
        
//        setHorizontalAlignment(RIGHT);
        setOpaque(true);
        
        return this;
    }
    
    
    public static class StyledValue {
        private Color background;
        private Color foreground;
        private Integer style;
        
        private Object value;

        public StyledValue(Object value) {
            this.value = value;
        }

        public Color getBackground() {
            return background;
        }

        public void setBackground(Color background) {
            this.background = background;
        }

        public Color getForeground() {
            return foreground;
        }

        public void setForeground(Color foreground) {
            this.foreground = foreground;
        }

        public Integer getStyle() {
            return style;
        }

        public void setStyle(Integer style) {
            this.style = style;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
        
    }
}
