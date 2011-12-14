/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filestate.data;

import java.io.Serializable;
import java.util.Calendar;
import net.landora.video.info.file.FileInfo;

/**
 *
 * @author bdickie
 */
public class FileRecord extends FileInfo implements Serializable {
    private int fileId;
    private SharedDirectory directory;
    private String subPath;
    private boolean rename;

    public FileRecord() {
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
    public SharedDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(SharedDirectory directory) {
        this.directory = directory;
    }

    public boolean isRename() {
        return rename;
    }

    public void setRename(boolean rename) {
        this.rename = rename;
    }

    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileRecord other = (FileRecord) obj;
        if (this.fileId != other.fileId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.fileId;
        return hash;
    }
    
    public Calendar getLastModifiedDate() {
        long lastMod = getLastModified();
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(lastMod);
        return cal;
    }
    
    public void setLastModifiedDate(Calendar cal) {
        setLastModified(cal.getTimeInMillis());
    }
    
}
