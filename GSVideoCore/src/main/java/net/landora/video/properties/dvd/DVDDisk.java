/*
 * DVDDisk.java
 *
 * Created on January 6, 2007, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties.dvd;

import java.util.*;

/**
 *
 * @author bdickie
 */
public class DVDDisk {
    private List<DVDTitle> titles;
    private String title;

    private String dvdDevice;
    
    /** Creates a new instance of DVDDisk */
    public DVDDisk() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DVDTitle> getTitles() {
        return titles;
    }

    public void setTitles(List<DVDTitle> titles) {
        this.titles = titles;
    }

    public String getDvdDevice() {
        return dvdDevice;
    }

    public void setDvdDevice(String dvdDevice) {
        this.dvdDevice = dvdDevice;
    }

    
    
    @Override
    public String toString() {
        return getTitle();
    }
    
    public List<DVDTitle> getFilteredTitles() {
        ArrayList<DVDTitle> titles = new ArrayList<DVDTitle>(getTitles().size());
        for(DVDTitle title: getTitles()) {
            if (title.getLength() >= FILTER_LENGTH)
                titles.add(title);
        }
        
        return (titles.isEmpty() ? getTitles() : titles);
    }
    
    private static final float FILTER_LENGTH = 300;
}
