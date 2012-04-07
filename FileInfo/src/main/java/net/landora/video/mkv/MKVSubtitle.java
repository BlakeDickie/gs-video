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
        if (name != null && !name.isEmpty()) {
            return name + " (" + super.toString() + ")";
        } else
            return super.toString();
    }
    
}
