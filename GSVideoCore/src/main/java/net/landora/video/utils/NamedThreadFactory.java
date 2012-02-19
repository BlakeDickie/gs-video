/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    public NamedThreadFactory(String threadName, boolean daemon) {
        this.threadName = threadName;
        this.daemon = daemon;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, threadName);
        t.setDaemon(daemon);
        return t;
    }
    
    
    
}
