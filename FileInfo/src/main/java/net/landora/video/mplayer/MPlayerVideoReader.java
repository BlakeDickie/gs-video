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
package net.landora.video.mplayer;

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
public class MPlayerVideoReader extends AbstractModule implements VideoFileReader {

    @Override
    public Video parseFile(File file) {
        return new MPlayerParser().handleFile(file);
    }

    @Override
    public boolean supportsFile(File file) {
        return true;
    }

    @Override
    protected void loadModuleState(MutableObject<Boolean> usable, MutableObject<Boolean> configured, MutableObject<String> errorMessage) {
        usable.setValue(true);

        if (!ProgramsAddon.getInstance().isAvaliable(CommonPrograms.MPLAYER)) {
            errorMessage.setValue("MPlayer not installed or configured.");
            return;
        }

        configured.setValue(true);
    }

    @Override
    public String getModuleName() {
        return "MPlayer Video Parser";
    }

    @Override
    public String getModuleDescription() {
        return "Reads information about media files through mplayer (http://www.mplayerhq.hu/).";
    }

    @Override
    public int getModulePriority() {
        return MODULE_PRIORITY_FALLBACK;
    }

}
