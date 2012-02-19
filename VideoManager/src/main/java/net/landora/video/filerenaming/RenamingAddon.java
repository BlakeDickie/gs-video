/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.tasks.PeriodicTaskManager;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class RenamingAddon extends AbstractAddon {
    public static final String ID = RenamingAddon.class.getName();

    public RenamingAddon() {
        super(ID, "File Renaming", UIAddon.ID, PreferencesAddon.ID);
    }

    @Override
    public void start() {
        UIAddon.getInstance().addConfigurationPanel(FileRenamingConfigPanel.class);
    }

    @Override
    public void ready() {
        PeriodicTaskManager.getInstance().addPeriodicTask(new CheckFilesPeriodicTask());
    }
    
    
}
