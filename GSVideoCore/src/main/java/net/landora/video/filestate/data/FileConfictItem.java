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
