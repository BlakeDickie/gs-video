/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
