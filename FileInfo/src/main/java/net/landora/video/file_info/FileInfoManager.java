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

package net.landora.video.file_info;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.dvd.LsDVDReader;
import net.landora.video.mediainfo.MediaInfoReader;
import net.landora.video.mkv.MKVReader;
import net.landora.video.module.ModulesAddon;
import net.landora.video.module.ModulesManager;
import net.landora.video.mplayer.MPlayerVideoReader;
import net.landora.video.programs.ProgramsAddon;
import net.landora.video.properties.DVDReader;
import net.landora.video.properties.PropertiesAddon;
import net.landora.video.properties.VideoFileReader;

/**
 *
 * @author bdickie
 */
public class FileInfoManager extends AbstractAddon {
    public static final String ID = "gsilva.file_info.FileInfoManager";
    
    public FileInfoManager() {
        super(ID, "Core File Info Parsers", ProgramsAddon.ID, PropertiesAddon.ID, ModulesAddon.ID);
    }

    @Override
    public void start() {
        ModulesManager.getInstance().addModule(DVDReader.class, new LsDVDReader());
        ModulesManager.getInstance().addModule(VideoFileReader.class, new MediaInfoReader());
        ModulesManager.getInstance().addModule(VideoFileReader.class, new MKVReader());
        ModulesManager.getInstance().addModule(VideoFileReader.class, new MPlayerVideoReader());
    }

    
    
    
}
