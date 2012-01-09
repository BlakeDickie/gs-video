/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo;

import net.landora.animeinfo.actions.AcknowlegeNotification;
import net.landora.animeinfo.actions.ViewAnimeGroupPage;
import net.landora.animeinfo.actions.ViewAnimePage;
import net.landora.animeinfo.anidb.AniDBUDPManager;
import net.landora.animeinfo.listrefresher.CategoryReloadPeriodicTask;
import net.landora.animeinfo.listrefresher.NameReloadPeriodicTask;
import net.landora.animeinfo.mylistreader.MyListExportPeriodicTask;
import net.landora.animeinfo.notifications.NotificationViewer;
import net.landora.animeinfo.notifications.NotificationsPeriodic;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.data.DataAddons;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.tasks.PeriodicTaskManager;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class AniDBAddon extends AbstractAddon {
    public static final String ID = "net.landora.animeinfo";

    public AniDBAddon() {
        super(ID, "AniDB Information", PreferencesAddon.ID, DataAddons.ID, UIAddon.ID);
    }

    @Override
    public void start() {
        AniDBUDPManager.getInstance();
        
        UIAddon.getInstance().addAction(new AcknowlegeNotification(), new ViewAnimeGroupPage(), new ViewAnimePage());
        notificationViewer = new NotificationViewer();
        UIAddon.getInstance().addContentPanel(notificationViewer);
    }
    
    private NotificationViewer notificationViewer;

    @Override
    public void ready() {
        
        PeriodicTaskManager.getInstance().addPeriodicTask(new NameReloadPeriodicTask());
        PeriodicTaskManager.getInstance().addPeriodicTask(new CategoryReloadPeriodicTask());
        PeriodicTaskManager.getInstance().addPeriodicTask(new NotificationsPeriodic());
        PeriodicTaskManager.getInstance().addPeriodicTask(new MyListExportPeriodicTask());
        
        notificationViewer.loadNotifications();
    }

    
    
    @Override
    public void stop() {
        super.stop();
        
        AniDBUDPManager.getInstance().shutdown();
    }
    
    
}
