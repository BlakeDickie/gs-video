/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.landora.video.info.data.preferences.FileSyncProperties;
import net.landora.video.utils.NamedThreadFactory;
import org.jdesktop.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class VideoManagerApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(VideoManagerApp.class);
    
    private FileSyncProperties localProperties;
    private ScheduledExecutorService scheduledExecutor;
    
    public static VideoManagerApp getInstance() {
        return getInstance(VideoManagerApp.class);
    }

    public synchronized FileSyncProperties getLocalProperties() {
        if (localProperties == null)
            localProperties = new FileSyncProperties("user.properties");
        return localProperties;
    }
    
    public synchronized ScheduledExecutorService getScheduledExecutor() {
        if (scheduledExecutor == null)
            scheduledExecutor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("Scheduled", true));
        return scheduledExecutor;
    }
    
    
    
    @Override
    protected void startup() {
        
    }

    @Override
    protected void initialize(String[] args) {
    }

    @Override
    protected void ready() {
        
    }

    
    
    
}
