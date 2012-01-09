/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.xbmc;

import net.landora.xbmc.nfo.NFOManager;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.filerenaming.RenamingAddon;
import net.landora.video.info.file.FileInfoManager;
import net.landora.video.info.file.FileManager;
import net.landora.video.preferences.PreferencesAddon;

/**
 *
 * @author bdickie
 */
public class XBMCAddon extends AbstractAddon {
    public static final String ID = XBMCAddon.class.getName();

    public XBMCAddon() {
        super(ID, "XBMC Support", PreferencesAddon.ID, RenamingAddon.ID);
    }
    
    private NFOManager nfoManager;

    @Override
    public void start() {
        nfoManager = new NFOManager();
        FileManager.getInstance().addCheckFileExtension(nfoManager);
        FileInfoManager.getInstance().addFileInfoChangedListener(nfoManager);
    }
    
    
}
