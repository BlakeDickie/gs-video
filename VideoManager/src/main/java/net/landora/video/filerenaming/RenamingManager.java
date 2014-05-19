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
import net.landora.video.info.ExtensionUtils;
import net.landora.video.info.VideoMetadata;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class RenamingManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.instance ,
     * not before.
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
        try {
            RenamingScript script = RenameScriptManager.getInstance().getRenamingScript();

            if (info == null) {
                return file;
            }

            String outputFolder = script.findFolderName(info);
            String outputFile = script.findFilename(info);

            if (outputFolder != null) {
                outputFolder = outputFolder.replace('/', File.separatorChar).replace('\\', File.separatorChar);
            }

            File outputFolderFile = (outputFolder == null ? file.getParentFile() : new File(outputFolder));
            if (outputFile == null) {
                outputFile = file.getName();
            } else {
                String extension = ExtensionUtils.getExtension(file);
                outputFile = outputFile + "." + extension;
            }

            return new File(outputFolderFile, outputFile);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).warn("Error finding rename patterns.", e);
            return null;
        }
    }
}
