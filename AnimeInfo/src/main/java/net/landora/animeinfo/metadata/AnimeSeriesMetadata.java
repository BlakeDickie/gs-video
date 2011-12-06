/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.metadata;

import net.landora.videoinfo.SeriesMetadata;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;

/**
 *
 * @author bdickie
 */
public class AnimeSeriesMetadata extends AnimeMetadata implements SeriesMetadata {

    public AnimeSeriesMetadata(AnimeEpisode episode, AnimeFile file) {
        super(episode, file);
    }
    
    @Override
    public String getSeriesName() {
        return anime.getDisplayName();
    }

    @Override
    public String getEpisodeName() {
        return episode.getNameEnglish();
    }

    @Override
    public String getEpisodeNumber() {
        return episode.getEpisodeNumber();
    }
    
}
