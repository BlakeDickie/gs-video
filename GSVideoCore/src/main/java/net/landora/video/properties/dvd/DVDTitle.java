/*
 * DVDTitle.java
 *
 * Created on January 6, 2007, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties.dvd;

import net.landora.video.properties.Video;

/**
 *
 * @author bdickie
 */
public class DVDTitle extends Video {
    
    private int number;
    private DVDDisk disk;
    
    /** Creates a new instance of DVDTitle */
    public DVDTitle(DVDDisk disk) {
        this.disk = disk;
    }

    public DVDDisk getDisk() {
        return disk;
    }

    

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DVD Title " + number;
    }

    @Override
    public boolean isDVD() {
        return true;
    }

    @Override
    public String getName() {
        return toString();
    }



}
