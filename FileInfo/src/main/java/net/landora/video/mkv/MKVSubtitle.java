/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.mkv;

import net.landora.video.properties.SubtitleFormat;
import net.landora.video.properties.SubtitleStream;

/**
 *
 * @author bdickie
 */
public class MKVSubtitle extends SubtitleStream implements MKVStream {
    private boolean defaultTrack;
    private long uid;
    private int trackId;
    private boolean forcedTrack;
    private String name;

    public boolean isDefaultTrack() {
        return defaultTrack;
    }

    public void setDefaultTrack(boolean defaultTrack) {
        this.defaultTrack = defaultTrack;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public void setFormat(String format) {
        setFormat(SubtitleFormat.getFormat(format));
    }

    public boolean isForcedTrack() {
        return forcedTrack;
    }

    public void setForcedTrack(boolean forcedTrack) {
        this.forcedTrack = forcedTrack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        if (name != null && !name.equals("")) {
            return name + " (" + super.toString() + ")";
        } else
            return super.toString();
    }
    
}
