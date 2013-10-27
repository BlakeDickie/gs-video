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
package net.landora.xbmc.database;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import net.landora.video.data.AbstractJPAManager;
import net.landora.xbmc.database.entities.Country;
import net.landora.xbmc.database.entities.Episode;
import net.landora.xbmc.database.entities.Genre;
import net.landora.xbmc.database.entities.MovieSet;
import net.landora.xbmc.database.entities.Studio;
import net.landora.xbmc.database.entities.VideoFile;

/**
 *
 * @author bdickie
 */
public class XBMCDatabase extends AbstractJPAManager {
    
    public static final String DB_VERSION = "75";

    // <editor-fold defaultstate="collapsed" desc="JPA Setup">
    private DataSource dataSource;

    public XBMCDatabase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected DataSource getDataSource() {
        return dataSource;
    }
    
    @Override
    protected boolean createScheme() {
        return false;
    }

    @Override
    protected List<? extends Class> getEntityClasses() {
        return Arrays.asList(
                net.landora.xbmc.database.entities.TvShow.class,
                net.landora.xbmc.database.entities.Path.class,
                net.landora.xbmc.database.entities.Studio.class,
                net.landora.xbmc.database.entities.Art.class,
                net.landora.xbmc.database.entities.Tag.class,
                net.landora.xbmc.database.entities.MusicVideo.class,
                net.landora.xbmc.database.entities.MovieSet.class,
                net.landora.xbmc.database.entities.VideoFile.class,
                net.landora.xbmc.database.entities.Country.class,
                net.landora.xbmc.database.entities.Episode.class,
                net.landora.xbmc.database.entities.Bookmark.class,
                net.landora.xbmc.database.entities.Person.class,
                net.landora.xbmc.database.entities.Genre.class,
                net.landora.xbmc.database.entities.Movie.class,
                net.landora.xbmc.database.entities.Seasons.class,
                net.landora.xbmc.database.entities.TvShow.class,
                net.landora.xbmc.database.entities.MusicVideo.class);
    }
    // </editor-fold>
    
    private static <T> T getSingleResult(TypedQuery<T> query) {
        List<T> result = query.getResultList();
        if (result == null || result.isEmpty())
            return null;
        else
            return result.get(0);
    }
    
    public VideoFile getFileAt(File file) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        String fileName = file.getName();
        String folder = file.getParent();
        if (!folder.endsWith(File.separator))
            folder += File.separator;
        
        TypedQuery<VideoFile> query = em.createNamedQuery("VideoFile.findByPath", VideoFile.class);
        query.setParameter("filename", fileName);
        query.setParameter("path", folder);
        
        VideoFile result = getSingleResult(query);
        
        em.close();
        return result;
    }
    
    public Episode getEpisodeForFile(VideoFile file) {
        
        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        TypedQuery<Episode> query = em.createNamedQuery("Episode.findByFile", Episode.class);
        query.setParameter("file", file);
        
        Episode result = getSingleResult(query);
        
        em.close();
        return result;
    }
    
    
    public Genre getGenre(String genreName) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        TypedQuery<Genre> query = em.createNamedQuery("Genre.findByName", Genre.class);
        query.setParameter("name", genreName);
        List<Genre> list = query.getResultList();
        Genre result;
        if (list == null || list.isEmpty()) {
            result = new Genre();
            result.setGenreName(genreName);
            
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            
        } else
            result = list.get(0);
        
        em.close();
        return result;
    }
    
    
    public MovieSet getMovieSet(String setName) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        TypedQuery<MovieSet> query = em.createNamedQuery("MovieSet.findByName", MovieSet.class);
        query.setParameter("name", setName);
        List<MovieSet> list = query.getResultList();
        MovieSet result;
        if (list == null || list.isEmpty()) {
            result = new MovieSet();
            result.setSetName(setName);
            
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            
        } else
            result = list.get(0);
        
        em.close();
        return result;
    }
    
    
    public Country getCountry(String countryName) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        TypedQuery<Country> query = em.createNamedQuery("Country.findByName", Country.class);
        query.setParameter("name", countryName);
        List<Country> list = query.getResultList();
        Country result;
        if (list == null || list.isEmpty()) {
            result = new Country();
            result.setCountryName(countryName);
            
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            
        } else
            result = list.get(0);
        
        em.close();
        return result;
    }
    
    
    public Studio getStudio(String studioName) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        
        TypedQuery<Studio> query = em.createNamedQuery("Studio.findByName", Studio.class);
        query.setParameter("name", studioName);
        List<Studio> list = query.getResultList();
        Studio result;
        if (list == null || list.isEmpty()) {
            result = new Studio();
            result.setStudioName(studioName);
            
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            
        } else
            result = list.get(0);
        
        em.close();
        return result;
    }
}
