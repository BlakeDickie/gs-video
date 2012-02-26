/*
 * DVDVideo.java
 *
 * Created on June 1, 2007, 11:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties.dvd;

import net.landora.video.properties.VideoFormat;
import net.landora.video.properties.VideoStream;

/**
 *
 * @author bdickie
 */
public class DVDVideo extends VideoStream {
    
    
    private int angles;
    
    /** Creates a new instance of DVDVideo */
    public DVDVideo() {
        setFormat(VideoFormat.MPEG2);
    }

    public int getAngles() {
        return angles;
    }

    public void setAngles(int angles) {
        this.angles = angles;
    }
    
}
