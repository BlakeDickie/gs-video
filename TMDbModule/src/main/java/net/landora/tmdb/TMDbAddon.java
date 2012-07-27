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

import com.moviejukebox.themoviedb.MovieDbException;
import com.moviejukebox.themoviedb.TheMovieDb;
import com.moviejukebox.themoviedb.model.Genre;
import java.util.ArrayList;
import java.util.List;
import net.landora.tmdb.data.TMDbDataManager;
import net.landora.tmdb.data.entity.TMDbGenre;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.data.DataAddons;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.manager.ManagerAddon;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class TMDbAddon extends AbstractAddon {
    public static final String ID = "net.landora.tmdb.TMDbAddon";
    
    public static final String API_KEY = "84a76d3264028068970506ba25375a9e";
    
    private TheMovieDb api;

    public TMDbAddon() {
        super(ID, "The Movie DB", PreferencesAddon.ID, DataAddons.ID, UIAddon.ID, ManagerAddon.ID);
    }
    
    public static TMDbAddon getIntance() {
        return AddonManager.getInstance().getAddonInstance(TMDbAddon.class);
    }

    public TheMovieDb getApi() {
        return api;
    }
    
    @Override
    public void load() {
        
        try {
            api = new TheMovieDb(API_KEY);
        } catch (Exception ex) {
            log.error("Error loading TMDb API.", ex);
            return;
        }
        
        MetadataProvidersManager.getInstance().registerProvider(new TMDbMetadataProvider());
    }

    @Override
    public void ready() {
        if (api == null)
            return;
        
//        loadGenres();
        
    }
    
    
    private void loadGenres() {
        try {
            List<Genre> genres = api.getGenreList("eng");
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
        } catch (MovieDbException e) {
            log.error("Error loading TMDb Genres.", e);
        }
    }
}
