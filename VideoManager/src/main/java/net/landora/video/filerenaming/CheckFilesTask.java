/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.landora.video.filestate.data.FileRecord;
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectory;
import net.landora.video.filestate.data.SharedDirectoryDBA;
import net.landora.video.tasks.NBTask;
import net.landora.video.utils.Touple;
import net.landora.video.utils.UIUtils;
import net.landora.video.filerenaming.RenamingManager;
import net.landora.video.info.ExtensionUtils;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.ViewListManager;
import net.landora.video.info.ViewListState;
import net.landora.video.info.file.CheckFileExtension;
import net.landora.video.info.file.FileInfo;
import net.landora.video.info.file.FileInfoManager;
import net.landora.video.info.file.FileManager;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author bdickie
 */
public class CheckFilesTask extends NBTask<Object, Object> {

    public CheckFilesTask() {
        super("Checking Files");
    }

    
    @Override
    protected Object doInBackground() throws Throwable {
        start();
        
        LocalPathManager pathMgr = LocalPathManager.getInstance();
        
        progress("Finding Files to Process");
        
        List<File> filesToCheck = new ArrayList<File>();
        for(SharedDirectory dir: pathMgr.getAvaliableLocalPaths()) {
            File folder = pathMgr.getLocalPath(dir);
            if (!folder.exists() || !folder.isDirectory())
                continue;
            
            checkDirectory(folder, filesToCheck);
        }
        
        Collections.sort(filesToCheck, UIUtils.LEXICAL_SORTER);
        
        Collection<CheckFileExtension> extensions = FileManager.getInstance().getCheckFileExtensions();
        
        switchToDeterminate(0, filesToCheck.size());
        for (int i = 0; i < filesToCheck.size(); i++) {
            File file = filesToCheck.get(i);
            progress(file.getName(), i);
            
            FileInfo info = FileInfoManager.getInstance().getFileInfo(file);
            VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);

//            if (md == null)
//                continue;
            
            Touple<SharedDirectory, String> subPath = pathMgr.findSubPath(file);
            if (subPath == null)
                continue;
            FileRecord fileRecord = SharedDirectoryDBA.getFileRecord(subPath.getFirst(), subPath.getSecond());
            if (fileRecord == null) {
                fileRecord = new FileRecord();
                fileRecord.setDirectory(subPath.getFirst());
                fileRecord.setE2dkHash(info.getE2dkHash());
                fileRecord.setFilename(info.getFilename());
                fileRecord.setFileSize(info.getFileSize());
                fileRecord.setLastModified(info.getLastModified());
                fileRecord.setMetadataId(info.getMetadataId());
                fileRecord.setMetadataSource(info.getMetadataSource());
                fileRecord.setRename(subPath.getFirst().isRenameNewFiles());
                fileRecord.setSubPath(subPath.getSecond());
                fileRecord.setVideoId(info.getVideoId());
                
                SharedDirectoryDBA.saveFileRecord(fileRecord);
            } else if (fileRecord.getVideoId() == null && info.getVideoId() != null) {
                fileRecord.setVideoId(info.getVideoId());
                
                SharedDirectoryDBA.saveFileRecord(fileRecord);
            }
            
            if (md != null) {
                File currentFileLocation = file;
                
                if (fileRecord.isRename()) {
                    File outputFile = RenamingManager.getInstance().getOutputFile(file, md);

                    if (outputFile != null && !outputFile.equals(file)) {
                        if (outputFile.exists()) {
                            System.err.println("File already exists: " + outputFile);
                        } else {
                            System.err.println("Move File: " + file + "   to   " + outputFile);
                            if (FileManager.getInstance().moveFile(file, outputFile))
                                currentFileLocation = outputFile;
                        }
                    }

                    ViewListManager listManager = md.getListManager();
                    if (listManager != null) {
                        ViewListState viewListState = listManager.getViewListState(md);
                        if (viewListState == null)
                            viewListState = listManager.createViewListState(md, null, ViewListState.DiskState.HardDisk);
                        else
                            viewListState.setDiskState(ViewListState.DiskState.HardDisk);
                    }
                } else {
                    File outputFile = RenamingManager.getInstance().getOutputFile(file, md);
                    if (outputFile != null && outputFile.equals(file)) {
                        fileRecord.setRename(true);
                        SharedDirectoryDBA.saveFileRecord(fileRecord);
                    }
                }
            }
            
            for(CheckFileExtension extension: extensions)
                extension.checkFile(file, md, info);
        }
        
        switchToIndeterminate();
        progress("Checking for Deleted Files");
        for(SharedDirectory dir: pathMgr.getAvaliableLocalPaths()) {
            File folder = pathMgr.getLocalPath(dir);
            if (!folder.exists() || !folder.isDirectory())
                continue;
            
            Collection<FileRecord> files = SharedDirectoryDBA.getFileRecords(dir);
            if (files != null) {
                for(FileRecord record: files) {
                    File file = new File(folder.getPath() + record.getSubPath().replace('/', File.separatorChar));
                    if (!file.exists()) {
                        SharedDirectoryDBA.deleteFileRecord(record);
                    }
                }
            }
        }
        
        return null;
    }
    
    public static void checkDirectory(File dir, List<File> filesToCheck) {
        for(File file : dir.listFiles()) {
            if (file.isHidden())
                continue;
            
            if (file.isDirectory()) {
                checkDirectory(file, filesToCheck);
            } else {
                String extension = ExtensionUtils.getExtension(file);
                if (ExtensionUtils.isVideoExtension(extension))
                    filesToCheck.add(file);
            }
        }
    }

    @Override
    protected void success(Object result) {
        
    }
    
}
