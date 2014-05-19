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
import net.landora.video.filestate.data.LocalPathManager;
import net.landora.video.filestate.data.SharedDirectory;
import net.landora.video.info.VideoInfoFileUtils;

/**
 *
 * @author bdickie
 */
public class RenameScriptFunctions {

    public static String filterInvalidFilenameCharacters(String filename) {
        return VideoInfoFileUtils.filterInvalidFilenameCharacters(filename);
    }

    public static String getSharedDirectoryPath(String name) {
        for (SharedDirectory dir : LocalPathManager.getInstance().getAvaliableLocalPaths()) {
            if (dir.getName().equalsIgnoreCase(name)) {
                return LocalPathManager.getInstance().getLocalPath(dir).getPath() + File.separator;
            }
        }
        throw new IllegalStateException("Shared directory is not avaliable: " + name);
    }
}
