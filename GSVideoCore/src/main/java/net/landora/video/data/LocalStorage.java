/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
