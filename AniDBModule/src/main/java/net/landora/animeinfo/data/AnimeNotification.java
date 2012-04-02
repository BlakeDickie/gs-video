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

package net.landora.animeinfo.data;

import java.util.Calendar;
import java.util.Collection;
import net.landora.animeinfo.metadata.AnimeMetadataProvider;
import net.landora.video.ui.ContextProducer;
import net.landora.video.utils.UIUtils;

/**
 *
 * @author bdickie
 */

public class AnimeNotification implements java.io.Serializable, ContextProducer {
    private AnimeFile file;
    private Calendar addedDate;
    private Calendar viewDate;
    private Calendar removedDate;
    private NotificationType type;

    public AnimeNotification() {
    }

    public Calendar getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Calendar addedDate) {
        this.addedDate = addedDate;
    }

    public AnimeFile getFile() {
        return file;
    }

    public void setFile(AnimeFile file) {
        this.file = file;
    }

    public Calendar getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(Calendar removedDate) {
        this.removedDate = removedDate;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Calendar getViewDate() {
        return viewDate;
    }

    public void setViewDate(Calendar viewDate) {
        this.viewDate = viewDate;
    }
    
    public void setFileId(long fileId) {
        
    }
    
    public int getTypeId() {
        return getType().getType();
    }
    
    public void setTypeId(int typeId) {
        setType(NotificationType.lookupType(typeId));
    }

    @Override
    public String toString() {
        return file.toString();
    }

    @Override
    public void addContentObjects(Collection<Object> addTo) {
        addTo.add(this);
        UIUtils.addContentObject(AnimeMetadataProvider.getMetadata(file), addTo);
        UIUtils.addContentObject(file, addTo);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeNotification other = (AnimeNotification) obj;
        if (this.file != other.file && (this.file == null || !this.file.equals(other.file))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.file != null ? this.file.hashCode() : 0);
        return hash;
    }
    
    
    
}
