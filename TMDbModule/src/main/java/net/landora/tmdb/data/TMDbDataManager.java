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
package net.landora.tmdb.data;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import net.landora.tmdb.data.entity.TMDbMovie;
import net.landora.video.data.AbstractJPAManager;

/**
 *
 * @author bdickie
 */
public class TMDbDataManager extends AbstractJPAManager {

    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static TMDbDataManager instance = new TMDbDataManager();
    }

    public static TMDbDataManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    
    private TMDbDataManager() {
    }

    @Override
    protected boolean createScheme() {
        return true;
    }
    
    public void save(TMDbMovie movie) {
        EntityManager manager = getEntityManagerFactory().createEntityManager();
        manager.getTransaction().begin();
        manager.persist(movie);
        manager.getTransaction().commit();
        manager.close();
    }
    
    @Override
    protected List<? extends Class> getEntityClasses() {
        return Arrays.asList(
                TMDbMovie.class
                );
    }
    
}
