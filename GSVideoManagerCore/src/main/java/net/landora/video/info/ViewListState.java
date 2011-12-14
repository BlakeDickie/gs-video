/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info;

import java.util.Calendar;

/**
 *
 * @author bdickie
 */
public interface ViewListState {
    public Calendar getLastViewed();
    public void setLastViewed(Calendar lastViewed);
    public DiskState getDiskState();
    public void setDiskState(DiskState state);
    
    
    public static enum DiskState {
        HardDisk,
        DVD,
        Unknown,
        Deleted;
    }
}
