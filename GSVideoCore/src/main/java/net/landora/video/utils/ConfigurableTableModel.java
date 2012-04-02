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
/*
 * ParitalEditableTableModel.java
 *
 * Created on July 12, 2004, 8:44 AM
 */

package net.landora.video.utils;

import java.util.*;
import javax.swing.table.DefaultTableModel;

/** A TableModel based on the DefaultTableModel with the addition that a
 *  cell can be set to editable or not.  Defaults to not being editable.
 *
 * @author  Blake Dickie
 */
public class ConfigurableTableModel<T> extends DefaultTableModel {
    private HashSet<Pair> editable;
    private HashSet<Pair> masked;
    private HashSet<Pair> maskAuthorized;
    private HashMap<Integer,Class> columnClass;
    private HashSet<Integer> editableColumns;
    private HashMap<Integer,T> rowObject;
    private boolean readOnly;
    private Class<?> defaultColumnClass = Object.class;
    
    public ConfigurableTableModel(List columnNames) {
        this(columnNames.toArray());
    }

    public ConfigurableTableModel(String... columnNames) {
        this((Object[])columnNames);
    }
    
    /** Creates a new instance of NonEditableTableModel */
    public ConfigurableTableModel(Object[] columnNames) {
        super(columnNames, 0);
        editable = new HashSet<Pair>();
        masked = new HashSet<Pair>();
        maskAuthorized = new HashSet<Pair>();
        columnClass = new HashMap<Integer,Class>();
        rowObject = new HashMap<Integer,T>();
        editableColumns = new HashSet<Integer>();
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isReadOnly() {
        return readOnly;
    }
    
    /** Returns if the cell is editable.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (readOnly)
            return false;

        Pair p = new Pair(rowIndex, columnIndex);
        return (editableColumns.contains(columnIndex) || editable.contains(p)) 
                && !masked.contains(p);
    }
    
    public void setColumnEditable(int column, boolean editable) {
        if (editable)
            editableColumns.add(column);
        else
            editableColumns.remove(column);
    }
    
    /** Sets the given cell to editable iff edit it true.
     */
    public void setCellEditable(int rowIndex, int columnIndex, boolean edit) {
        Pair p = new Pair(rowIndex, columnIndex);
        if (!editable.contains(p) && edit)
            editable.add(p);
        if (editable.contains(p) && !edit)
            editable.remove(p);
    }
    
    /** Returns if the cell is editable.
     */
    public boolean isCellMasked(int rowIndex, int columnIndex) {
        return masked.contains(new Pair(rowIndex, columnIndex));
    }
    
    /** Sets the given cell to editable iff edit it true.
     */
    public void setCellMasked(int rowIndex, int columnIndex, boolean isMasked) {
        Pair p = new Pair(rowIndex, columnIndex);
        if (!masked.contains(p) && isMasked)
            masked.add(p);
        if (masked.contains(p) && !isMasked)
            masked.remove(p);
    }
    
    /** Returns if the cell is editable.
     */
    public boolean isCellMaskAuthorized(int rowIndex, int columnIndex) {
        return maskAuthorized.contains(new Pair(rowIndex, columnIndex));
    }
    
    /** Sets the given cell to editable iff edit it true.
     */
    public void setCellMaskAuthorized(int rowIndex, int columnIndex, boolean isMaskAuthorized) {
        Pair p = new Pair(rowIndex, columnIndex);
        if (!maskAuthorized.contains(p) && isMaskAuthorized)
            maskAuthorized.add(p);
        if (maskAuthorized.contains(p) && !isMaskAuthorized)
            maskAuthorized.remove(p);
    }
    
    public void setRowMasked(int rowIndex, boolean isMasked) {
        for(int i = 0; i < getColumnCount(); i++)
            setCellMasked(rowIndex, i, isMasked);
    }
    
    public void setCellsMasked(int rowIndex, int startCol, int endCol, boolean isMasked) {
        for(int i = startCol; i <= endCol; i++)
            setCellMasked(rowIndex, i, isMasked);
    }
    
    public void setCellsMasked(int rowIndex, boolean isMasked, int...columns) {
        for(int i: columns)
            setCellMasked(rowIndex, i, isMasked);
    }

    public Class<?> getDefaultColumnClass() {
        return defaultColumnClass;
    }

    public void setDefaultColumnClass(Class<?> defaultColumnClass) {
        this.defaultColumnClass = defaultColumnClass;
    }
    
    
    
    public Class<?> getColumnClass(int columnIndex) {
        if (columnClass.containsKey(columnIndex))
            return columnClass.get(columnIndex);
        else
            return defaultColumnClass;
    }
    
    public void setColumnClass(int columnIndex, Class clazz) {
        columnClass.put(columnIndex, clazz);
    }
    
    public void addRow(Vector rowData, T object) {
        rowObject.put(getRowCount(), object);
        super.addRow(rowData);
    }
    
    public void addRow(Object[] rowData, T object) {
        rowObject.put(getRowCount(), object);
        super.addRow(rowData);
    }

    public void addRows(List<Vector> rows) {
        int currentCount = getRowCount();
        for(Vector row: rows) {
            dataVector.add(row);
        }
        fireTableRowsInserted(currentCount, currentCount + rows.size() - 1);
    }

    public void addRowsWithObjects(List<Touple<Vector,T>> rows) {
        int currentCount = getRowCount();

        for(Touple<Vector,T> rowEntry : rows) {
            rowObject.put(getRowCount(), rowEntry.getSecond());
            dataVector.add(rowEntry.getFirst());
        }
        
        fireTableRowsInserted(currentCount, currentCount + rows.size() - 1);
    }


    @Override
    public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
        if (maskAuthorized != null)
            maskAuthorized.clear();
        if (masked != null)
            masked.clear();
        if (maskAuthorized != null)
            maskAuthorized.clear();
        
        super.setDataVector(dataVector, columnIdentifiers);
    }
    
