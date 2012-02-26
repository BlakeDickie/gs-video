/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.data;

import java.io.*;
import net.landora.video.utils.OSUtils;

/**
 *
 * @author bdickie
 */
public class LocalStorage {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static LocalStorage instance = new LocalStorage();
    }

    public static LocalStorage getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>  
    
    private LocalStorage() {
        File userFolder = null;
        if (OSUtils.isWindows()) {
            String appFolder = System.getenv("APPDATA");
            if (appFolder != null && !appFolder.trim().isEmpty())
                userFolder = new File(appFolder);
        }
        
        if (userFolder == null)
            userFolder = new File(System.getProperty("user.home"));
        
        if (OSUtils.isLinux())
            profileFolder = new File(new File(userFolder, ".config"), "gsvideo");
        else
            profileFolder = new File(userFolder, ".gsvideo");
        
        
        if (!profileFolder.isDirectory())
            profileFolder.mkdirs();
    }
    
    private File profileFolder;
    
    public File getProfileFolder() {
        return profileFolder;
    }
    
    public File getSettingFile(String filename) {
        return new File(getProfileFolder(), filename);
    }
    
    public boolean doesSettingFileExist(String filename) {
        return getSettingFile(filename).exists();
    }
    
    public InputStream openInputFile(String filename) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(getSettingFile(filename)));
    }
    
    public OutputStream openOutputFile(String filename) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(getSettingFile(filename)));
    }
    
    
}
