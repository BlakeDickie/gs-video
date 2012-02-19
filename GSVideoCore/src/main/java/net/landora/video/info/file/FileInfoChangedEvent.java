/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info.file;

import java.io.File;
import java.util.EventObject;

/**
 *
 * @author bdickie
 */
public class FileInfoChangedEvent extends EventObject {
    private File file;
    private FileInfo fileInfo;

    public FileInfoChangedEvent(File file, FileInfo fileInfo, Object source) {
        super(source);
        this.file = file;
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public File getFile() {
        return file;
    }
    
    
    
}
