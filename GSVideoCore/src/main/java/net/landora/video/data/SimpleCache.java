/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.data;

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

    public SimpleCache( final String cacheName, final CacheManager cacheManager ) {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
    }

    public void put( final K key, final V value ) {
        getCache().put( new Element( key, value ) );
    }

    public V get( final K key ) {
        Element element = getCache().get( key );
        if ( element != null ) {
            return (V) element.getValue();
        }
        return null;
    }

    public Ehcache getCache() {
        Ehcache result = cacheManager.getEhcache( cacheName );
        if ( result == null ) {
            cacheManager.addCacheIfAbsent( cacheName );
            result = cacheManager.getEhcache( cacheName );
        }
        return result;
    }
}
