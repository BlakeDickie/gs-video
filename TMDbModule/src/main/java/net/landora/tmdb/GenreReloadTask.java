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

package net.landora.tmdb;

import com.moviejukebox.themoviedb.model.Genre;
import java.util.ArrayList;
import java.util.List;
import net.landora.tmdb.data.TMDbDataManager;
import net.landora.tmdb.data.entity.TMDbGenre;
import net.landora.video.tasks.NBTask;

/**
 *
 * @author bdickie
 */
public class GenreReloadTask extends NBTask<Object, Object> {

    public GenreReloadTask() {
        super("Loading Anime Categories");
    }

    
    
    @Override
    protected Object doInBackground() throws Throwable {
        start();
        
        List<Genre> genres = TMDbAddon.getIntance().getApi().getGenreList("eng");
        TMDbDataManager data = TMDbDataManager.getInstance();
        List<TMDbGenre> toSave = new ArrayList<TMDbGenre>(genres.size());
        for(Genre g: genres) {
            TMDbGenre genre = data.getGenre(g.getId());
            if (genre == null) {
                genre = new TMDbGenre(g.getId(), g.getName());
            } else
                genre.setName(g.getName());
            toSave.add(genre);
        }
        data.saveGenres(toSave);
        
        return null;
    }

    @Override
    protected void success(Object result) {
    }
    
}
