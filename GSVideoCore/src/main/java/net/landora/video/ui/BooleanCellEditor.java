/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
        super(new JCheckBox());
        
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
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (!(value instanceof Boolean))
            throw new IllegalArgumentException("Only Boolean objects are supported");
        
        JCheckBox box = (JCheckBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);
                
        box.setOpaque(true);
        box.setBackground(table.getBackground());
        
        return box;
    }
    
}
