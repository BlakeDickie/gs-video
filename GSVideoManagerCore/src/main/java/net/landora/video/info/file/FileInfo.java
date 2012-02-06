/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.info.file;

import java.io.Serializable;

/**
 *
 * @author bdickie
 */
public class FileInfo implements Serializable {

    private String filename;
    private long fileSize;
    private String e2dkHash;
    private String metadataSource;
    private String metadataId;
    private String videoId;
    private long lastModified;

    public FileInfo() {
    }
    
    @Override
    public FileInfo clone() {
        FileInfo info = new FileInfo();
        info.filename = filename;
        info.fileSize = fileSize;
        info.e2dkHash = e2dkHash;
        info.metadataSource = metadataSource;
        info.metadataId = metadataId;
        info.lastModified = lastModified;
        info.videoId = videoId;
        
        return info;
    }

    public String getE2dkHash() {
        return e2dkHash;
    }

    public void setE2dkHash(String e2dkHash) {
        this.e2dkHash = e2dkHash;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetadataSource() {
        return metadataSource;
    }

    public void setMetadataSource(String metadataSource) {
        this.metadataSource = metadataSource;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s", getFilename(), getMetadataSource(), getMetadataId());
    }

    
    
}
