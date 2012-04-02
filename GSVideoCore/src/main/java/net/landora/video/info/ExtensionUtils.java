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

package net.landora.video.info;

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
    
    
    public static String getFilenameWithoutExtension(File file) {
        String filename = file.getName();
        int index = filename.lastIndexOf('.');
        if (index <= 0)
            return filename;
        
        return filename.substring(0, index);
    }
    
    public static boolean isVideoExtension(File file) {
        return isVideoExtension(getExtension(file));
    }
}
