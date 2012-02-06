/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info;

import java.util.Map;

/**
 *
 * @author bdickie
 */
public interface VideoMetadata {
    public boolean isSeries();
    public boolean isMultiSeasonSeries();
    public boolean isMovie();
    public byte[] getPosterImage();
    public void addExtraInformation(Map<String,String> addTo);
    public ViewListManager getListManager();
    public String getTypeDescription();
    public boolean isAnime();
    public String getNfoUrl();
    public String getUniqueVideoId();
    
    public boolean isAdult();
}
