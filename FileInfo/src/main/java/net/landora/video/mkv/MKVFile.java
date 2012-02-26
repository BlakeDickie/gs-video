/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.mkv;

import net.landora.video.properties.Video;
import java.util.ArrayList;
import java.util.List;

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
