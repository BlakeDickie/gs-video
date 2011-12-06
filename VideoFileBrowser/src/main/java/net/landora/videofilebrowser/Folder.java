/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author bdickie
 */
public class Folder {
    private File file;

    public Folder(File file) {
        this.file = file;
    }
    
    public static List<Folder> getRootFolders() {
        List<Folder> folders = new ArrayList<Folder>();
        
        for(File file: File.listRoots()) {
            folders.add(new Folder(file));
        }
        return folders;
    }

    public String getDisplayName() {
        if (file.getPath().equals("/"))
            return "/";
        else
            return file.getName();
    }
    
    @Override
    public String toString() {
        return getDisplayName();
    }

    public File getFile() {
        return file;
    }
    
    
    public List<Folder> getChildrenFolders() {
        List<Folder> folders = new ArrayList<Folder>();
        File[] files = file.listFiles();
        if (files == null)
            return Collections.EMPTY_LIST;
        
        for(File children: files) {
            if (children.isDirectory()) {
                folders.add(new Folder(children));
            }
        }
        return folders;
    }
    
    
}
