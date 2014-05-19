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
package net.landora.video.utils;

import java.util.concurrent.ThreadFactory;

/**
 *
 * @author bdickie
 */
public class NamedThreadFactory implements ThreadFactory {

    private String threadName;
    private boolean daemon;

    public NamedThreadFactory( String threadName, boolean daemon ) {
        this.threadName = threadName;
        this.daemon = daemon;
    }

    public Thread newThread( Runnable r ) {
        Thread t = new Thread( r, threadName );
        t.setDaemon( daemon );
        return t;
    }

}
