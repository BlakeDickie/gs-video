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

package net.landora.video.data;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;

/**
 *
 * @author bdickie
 */
public class MyBatisCacheManager {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static MyBatisCacheManager instance = new MyBatisCacheManager();
    }

    public static MyBatisCacheManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private CacheManager cacheManager;
    
    private MyBatisCacheManager() {
        cacheManager = CacheManager.create(MyBatisCacheManager.class.getResource("ehcache.xml"));
    }
    
    public Ehcache getCache(MyBatisCache cacheObject) {
        Ehcache cache = cacheManager.getEhcache(cacheObject.getId());
        if (cache != null)
            return cache;
        
        CacheConfiguration config = new CacheConfiguration(cacheObject.getId(), cacheObject.getItems())
                .copyOnRead(cacheObject.isCopy())
                .copyOnWrite(cacheObject.isCopy());
        
        cache = new Cache(config);
        cacheManager.addCacheIfAbsent(cache);
        
        return cacheManager.getEhcache(cacheObject.getId());
    }
}
