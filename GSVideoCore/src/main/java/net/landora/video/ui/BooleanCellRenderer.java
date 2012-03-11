/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bdickie
 */
public class BooleanCellRenderer extends JCheckBox implements TableCellRenderer {

    
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!(value instanceof Boolean))
            throw new IllegalArgumentException("Only Boolean objects are supported");
        
        setSelected((Boolean)value);
        setOpaque(true);
        setBackground(table.getBackground());
        
        return this;
    }
    
}
