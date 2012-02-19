/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filestate.data;

import java.io.Serializable;

/**
 *
 * @author bdickie
 */
public class FileConfictItem implements Serializable {
    private FileRecord file;
    private FileConflict conflict;
    private String conflictExtras;

    public FileConfictItem() {
    }

    public FileConflict getConflict() {
        return conflict;
    }

    public void setConflict(FileConflict conflict) {
        this.conflict = conflict;
    }

    public String getConflictExtras() {
        return conflictExtras;
    }

    public void setConflictExtras(String conflictExtras) {
        this.conflictExtras = conflictExtras;
    }

    public FileRecord getFile() {
        return file;
    }

    public void setFile(FileRecord file) {
        this.file = file;
    }
    
    
}
