/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.mkv;

/**
 *
 * @author bdickie
 */
public interface MKVStream {
    
    public boolean isDefaultTrack();

    public void setDefaultTrack(boolean defaultTrack);

    public long getUid();

    public void setUid(long uid);
    
    public void setFormat(String format);
    
    public String getLanguage();
    
    public void setLanguage(String language);
    
    
    public int getTrackId();
    
    public void setTrackId(int trackId);
    
    
    public boolean isForcedTrack();

    public void setForcedTrack(boolean forcedTrack);
    
    
    public String getName();

    public void setName(String name);
    
    
    
}
