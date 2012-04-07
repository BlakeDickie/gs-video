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

package net.landora.video.mkv;

import java.text.NumberFormat;
import net.landora.video.properties.VideoFormat;
import net.landora.video.properties.VideoStream;

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
        if (name != null && !name.isEmpty()) {
            return name + " (" + super.toString() + ")";
        } else
            return super.toString();
    }
}
