/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.mkv;

import net.landora.video.properties.VideoFormat;
import net.landora.video.properties.VideoStream;
import java.text.NumberFormat;

/**
 *
 * @author bdickie
 */
public class MKVVideo extends VideoStream implements MKVStream {

    private boolean defaultTrack;
    private boolean forcedTrack;
    private long uid;
    private int displayWidth;
    private int displayHeight;
    private boolean interlaced;
    private int trackId;
    private String name;

    public boolean isDefaultTrack() {
        return defaultTrack;
    }

    public void setDefaultTrack(boolean defaultTrack) {
        this.defaultTrack = defaultTrack;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public boolean isInterlaced() {
        return interlaced;
    }

    public void setInterlaced(boolean interlaced) {
        this.interlaced = interlaced;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @Override
    public String getAspect() {
        double aspect = ((double)getDisplayWidth())/getDisplayHeight();
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setGroupingUsed(false);
        return format.format(aspect);
    }

    public void setFormat(String format) {
        setFormat(VideoFormat.getFormat(format));
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
