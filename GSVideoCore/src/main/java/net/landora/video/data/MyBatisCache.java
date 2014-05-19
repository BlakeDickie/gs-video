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

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.ibatis.cache.Cache;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author bdickie
 */
public class MyBatisCache implements Cache {

    private String id;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public MyBatisCache( String id ) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    private Ehcache getCache() {
        return MyBatisCacheManager.getInstance().getCache( this );
    }

    @Override
    public int getSize() {
        return getCache().getSize();
    }

    @Override
    public void putObject( Object key, Object value ) {
        Element e = new Element( key, value );
        getCache().put( e );
    }

    @Override
    public Object getObject( Object key ) {
        Element e = getCache().get( key );
        if ( e == null ) {
            return null;
        } else {
            return e.getObjectValue();
        }
    }

    @Override
    public Object removeObject( Object key ) {
        Object returnValue = getObject( key );
        getCache().remove( key );
        return returnValue;
    }

    @Override
    public void clear() {
        getCache().removeAll();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return lock;
    }

    private boolean copy = true;

    public boolean isCopy() {
        return copy;
    }

    public void setCopy( boolean copy ) {
        this.copy = copy;
    }

    private int items = 512;

    public int getItems() {
        return items;
    }

    public void setItems( int items ) {
        this.items = items;
    }

}
