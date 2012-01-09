/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info;

/**
 *
 * @author bdickie
 */
public interface SeriesMetadata extends VideoMetadata {
    
    public String getSeriesName();
    public String getEpisodeName();
    public String getEpisodeNumber();
    
    public boolean sameSeries(SeriesMetadata other);
}
