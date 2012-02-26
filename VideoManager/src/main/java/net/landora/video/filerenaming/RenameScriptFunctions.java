/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import java.io.File;
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectory;
import net.landora.video.info.VideoInfoFileUtils;

/**
 *
 * @author bdickie
 */
public class RenameScriptFunctions {
    public static String filterInvalidFilenameCharacters(String filename) {
        return VideoInfoFileUtils.filterInvalidFilenameCharacters(filename);
    }
    
    public static String getSharedDirectoryPath(String name) {
        for (SharedDirectory dir : LocalPathManager.getInstance().getAvaliableLocalPaths()) {
            if (dir.getName().equalsIgnoreCase(name)) {
                return LocalPathManager.getInstance().getLocalPath(dir).getPath() + File.separator;
            }
        }
        throw new IllegalStateException("Shared directory is not avaliable: " + name);
    }
}
