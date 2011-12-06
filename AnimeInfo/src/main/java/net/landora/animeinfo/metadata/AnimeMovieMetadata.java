/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.metadata;

import net.landora.videoinfo.MovieMetadata;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;

/**
 *
 * @author bdickie
 */
public class AnimeMovieMetadata extends AnimeMetadata implements MovieMetadata {

    public AnimeMovieMetadata(AnimeEpisode episode, AnimeFile file) {
        super(episode, file);
    }

    @Override
    public String getMovieName() {
        return anime.getDisplayName();
    }
    
    
}
