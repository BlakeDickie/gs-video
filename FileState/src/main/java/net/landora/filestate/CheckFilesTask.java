/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.filestate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.landora.filestate.data.FileRecord;
import net.landora.filestate.data.LocalPathManager;
import net.landora.filestate.data.SharedDirectory;
import net.landora.filestate.data.SharedDirectoryDBA;
import net.landora.gsuiutils.NBTask;
import net.landora.gsuiutils.UIUtils;
import net.landora.videofilerenaming.api.RenamingManager;
import net.landora.videoinfo.ExtensionUtils;
import net.landora.videoinfo.MetadataProvidersManager;
import net.landora.videoinfo.VideoMetadata;
import net.landora.videoinfo.ViewListManager;
import net.landora.videoinfo.ViewListState;
import net.landora.videoinfo.file.FileInfo;
import net.landora.videoinfo.file.FileInfoManager;
import net.landora.videoinfo.file.FileManager;
import net.landora.videoinfo.util.Touple;

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
        
        switchToDeterminate(filesToCheck.size());
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
                
                SharedDirectoryDBA.saveFileRecord(fileRecord);
            }
            
            if (md != null) {
                if (fileRecord.isRename()) {
                    File outputFile = RenamingManager.getInstance().getOutputFile(file, md);

                    if (!outputFile.equals(file)) {
                        if (outputFile.exists()) {
                            System.err.println("File already exists: " + outputFile);
                        } else {
                            System.err.println("Move File: " + file + "   to   " + outputFile);
                            FileManager.getInstance().moveFile(file, outputFile);
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
                    if (outputFile.equals(file)) {
                        fileRecord.setRename(true);
                        SharedDirectoryDBA.saveFileRecord(fileRecord);
                    }
                }
            }
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
