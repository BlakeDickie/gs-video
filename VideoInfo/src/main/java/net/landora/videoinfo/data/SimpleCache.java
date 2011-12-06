/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo.data;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 *
 * @author bdickie
 */
public class SimpleCache<K, V> {

    private final String cacheName;
    private final CacheManager cacheManager;

    public SimpleCache(final String cacheName, final CacheManager cacheManager) {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
    }

    public void put(final K key, final V value) {
        getCache().put(new Element(key, value));
    }

    public V get(final K key) {
        Element element = getCache().get(key);
        if (element != null) {
            return (V) element.getValue();
        }
        return null;
    }

    public Ehcache getCache() {
        Ehcache result = cacheManager.getEhcache(cacheName);
        if (result == null) {
            cacheManager.addCacheIfAbsent(cacheName);
            result = cacheManager.getEhcache(cacheName);
        }
        return result;
    }
}
