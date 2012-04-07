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

import java.util.ArrayList;
import java.util.List;
import net.landora.video.properties.Video;

/**
 *
 * @author bdickie
 */
public class MKVFile extends Video {
    private short[] uid;
    private List<MKVAttachment> attachments;

    public MKVFile() {
        attachments = new ArrayList<MKVAttachment>();
    }

    
    
    public short[] getUid() {
        return uid;
    }

    public void setUid(short[] uid) {
        this.uid = uid;
    }

    public List<MKVAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MKVAttachment> attachments) {
        this.attachments = attachments;
    }
    
    
}
