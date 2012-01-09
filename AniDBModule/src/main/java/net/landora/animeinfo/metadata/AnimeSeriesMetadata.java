/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.metadata;

import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.video.info.SeriesMetadata;

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

    public boolean sameSeries(SeriesMetadata other) {
        if (other == this)
            return true;
        
        if (!(other instanceof AnimeSeriesMetadata))
            return false;
        
        AnimeSeriesMetadata md = (AnimeSeriesMetadata)other;
        return md.anime.getAnimeId() == anime.getAnimeId();
    }
    
    
}
