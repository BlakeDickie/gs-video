/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.preferences;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AddonManager;
import net.landora.video.data.LocalStorage;
import net.landora.video.utils.ComparisionUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class FileSyncProperties {
    private String filename;

    public FileSyncProperties(String filename) {
        this.filename = filename;
        
        values = new HashMap<String, String>();
        changedValues = new HashSet<String>();
        
        sync();
        
        AddonManager.getInstance().getAddonInstance(PreferencesAddon.class).addShutdownCall(new QuitListener());
        
        VideoManagerApp.getInstance().getScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            public void run() {
                sync();
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
    
    private Map<String,String> values;
    private Set<String> changedValues;
    
    public final synchronized void sync() {
        try {
            Properties props = new Properties();
            if (LocalStorage.getInstance().doesSettingFileExist(filename)) {
                try {
                    InputStream is = LocalStorage.getInstance().openInputFile(filename);
                    props.load(is);
                    is.close();
                } catch (Exception ignore) { }
            }
            
            for(String name: changedValues) {
                String value = values.get(name);
                if (value == null)
                    props.remove(name);
                else
                    props.setProperty(name, value);
            }
            
            OutputStream os = LocalStorage.getInstance().openOutputFile(filename);
            props.store(os, "");
            os.close();
            
            changedValues.clear();
            values.clear();
            
            for (String name : props.stringPropertyNames()) {
                values.put(name, props.getProperty(name));
            }
            
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Error syncing properties.", e);
        }
    }
    
    public synchronized String getValue(String name) {
        return values.get(name);
    }
    
    public synchronized void setValue(String name, String value) {
        String oldValue = values.put(name, value);
        if (!ComparisionUtils.equals(oldValue, value))
            changedValues.add(name);
    }
    
    private class QuitListener implements Runnable {

        @Override
        public void run() {
            sync();
        }
        
    }
}
