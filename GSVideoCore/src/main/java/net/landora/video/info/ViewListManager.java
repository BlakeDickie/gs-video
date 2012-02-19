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
public interface ViewListManager<T extends VideoMetadata, V extends ViewListState> {
    public V getViewListState(T videoMetadata);
    public V createViewListState(T videoMetadata, Calendar viewDate, ViewListState.DiskState diskState);
}
