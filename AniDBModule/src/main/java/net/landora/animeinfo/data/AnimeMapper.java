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
import java.util.List;
import java.util.Map;

/**
 *
 * @author bdickie
 */
public interface AnimeMapper {

    public void insertAnime(Anime anime);
    public int updateAnime(Anime anime);
    public int deleteAnime(AnimeStub anime);
    public int deleteAnimeById(int animeId);

    public AnimeStub selectAnimeStub(int animeId);
    public Anime selectAnime(int animeId);

    public void insertAnimeName(AnimeName name);
    public void deleteAnimeNames(AnimeStub anime);

    public void insertAnimeCategoryWeight(AnimeCategoryWeight name);
    public void deleteAnimeCategoryWeight(AnimeStub anime);

    public void insertAnimeRelation(AnimeRelation name);
    public void deleteAnimeRelations(AnimeStub anime);

    public void insertCategory(AnimeCategory anime);
    public int updateCategory(AnimeCategory anime);
    public AnimeCategory selectCategory(int categoryid);

    public void insertEpisode(AnimeEpisode episode);
    public int updateEpisode(AnimeEpisode episode);
    public AnimeEpisode selectEpisode(int episodeId);
    public AnimeEpisode findEpisode(int animeId, int episodeNumber);
    public AnimeEpisode findEpisodeFullEpisodeNumber(int animeId, String episodeNumber);

    public void insertGroup(AnimeGroup group);
    public int updateGroup(AnimeGroup group);
    public AnimeGroup selectGroup(int groupId);

    public void insertFile(AnimeFile file);
    public int updateFile(AnimeFile file);
    public AnimeFile selectFile(int fileId);
    public AnimeFile selectFileByED2K(String ed2k, long size);
    public AnimeFile selectGenericFile(AnimeEpisode forEpisode);
    
    public void insertListItem(AnimeListItem listItem);
    public int updateListItem(AnimeListItem listItem);
    public AnimeListItem selectListByFileId(int fileId);
    public int deleteListItem(AnimeListItem listItem);
    
    
    public void insertAnimeNotification(AnimeNotification listItem);
    public int updateAnimeNotification(AnimeNotification listItem);
    public List<AnimeNotification> selectWaitingAnimeNotification();
    public AnimeNotification selectAnimeNotificationByFileId(int fileId);
    public List<AnimeStub> selectAnimeWithWaitingNotification();
    public void markDownloadedEpisodesAsCompleted();
    public List<AnimeEpisode> selectEpisodesForNotifications(int animeId);
    public List<AnimeNotification> selectNotificationsForEpisode(int episode);
    
    
    public void insertAnimeMessage(AnimeMessage listItem);
    public int updateAnimeMessage(AnimeMessage listItem);
    public List<AnimeMessage> selectWaitingAnimeMessage();
    public AnimeMessage selectAnimeMessageByMessageId(int messageId);
    
    public AnimeEpisode findCachedED2K(String ed2k, long size);
    public void addCachedED2K(String ed2k, long size, int episodeId);
    
    public int findCachedED2KFileFailure(String ed2k, long size);
    public void addCachedED2KFileFailure(String ed2k, long size);
    public void clearOldCachedED2KFileFailure(Map<String,Calendar> cachedBefore);
    
    public void insertAnimePicture(String filename, byte[] imageData);
    public Map getAnimePicture(String filename);
}
