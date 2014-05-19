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

import java.util.List;

/**
 *
 * @author bdickie
 */
public interface AnimeNameLookupMapper {

    public void insertAnime(AnimeNameLookupSummary anime);

    public void insertAnimeName(AnimeNameLookup name);

    public void deleteAnimeNames();

    public AnimeNameLookupSummary selectAnimeNameLookupSummary(int animeId);

    public List<AnimeNameLookup> selectAnimeNames(int animeId);

    public List<AnimeNameLookup> selectNames(String animeName);

    public List<AnimeNameLookup> searchNames(String escapedAnimeName);

    public List<AnimeNameLookup> selectNamesFull(String animeName);

    public List<AnimeNameLookup> searchNamesFull(String escapedAnimeName);

    public void updateExistingAnimeMainName();

    public void deleteCoreAnimeNames();

    public void insertExistingAnimeMainName();
}
