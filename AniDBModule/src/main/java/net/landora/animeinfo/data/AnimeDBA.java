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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.landora.video.data.SimpleCache;
import net.landora.video.utils.Touple;
import net.landora.video.utils.UIUtils;
import net.sf.ehcache.CacheManager;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class AnimeDBA {
    private static Logger log = LoggerFactory.getLogger(AnimeDBA.class);

    public static final int ED2K_FILE_CACHE_AGE = 14;
    public static final int ED2K_FILE_CACHE_AGE_UNITS = Calendar.DATE;

    private static final CacheManager cacheManager;
    
    private static final SimpleCache<Integer,Anime> animeCache;
    private static final SimpleCache<Integer,AnimeFile> fileCache;
    private static final SimpleCache<Integer,AnimeListItem> myListCache;
    private static final SimpleCache<Integer,AnimeNotification> notificationCache;
    private static final SimpleCache<Touple<String,Long>,AnimeFile> fileED2KCache;
    
    static {
        cacheManager = CacheManager.create(AnimeDBA.class.getResource("ehcache.xml"));
        animeCache = new SimpleCache<Integer, Anime>("animeCache", cacheManager);
        fileCache = new SimpleCache<Integer, AnimeFile>("fileCache", cacheManager);
        fileED2KCache = new SimpleCache<Touple<String, Long>, AnimeFile>("fileED2kCache", cacheManager);
        myListCache = new SimpleCache<Integer, AnimeListItem>("myListCache", cacheManager);
        notificationCache = new SimpleCache<Integer, AnimeNotification>("notificationCache", cacheManager);
    }
    
    
    public static void saveAnimeWithNames(Anime anime) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            int rows = mapper.updateAnime(anime);
            if (rows == 0)
                mapper.insertAnime(anime);
            else {
                mapper.deleteAnimeNames(anime);
                mapper.deleteAnimeCategoryWeight(anime);
                mapper.deleteAnimeRelations(anime);
            }

            for(AnimeName name: anime.getNames())
                mapper.insertAnimeName(name);
            for(AnimeCategoryWeight category: anime.getCategories())
                mapper.insertAnimeCategoryWeight(category);
            for(AnimeRelation relation: anime.getRelations())
                mapper.insertAnimeRelation(relation);

            session.commit();
            
            animeCache.put(anime.getAnimeId(), anime);
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving anime.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }


    public static Anime getAnime(int animeId) {
        Anime result = animeCache.get(animeId);
        if (result != null)
            return result;
        
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            result = mapper.selectAnime(animeId);
            if (result != null)
                animeCache.put(result.getAnimeId(), result);
            
            return result;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }


    public static AnimeCategory getAnimeCategory(int categoryId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectCategory(categoryId);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static List<AnimeNameLookup> getLookupNames(int animeId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeNameLookupMapper mapper = session.getMapper(AnimeNameLookupMapper.class);

            return mapper.selectAnimeNames(animeId);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }



    public static void saveEpisode(AnimeEpisode episode) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            int rows = mapper.updateEpisode(episode);
            if (rows == 0)
                mapper.insertEpisode(episode);

            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving anime.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }




    public static AnimeEpisode getAnimeEpisode(int episodeId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectEpisode(episodeId);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }




    public static AnimeEpisode findAnimeEpisode(int animeId, int episodeNumber) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.findEpisode(animeId, episodeNumber);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }




    public static AnimeEpisode findAnimeEpisode(int animeId, String episodeNumber) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.findEpisodeFullEpisodeNumber(animeId, episodeNumber);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    


    public static void saveGroup(AnimeGroup group) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            int rows = mapper.updateGroup(group);
            if (rows == 0)
                mapper.insertGroup(group);

            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving group.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }




    public static AnimeGroup getAnimeGroup(int groupId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectGroup(groupId);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }



    public static void saveFile(AnimeFile file) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            file.setCurrentSaveRevision(AnimeFile.SAVE_REVISION);
            
            int rows = mapper.updateFile(file);
            if (rows == 0)
                mapper.insertFile(file);

            session.commit();
            
            addToAnimeFileCache(file);
            
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving file.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }




    public static AnimeFile getAnimeFile(int fileId) {
        AnimeFile file = fileCache.get(fileId);
        if (file != null)
            return file;
        
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            file = mapper.selectFile(fileId);
            if (file != null)
                addToAnimeFileCache(file);
            return file;
        } catch (Exception e) {
            log.error("Error getting file.", e);
            session.rollback();
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    private static void addToAnimeFileCache(AnimeFile file) {
        fileCache.put(file.getFileId(), file);
            if (file.getEd2k() != null && !file.getEd2k().isEmpty() && file.getSize() != null)
                fileED2KCache.put(new Touple<String, Long>(file.getEd2k(), file.getSize()), file);
    }


    public static AnimeFile getAnimeFile(String ed2k, long size) {
        Touple<String,Long> info = new Touple<String, Long>(ed2k, size);
        
        AnimeFile file = fileED2KCache.get(info);
        if (file != null)
            return file;
        
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            file = mapper.selectFileByED2K(ed2k, size);
            if (file != null)
                addToAnimeFileCache(file);
            return file;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting file.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }




    public static AnimeFile getGenericAnimeFile(AnimeEpisode episode) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectGenericFile(episode);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting file.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static List<AnimeNameLookup> searchAnimeNames(String name) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeNameLookupMapper mapper = session.getMapper(AnimeNameLookupMapper.class);

            return mapper.searchNames(name.replaceAll(Pattern.quote("%") + "|" + Pattern.quote("_"), "\\$1"));
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime name.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static List<AnimeNameLookup> findAnimeNames(String name) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeNameLookupMapper mapper = session.getMapper(AnimeNameLookupMapper.class);

            List<AnimeNameLookup> lookups = mapper.selectNames(name);
            if (!lookups.isEmpty())
                return lookups;
            else
                return mapper.selectNamesFull(name);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime name.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }



    public static AnimeEpisode findCachedED2K(String ed2k, long size) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.findCachedED2K(ed2k, size);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime name.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static void addCachedED2K(String ed2k, long size, int episodeId) {
        if (size == 0)
            return;
        
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            mapper.addCachedED2K(ed2k, size, episodeId);

            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving file.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static void addCachedED2KFileFailure(String ed2k, long size) {
        if (size == 0)
            return;

        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            mapper.addCachedED2KFileFailure(ed2k, size);

            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving file.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public static boolean checkForCachedED2KFileFailure(String ed2k, long size) {
        if (size == 0)
            return false;


        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            Calendar cal = Calendar.getInstance();
            cal.add(ED2K_FILE_CACHE_AGE_UNITS, -ED2K_FILE_CACHE_AGE);
            mapper.clearOldCachedED2KFileFailure(Collections.singletonMap("date", cal));

            int entries = mapper.findCachedED2KFileFailure(ed2k, size);

            session.commit();
            
            return entries > 0;
            
        } catch (Exception e) {
            session.rollback();
            log.error("Error checking for ed2k failure.", e);
            return false;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    public static void saveListItem(AnimeListItem list) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            int rows = mapper.updateListItem(list);
            if (rows == 0)
                mapper.insertListItem(list);

            session.commit();
            
            myListCache.put(list.getFile().getFileId(), list);
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving list item.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    public static void deleteListItem(AnimeListItem list) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            mapper.deleteListItem(list);

            session.commit();
            
            myListCache.put(list.getFile().getFileId(), null);
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving list item.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static AnimeListItem getAnimeListByFileId(int fileId) {
        AnimeListItem result = myListCache.get(fileId);
        if (result != null)
            return result;
        
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            result = mapper.selectListByFileId(fileId);
            if (result != null)
                myListCache.put(fileId, result);
            return result;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting list.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    public static boolean saveAnimeNotification(AnimeNotification notification) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            int rows = mapper.updateAnimeNotification(notification);
            if (rows == 0)
                mapper.insertAnimeNotification(notification);

            session.commit();
            
            notificationCache.put(notification.getFile().getFileId(), notification);
            return true;
        } catch (Exception e) {
            log.error("Error saving list item.", e);
            if (session != null)
                session.rollback();
            return false;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static AnimeNotification getAnimeNotificationByFileId(int fileId) {
        AnimeNotification result = notificationCache.get(fileId);
        if (result != null)
            return result;
        
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            result = mapper.selectAnimeNotificationByFileId(fileId);
            if (result != null)
                notificationCache.put(fileId, result);
            return result;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting notification.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static List<AnimeNotification> getOutstandAnimeNotifications() {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectWaitingAnimeNotification();
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting messages.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static List<AnimeStub> getAnimeWithWaitingNotification() {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);
            List<AnimeStub> result = mapper.selectAnimeWithWaitingNotification();
            Collections.sort(result, UIUtils.LEXICAL_SORTER);
            return result;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting messages.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static List<AnimeEpisode> getEpisodesForNotifications(int animeId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);
            List<AnimeEpisode> result = mapper.selectEpisodesForNotifications(animeId);
            Collections.sort(result, UIUtils.LEXICAL_SORTER);
            return result;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting messages.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static List<AnimeNotification> getNotificationsForEpisode(int animeId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);
            List<AnimeNotification> result = mapper.selectNotificationsForEpisode(animeId);
            Collections.sort(result, UIUtils.LEXICAL_SORTER);
            return result;
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting messages.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    
    
    public static boolean markDownloadedEpisodesAsCompletedNotifications() {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            mapper.markDownloadedEpisodesAsCompleted();
            session.commit();
            notificationCache.getCache().removeAll();
            return true;
        } catch (Exception e) {
            log.error("Error saving list item.", e);
            if (session != null)
                session.rollback();
            return false;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    
    
    public static boolean saveAnimeMessage(AnimeMessage msg) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            int rows = mapper.updateAnimeMessage(msg);
            if (rows == 0)
                mapper.insertAnimeMessage(msg);

            session.commit();
            return true;
        } catch (Exception e) {
            log.error("Error saving message.", e);
            if (session != null)
                session.rollback();
            return false;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static AnimeMessage getAnimeMessageByMessageId(int msgId) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectAnimeMessageByMessageId(msgId);
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting message.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static List<AnimeMessage> getOutstandAnimeMessages() {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            return mapper.selectWaitingAnimeMessage();
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting messages.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static byte[] getAnimePicture(String filename) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            Map map =  mapper.getAnimePicture(filename);
            if (map == null)
                return null;
            else
                return (byte[])map.get("image_data");
        } catch (Exception e) {
            session.rollback();
            log.error("Error getting anime picture.", e);
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    public static void saveAnimePicture(String filename, byte[] data) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);

            mapper.insertAnimePicture(filename, data);

            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving anime picture.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }
    

}
