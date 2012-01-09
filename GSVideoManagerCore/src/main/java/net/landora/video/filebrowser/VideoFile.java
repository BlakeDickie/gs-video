/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filebrowser;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Collection;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.file.FileInfo;
import net.landora.video.ui.ContextProducer;
import net.landora.video.utils.UIUtils;

/**
 *
 * @author bdickie
 */
public class VideoFile implements ContextProducer {
    
    private File file;
    private long length;

    public VideoFile(File file) {
        this.file = file;
        
        length = file.length();
        
        status = Status.Waiting;
    }
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        Runnable runnable = new Runnable() {

            public void run() {
                propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
            }
        };
        UIUtils.invokeLaterInSwingThread(runnable);
    }
    
    
    

    public long getLength() {
        return length;
    }
    
    
    
    
    
    protected Status status;
    public static final String PROP_STATUS = "status";

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(Status status) {
        Status oldStatus = this.status;
        this.status = status;
        propertyChangeSupport.firePropertyChange(PROP_STATUS, oldStatus, status);
    }

    protected VideoMetadata video;
    public static final String PROP_VIDEO = "video";

    public VideoMetadata getVideo() {
        return video;
    }

    public void setVideo(VideoMetadata video) {
        VideoMetadata oldVideo = this.video;
        this.video = video;
        propertyChangeSupport.firePropertyChange(PROP_VIDEO, oldVideo, video);
    }

    public File getFile() {
        return file;
    }
    
    
    protected FileInfo info;
    public static final String PROP_INFO = "info";

    /**
     * Get the value of info
     *
     * @return the value of info
     */
    public FileInfo getInfo() {
        return info;
    }

    /**
     * Set the value of info
     *
     * @param info new value of info
     */
    public void setInfo(FileInfo info) {
        FileInfo oldInfo = this.info;
        this.info = info;
        propertyChangeSupport.firePropertyChange(PROP_INFO, oldInfo, info);
    }

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public void addContentObjects(Collection<Object> addTo) {
        addTo.add(this);
        
        UIUtils.addContentObject(getFile(), addTo);
                
        if (getInfo() != null)
            UIUtils.addContentObject(getInfo(), addTo);

        if (getVideo() != null) {
            UIUtils.addContentObject(getVideo(), addTo);

        }
    }

    
    
    public static enum Status {
        Waiting,
        Identifying,
        Unknown,
        Identifed;
    }
}
