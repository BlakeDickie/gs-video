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

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author bdickie
 */
public class BooleanCellEditor extends DefaultCellEditor {

    public BooleanCellEditor() {
        super( new JCheckBox() );

    }

//    
//    
//    
//    
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        if (!(value instanceof Boolean))
//            throw new IllegalArgumentException("Only Boolean objects are supported");
//        
//        setSelected((Boolean)value);
//        setOpaque(true);
//        setBackground(table.getBackground());
//        
//        return this;
//    }
    @Override
    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column ) {
        if ( !( value instanceof Boolean ) ) {
            throw new IllegalArgumentException( "Only Boolean objects are supported" );
        }

        JCheckBox box = (JCheckBox) super.getTableCellEditorComponent( table, value, isSelected, row, column );

        box.setOpaque( true );
        box.setBackground( table.getBackground() );

        return box;
    }

}
