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
