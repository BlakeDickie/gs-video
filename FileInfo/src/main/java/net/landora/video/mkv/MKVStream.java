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
