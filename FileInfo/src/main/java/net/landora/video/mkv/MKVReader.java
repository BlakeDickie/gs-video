/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
