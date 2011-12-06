/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.filestate.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author bdickie
 */
public class FileConflict implements java.io.Serializable {
    private int conflictId;
    private ConflictType type;
    private Calendar reportedDate;
    private List<FileConfictItem> items;

    public FileConflict() {
        items = new ArrayList<FileConfictItem>();
    }

    public int getConflictId() {
        return conflictId;
    }

    public void setConflictId(int conflictId) {
        this.conflictId = conflictId;
    }

    public Calendar getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Calendar reportedDate) {
        this.reportedDate = reportedDate;
    }

    public ConflictType getType() {
        return type;
    }

    public void setType(ConflictType type) {
        this.type = type;
    }

    public List<FileConfictItem> getItems() {
        return items;
    }

    public void setItems(List<FileConfictItem> items) {
        this.items = items;
    }
    
    
    
    public static enum ConflictType {
        SameFile;
    }
}
