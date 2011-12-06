/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author bdickie
 */
public class ExtensionUtils {
    
    private static Set<String> extensions;
    
    public synchronized static boolean isVideoExtension(String extension) {
        if (extension == null)
            return false;
        
        if (extensions == null) {
            extensions = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            extensions.addAll(VideoInfoPreference.VideoExtensions.getStringList());
        }
        return extensions.contains(extension);
    }
    
    
    public static String getExtension(File file) {
        String filename = file.getName();
        int index = filename.lastIndexOf('.');
        if (index < 0)
            return null;
        
        return filename.substring(index + 1);
    }
    
    public static boolean isVideoExtension(File file) {
        return isVideoExtension(getExtension(file));
    }
}
