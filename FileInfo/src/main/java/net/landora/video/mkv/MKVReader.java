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
package net.landora.video.mkv;

import java.io.File;
import net.landora.video.module.AbstractModule;
import net.landora.video.programs.CommonPrograms;
import net.landora.video.programs.ProgramsAddon;
import net.landora.video.properties.Video;
import net.landora.video.properties.VideoFileReader;
import net.landora.video.utils.MutableObject;

/**
 *
 * @author bdickie
 */
public class MKVReader extends AbstractModule implements VideoFileReader {

    @Override
    public Video parseFile(File file) {
        return new MKVParser().handleFile(file);
    }

    @Override
    public boolean supportsFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mkv") || name.endsWith(".mka");
    }

    @Override
    protected void loadModuleState(MutableObject<Boolean> usable, MutableObject<Boolean> configured, MutableObject<String> errorMessage) {
        usable.setValue(true);

        if (!ProgramsAddon.getInstance().isAvaliable(CommonPrograms.MKVINFO)) {
            errorMessage.setValue("mkvinfo not installed or configured.");
            return;
        }

        configured.setValue(true);
    }

    @Override
    public String getModuleDescription() {
        return "Reads information about Matroska files through mkvinfo from mkvtoolnix (http://www.bunkus.org/videotools/mkvtoolnix/).";
    }

    @Override
    public String getModuleName() {
        return "Matroska File Parser";
    }

    @Override
    public int getModulePriority() {
        return MODULE_PRIORITY_PREFERRED;
    }

}
