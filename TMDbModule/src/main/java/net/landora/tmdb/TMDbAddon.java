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

import net.landora.tmdb.data.TMDbDataManager;
import net.landora.tmdb.data.entity.TMDbMovie;
import net.landora.video.addons.AbstractAddon;
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

    public TMDbAddon() {
        super(ID, "The Movie DB", PreferencesAddon.ID, DataAddons.ID, UIAddon.ID, ManagerAddon.ID);
    }

    @Override
    public void load() {
        MetadataProvidersManager.getInstance().registerProvider(new TMDbMetadataProvider());
    }

    @Override
    public void ready() {
        TMDbMovie movie = new TMDbMovie();
        movie.setName("Testing");
        TMDbDataManager.getInstance().save(movie);
    }
    
    
    
}
