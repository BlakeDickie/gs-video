/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
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
import net.landora.video.info.ExtensionUtils;
import net.landora.video.tasks.NBTask;
import net.landora.video.utils.UIUtils;

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
        for (SharedDirectory dir : pathMgr.getAvaliableLocalPaths()) {
            File folder = pathMgr.getLocalPath(dir);
            if (!folder.exists() || !folder.isDirectory()) {
                continue;
            }

            checkDirectory(folder, filesToCheck);
        }

        Collections.sort(filesToCheck, UIUtils.LEXICAL_SORTER);

        RenamingAddon renamingAddon = RenamingAddon.getInstance();
        switchToDeterminate(0, filesToCheck.size());
        for (int i = 0; i < filesToCheck.size(); i++) {
            File file = filesToCheck.get(i);
            progress(file.getName(), i);

            renamingAddon.checkFile(file, false);
        }

        switchToIndeterminate();
        progress("Checking for Deleted Files");
        for (SharedDirectory dir : pathMgr.getAvaliableLocalPaths()) {
            File folder = pathMgr.getLocalPath(dir);
            if (!folder.exists() || !folder.isDirectory()) {
                continue;
            }

            Collection<FileRecord> files = SharedDirectoryDBA.getFileRecords(dir);
            if (files != null) {
                for (FileRecord record : files) {
                    File file = new File(folder.getPath() + record.getSubPath().replace('/', File.separatorChar));
                    if (!file.exists()) {
                        SharedDirectoryDBA.deleteFileRecord(record);
                    }
                }
            }
        }

        return null;
    }

    private static void checkDirectory(File dir, List<File> filesToCheck) {
        for (File file : dir.listFiles()) {
            if (file.isHidden()) {
                continue;
            }

            if (file.isDirectory()) {
                checkDirectory(file, filesToCheck);
            } else {
                String extension = ExtensionUtils.getExtension(file);
                if (ExtensionUtils.isVideoExtension(extension)) {
                    filesToCheck.add(file);
                }
            }
        }
    }

    @Override
    protected void success(Object result) {

    }

}
