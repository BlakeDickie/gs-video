/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo;

import java.io.IOException;
import java.util.logging.LogManager;
import net.landora.animeinfo.anidb.AniDBUDPManager;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.animeinfo.listrefresher.CategoryReloadPeriodicTask;
import net.landora.animeinfo.listrefresher.NameReloadPeriodicTask;
import net.landora.animeinfo.mylistreader.MyListExportPeriodicTask;
import net.landora.animeinfo.notifications.NotificationsPeriodic;
import net.landora.gsuiutils.PeriodicTaskManager;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        try {
            System.setProperty("net.landora.animeinfo.anidb.level", "100");
            LogManager.getLogManager().readConfiguration();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SecurityException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        PeriodicTaskManager.getInstance().addPeriodicTask(new NameReloadPeriodicTask());
        PeriodicTaskManager.getInstance().addPeriodicTask(new CategoryReloadPeriodicTask());
        PeriodicTaskManager.getInstance().addPeriodicTask(new NotificationsPeriodic());
        PeriodicTaskManager.getInstance().addPeriodicTask(new MyListExportPeriodicTask());
    }

    @Override
    public void close() {
        AniDBUDPManager.getInstance().shutdown();
        
    }
}
