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


package net.landora.animeinfo.data;

import java.util.Calendar;
import net.landora.animeinfo.anidb.AniDB;

/**
 *
 * @author bdickie
 */
public class AnimeManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static AnimeManager instance = new AnimeManager();
    }

    public static AnimeManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>


    private AnimeManager() {
    }


    public Anime getAnime(int animeId) {
        Anime anime = AnimeDBA.getAnime(animeId);
        if (anime != null)
            return anime;

        anime = AniDB.downloadAnime(animeId);
        if (anime != null)
            AnimeDBA.saveAnimeWithNames(anime);
        
        return anime;
    }


    public Anime refreshAnime(int animeId) {
        Anime anime = AniDB.downloadAnime(animeId);
        if (anime != null)
            AnimeDBA.saveAnimeWithNames(anime);
        
        return anime;
    }

    public AnimeEpisode findEpisode(int animeId, int episodeNumber) {
        AnimeEpisode episode = AnimeDBA.findAnimeEpisode(animeId, episodeNumber);
        if (episode != null)
            return episode;

        episode = AniDB.getAnimeEpisode(animeId, episodeNumber);
        if (episode != null)
            AnimeDBA.saveEpisode(episode);
        
        return episode;
    }

    public AnimeEpisode findEpisode(int animeId, String episodeNumber) {
        AnimeEpisode episode = AnimeDBA.findAnimeEpisode(animeId, episodeNumber);
        if (episode != null)
            return episode;

        episode = AniDB.getAnimeEpisode(animeId, episodeNumber);
        if (episode != null)
            AnimeDBA.saveEpisode(episode);
        
        return episode;
    }

    public AnimeEpisode findEpisode(int episodeId) {
        AnimeEpisode episode = AnimeDBA.getAnimeEpisode(episodeId);
        if (episode != null)
            return episode;

        episode = AniDB.getAnimeEpisode(episodeId);
        if (episode != null)
            AnimeDBA.saveEpisode(episode);

        return episode;
    }

    public AnimeFile findFile(String ed2k, long size) {
        AnimeFile file = AnimeDBA.getAnimeFile(ed2k, size);
        if (file != null)
            return file;

        file = AniDB.getAnimeFile(ed2k, size);
        if (file != null)
            AnimeDBA.saveFile(file);
        else
            AnimeDBA.addCachedED2KFileFailure(ed2k, size);

        return file;
    }

    public AnimeFile findFile(int fileId) {
        AnimeFile file = AnimeDBA.getAnimeFile(fileId);
        if (file != null && file.getCurrentSaveRevision() >= AnimeFile.SAVE_REVISION)
            return file;

        file = AniDB.getAnimeFile(fileId);
        if (file != null)
            AnimeDBA.saveFile(file);

        return file;
    }

    public AnimeFile updateFile(int fileId) {
        AnimeFile file = AniDB.getAnimeFile(fileId);
        if (file != null)
            AnimeDBA.saveFile(file);

        return file;
    }

    public AnimeListItem getOrCreateGenericListItem(AnimeEpisode episode, Calendar viewDate, AnimeListState listState) {
        AnimeFile file = AnimeDBA.getGenericAnimeFile(episode);
        if (file != null)
            return getOrCreateListItemByFileId(file.getFileId(), viewDate, listState);
        
        AnimeListItem item = AniDB.addGenericListitem(episode, viewDate, listState);
        AnimeDBA.saveListItem(item);
        
        return item;
    }

    public AnimeListItem getOrCreateListItemByFileId(int fileId, Calendar viewDate, AnimeListState listState) {
        AnimeListItem listItem = findListItemByFileId(fileId);
        if (listItem != null)
            return listItem;
        
        listItem = AniDB.addListitem(fileId, viewDate, listState);
        if (listItem != null)
            AnimeDBA.saveListItem(listItem);
        
        return listItem;
    }

    public AnimeListItem findListItemByFileId(int fileId) {
        return findListItemByFileId(fileId, false);
    }
    
    public AnimeListItem findListItemByFileId(int fileId, boolean localOnly) {
        AnimeListItem listItem = AnimeDBA.getAnimeListByFileId(fileId);
        if (listItem != null)
            return listItem;

        if (!localOnly) {
            listItem = AniDB.getAnimeListItemByFileId(fileId);
            if (listItem != null)
                AnimeDBA.saveListItem(listItem);
        }

        return listItem;
    }

    
    public void saveListItem(AnimeListItem item) {
        AniDB.updateListItem(item);
        AnimeDBA.saveListItem(item);
    }

    
    public void deleteListItem(AnimeListItem item) {
        AniDB.deleteListItem(item);
        AnimeDBA.deleteListItem(item);
    }
    
    public void ensureGroupIsFullyLoaded(AnimeGroup group) {
        if (group == null || group.isFullyLoaded())
            return;
        
        AnimeGroup newGroup = AniDB.getAnimeGroup(group.getGroupId());
        
        AnimeDBA.saveGroup(newGroup);
        
        group.setUrl(newGroup.getUrl());
        group.setFullyLoaded(newGroup.isFullyLoaded());
    }
}
