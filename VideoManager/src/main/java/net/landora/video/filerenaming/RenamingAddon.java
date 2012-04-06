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

package net.landora.video.filerenaming;

import java.io.File;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.filestate.data.FileRecord;
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectory;
import net.landora.video.filestate.data.SharedDirectoryDBA;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.ViewListManager;
import net.landora.video.info.ViewListState;
import net.landora.video.info.file.CheckFileExtension;
import net.landora.video.info.file.FileInfo;
import net.landora.video.info.file.FileInfoManager;
import net.landora.video.info.file.FileManager;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.tasks.PeriodicTaskManager;
import net.landora.video.ui.UIAddon;
import net.landora.video.utils.Touple;

/**
 *
 * @author bdickie
 */
public class RenamingAddon extends AbstractAddon {
    public static final String ID = RenamingAddon.class.getName();

    public static RenamingAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(RenamingAddon.class);
    }
    
    public RenamingAddon() {
        super(ID, "File Renaming", UIAddon.ID, PreferencesAddon.ID);
    }

    @Override
    public void start() {
        UIAddon.getInstance().addConfigurationPanel(FileRenamingConfigPanel.class);
        UIAddon.getInstance().addAction(new RefreshMetadataAction());
    }

    @Override
    public void ready() {
//        PeriodicTaskManager.getInstance().addPeriodicTask(new CheckFilesPeriodicTask());
    }
    
    
    public void checkFile(File file, boolean ignoreCache) {
        LocalPathManager pathMgr = LocalPathManager.getInstance();
        
        FileInfo info = FileInfoManager.getInstance().getFileInfo(file, false, ignoreCache);
        VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);

        Touple<SharedDirectory, String> subPath = pathMgr.findSubPath(file);
        if (subPath == null)
            return ;
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

        for(CheckFileExtension extension: FileManager.getInstance().getCheckFileExtensions())
            extension.checkFile(file, md, info);
    }
    
}
