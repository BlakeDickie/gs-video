/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.mkv;

/**
 *
 * @author bdickie
 */
public class MKVAttachment {
    private String filename;
    private String mimeType;
    private String description;
    private long size;
    private long uid;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    @Override
    public String toString() {
        return getFilename();
    }
}
