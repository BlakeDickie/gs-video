/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.animeinfo.metadata;

import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.video.info.MovieMetadata;

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
