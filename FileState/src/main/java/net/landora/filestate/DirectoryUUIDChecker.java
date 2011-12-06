/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.filestate;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author bdickie
 */
public class DirectoryUUIDChecker {
    private static final Logger log = LoggerFactory.getLogger(DirectoryUUIDChecker.class);
    
    public static final String UUID_FILE = ".gs_video_uuid";
    
    public static boolean validateUUID(File directory, String uuid) {
        try {
            File uuidFile = new File(directory, UUID_FILE);
            if (!uuidFile.exists())
                return false;
            List<String> lines = FileUtils.readLines(uuidFile);
            return lines.contains(uuid);
        } catch (Exception e) {
            log.error("Error checking for directory uuid: " + directory, e);
            return false;
        }
    }
    
    public static void setUUID(File directory, String uuid) {
        try {
            File uuidFile = new File(directory, UUID_FILE);
            Set<String> uuids = new LinkedHashSet<String>();
            
            if (uuidFile.exists())
                uuids.addAll(FileUtils.readLines(uuidFile));
            uuids.add(uuid);
            FileUtils.writeLines(uuidFile, uuids);
        } catch (Exception e) {
            log.error("Error adding directory uuid: " + directory, e);
            
        }
    }
}
