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
