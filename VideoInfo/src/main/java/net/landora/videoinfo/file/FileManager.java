/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo.file;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class FileManager {
    private Logger log = LoggerFactory.getLogger(getClass());

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static FileManager instance = new FileManager();
    }

    public static FileManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>
    
    private FileManager() {
        infoManager = FileInfoManager.getInstance();
    }
    
    private FileInfoManager infoManager;
    
    public boolean deleteFile(File file) {
        boolean result = file.delete();
        if (result)
            infoManager.removeFileInfo(file);
        return result;
    }
    
    public boolean moveFile(File srcFile, File destFile) {
        return moveFile(srcFile, destFile, true);
    }
    
    public boolean moveFile(File srcFile, File destFile, boolean deleteSourceIfEmpty) {
        FileInfo info = infoManager.getFileInfo(srcFile);
        info = info.clone();
        
        try {
            FileUtils.moveFile(srcFile, destFile);
        } catch (Exception e) {
            log.warn(String.format("Error moving file \"%s\" to \"%s\".", srcFile.getPath(), destFile.getPath()), e);
            return false;
        }
        infoManager.removeFileInfo(srcFile);
        info.setFilename(destFile.getName());
        infoManager.setFileInfo(destFile, info);
        
        if (deleteSourceIfEmpty) {
            File srcDir = srcFile.getParentFile();
            File[] children = srcDir.listFiles();
            boolean empty = true;
            if (children == null) {
                for(File file: children) {
                    if (!file.getName().equals(".") && !file.getName().equals("..")) {
                        empty = false;
                        break;
                    }
                }
            }
            if (empty)
                srcDir.delete();
        }
        
        return true;
    }
}
