/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.filestate;

import net.landora.gsuiutils.PeriodicTaskManager;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        PeriodicTaskManager.getInstance().addPeriodicTask(new CheckFilesPeriodicTask());
    }
}