    public void setDataVectorWithRowObjects(Vector rowData, List<T> rowObjects) {
        setDataVector(rowData, columnIdentifiers);
        
        rowObject.clear();
        
        int row = 0;
        for (T object : rowObjects) {
            rowObject.put(row++, object);
        }
    }

    @Override
    public void setNumRows(int rowCount) {
        super.setNumRows(rowCount);
        
        
        ArrayList<Integer> toDeleteObject = new ArrayList<Integer>();
        
        applyNumRows(rowCount, masked);
        applyNumRows(rowCount, editable);
        applyNumRows(rowCount, maskAuthorized);
        
        for(Map.Entry<Integer,T> obj: rowObject.entrySet()) {
            int cRow = obj.getKey();
            if (cRow >= rowCount)
                toDeleteObject.add(obj.getKey());
        }
        for(Integer id: toDeleteObject)
            rowObject.remove(id);
    }
    
    private void applyNumRows(int rowCount, Set<Pair> set) {
        ArrayList<Pair> toDelete = new ArrayList<Pair>();
        for(Pair def: set) {
            if (def.row >= rowCount)
                toDelete.add(def);
        }
        set.removeAll(toDelete);
    }
    
    
    
    /** A simple class that stores an x,y pair.
     */
    private static class Pair {
        private int row;
        private int column;
        
        public Pair(int row, int column) {
            this.row = row;
            this.column = column;
        }
        
        public boolean equals(Object other) {
            if (other instanceof Pair) {
                Pair p = (Pair)other;
                return (p.row == row) && (p.column == column);
            } else
                return false;
        }
        
        public int hashCode() {
            return row + column*column;
        }
    }

    @Override
    public void insertRow(int row, Vector rowData) {

        if (row < getRowCount()) {
            ArrayList<Integer> toDeleteObject = new ArrayList<Integer>();
            Map<Integer,T> adjustedRowObject = new HashMap<Integer,T>();

            applyInsertRow(row, editable);
            applyInsertRow(row, masked);
            applyInsertRow(row, maskAuthorized);

            for(Map.Entry<Integer,T> obj: rowObject.entrySet()) {
                int cRow = obj.getKey();
                if (cRow >= row) {
                    toDeleteObject.add(obj.getKey());
                    adjustedRowObject.put(obj.getKey() + 1, obj.getValue());
                }
            }
            for(Integer id: toDeleteObject)
                rowObject.remove(id);
            rowObject.putAll(adjustedRowObject);
        }
        
        super.insertRow(row, rowData);
    }



    public void removeRow(int row) {
        
        ArrayList<Integer> toDeleteObject = new ArrayList<Integer>();
        Map<Integer,T> adjustedRowObject = new HashMap<Integer,T>();
        
        applyRemoveRow(row, editable);
        applyRemoveRow(row, masked);
        applyRemoveRow(row, maskAuthorized);
        
        for(Map.Entry<Integer,T> obj: rowObject.entrySet()) {
            int cRow = obj.getKey();
            if (cRow > row) {
                toDeleteObject.add(obj.getKey());
                adjustedRowObject.put(obj.getKey() - 1, obj.getValue());
            } else if (cRow == row)
                toDeleteObject.add(obj.getKey());
        }
        for(Integer id: toDeleteObject)
            rowObject.remove(id);
        rowObject.putAll(adjustedRowObject);
        
        super.removeRow(row);
    }
    
    private void applyRemoveRow(int row, Set<Pair> set) {
        ArrayList<Pair> toDelete = new ArrayList<Pair>();
        ArrayList<Pair> toAdd = new ArrayList<Pair>();
        for(Pair def: set) {
            if (def.row > row) {
                toDelete.add(def);
                toAdd.add(new Pair(def.row - 1, def.column));
            } else if (def.row == row)
                toDelete.add(def);
        }
        set.removeAll(toDelete);
        set.addAll(toAdd);
    }

    private void applyInsertRow(int row, Set<Pair> set) {
        ArrayList<Pair> toDelete = new ArrayList<Pair>();
        ArrayList<Pair> toAdd = new ArrayList<Pair>();
        for(Pair def: set) {
            if (def.row >= row) {
                toDelete.add(def);
                toAdd.add(new Pair(def.row + 1, def.column));
            }
        }
        set.removeAll(toDelete);
        set.addAll(toAdd);
    }
    
    public T getRowObject(int row) {
        return rowObject.get(row);
    }
    
    public void setRowObject(int row, T object) {
        rowObject.put(row, object);
    }

    public Object getRowHeader(int row) {
        return getRowObject(row);
    }
    
    public int getRowHeaderSpan(int row) {
        return 1;
    }

    public void setRow(int row, Object[] rowData, T object) {
        for(int col = 0; col < rowData.length; col++)
            setValueAt(rowData[col], row, col);
        setRowObject(row, object);
    }
}
