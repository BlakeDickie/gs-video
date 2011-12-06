/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilerenaming.api;

import java.io.File;
import net.landora.videofilerenaming.RenameScriptManager;
import net.landora.videofilerenaming.RenamingScript;
import net.landora.videoinfo.ExtensionUtils;
import net.landora.videoinfo.VideoMetadata;

/**
 *
 * @author bdickie
 */
public class RenamingManager {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static RenamingManager instance = new RenamingManager();
    }

    public static RenamingManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    
    private RenamingManager() {
        
    }
    
    public File getOutputFile(File file, VideoMetadata info) {
        RenamingScript script = RenameScriptManager.getInstance().getRenamingScript();
        
        if (info == null)
            return file;
        
        String outputFolder = script.findFolderName(info);
        String outputFile = script.findFilename(info);
        
        File outputFolderFile = (outputFolder == null ? file.getParentFile() : new File(outputFolder));
        if (outputFile == null) {
            outputFile = file.getName();
        } else {
            String extension = ExtensionUtils.getExtension(file);
            outputFile = outputFile + "." + extension;
        }
        
        return new File(outputFolderFile, outputFile);
    }
}
