/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.data;

import java.util.Calendar;
import java.util.Collection;
import net.landora.animeinfo.metadata.AnimeMetadataProvider;
import net.landora.gsuiutils.ContextActionObject;
import net.landora.gsuiutils.ContextProducer;
import net.landora.gsuiutils.UIUtils;

/**
 *
 * @author bdickie
 */
@ContextActionObject("Actions/AnimeNotification")
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
    
    
}
