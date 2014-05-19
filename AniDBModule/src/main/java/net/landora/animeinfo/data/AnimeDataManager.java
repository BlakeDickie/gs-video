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
package net.landora.animeinfo.data;

import java.util.ArrayList;
import java.util.List;
import net.landora.video.data.AbstractDataManager;

/**
 *
 * @author bdickie
 */
public class AnimeDataManager extends AbstractDataManager {

    @Override
    protected List<? extends Class> getMapperClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(AnimeMapper.class);
        result.add(AnimeNameLookupMapper.class);
        return result;
    }

    @Override
    protected List<? extends Class> getAliasTypeClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(AnimeStub.class);
        result.add(Anime.class);
        result.add(AnimeCategory.class);
        result.add(AnimeName.class);
        result.add(AnimeNameLookup.class);
        result.add(AnimeNameLookupSummary.class);
        result.add(AnimeCategoryWeight.class);
        result.add(AnimeRelation.class);
        result.add(AnimeEpisode.class);
        result.add(AnimeFile.class);
        result.add(AnimeGroup.class);
        result.add(AnimeListItem.class);
        result.add(AnimeNotification.class);
        result.add(AnimeMessage.class);
        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.instance ,
     * not before.
     */
    private static class SingletonHolder {

        private final static AnimeDataManager instance = new AnimeDataManager();
    }

    public static AnimeDataManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private AnimeDataManager() {

    }
}
