/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.file_info;

import net.landora.video.dvd.LsDVDReader;
import net.landora.video.mediainfo.MediaInfoReader;
import net.landora.video.mkv.MKVReader;
import net.landora.video.mplayer.MPlayerVideoReader;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.module.ModulesManager;
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
        super(ID, "Core File Info Parsers", ProgramsAddon.ID, PropertiesAddon.ID);
    }

    @Override
    public void start() {
        ModulesManager.getInstance().addModule(DVDReader.class, new LsDVDReader());
        ModulesManager.getInstance().addModule(VideoFileReader.class, new MediaInfoReader());
        ModulesManager.getInstance().addModule(VideoFileReader.class, new MKVReader());
        ModulesManager.getInstance().addModule(VideoFileReader.class, new MPlayerVideoReader());
    }

    
    
    
}
