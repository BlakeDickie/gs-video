/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info.data;

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
