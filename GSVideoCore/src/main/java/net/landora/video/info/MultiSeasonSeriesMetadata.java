/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info;

/**
 *
 * @author bdickie
 */
public interface MultiSeasonSeriesMetadata extends SeriesMetadata {
    public int getSeasonNumber();
    public int getEpisodeInSeason();
}