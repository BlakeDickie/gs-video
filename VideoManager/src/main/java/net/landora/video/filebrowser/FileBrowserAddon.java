/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filebrowser;

import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.manager.ManagerAddon;

/**
 *
 * @author bdickie
 */
public class FileBrowserAddon extends AbstractAddon {
    private static final String ID = "net.landora.video.filebrowser.FileBrowserAddon";
    
    public FileBrowserAddon() {
        super(ID, "File Browser", ManagerAddon.ID);
    }

    @Override
    public void start() {
        if (VideoManagerApp.getInstance().getProfile().isManager()) {
            ManagerAddon.getInstance().addContentPanel(new FileBrowserPanel());
        }
    }
    
    
}
