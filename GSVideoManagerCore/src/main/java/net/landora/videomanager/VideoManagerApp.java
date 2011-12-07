/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videomanager;

import org.jdesktop.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class VideoManagerApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(VideoManagerApp.class);
    
    public static VideoManagerApp getInstance() {
        return getInstance(VideoManagerApp.class);
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
