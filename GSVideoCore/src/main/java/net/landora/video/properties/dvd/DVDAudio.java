/*
 * DVDAudio.java
 *
 * Created on January 6, 2007, 5:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties.dvd;

import net.landora.video.properties.AudioStream;

/**
 *
 * @author bdickie
 */
public class DVDAudio extends AudioStream {
    private String content;
    
    /** Creates a new instance of DVDAudio */
    public DVDAudio() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        if (content != null && !content.equals("")  && !content.equalsIgnoreCase("Undefined")) {
            return content + " (" + super.toString() + ")";
        } else
            return super.toString();
    }
    
}
