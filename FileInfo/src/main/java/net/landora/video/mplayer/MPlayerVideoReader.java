/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
