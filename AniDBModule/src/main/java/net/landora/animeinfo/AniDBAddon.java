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
package net.landora.animeinfo;

import net.landora.animeinfo.actions.AcknowlegeNotification;
import net.landora.animeinfo.actions.ViewAnimeGroupPage;
import net.landora.animeinfo.actions.ViewAnimePage;
import net.landora.animeinfo.anidb.AniDBUDPManager;
import net.landora.animeinfo.listrefresher.CategoryReloadPeriodicTask;
import net.landora.animeinfo.listrefresher.NameReloadPeriodicTask;
import net.landora.animeinfo.metadata.AnimeMetadataProvider;
import net.landora.animeinfo.mylistreader.MyListExportPeriodicTask;
import net.landora.animeinfo.notifications.NotificationViewer;
import net.landora.animeinfo.notifications.NotificationsPeriodic;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.data.DataAddons;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.manager.ManagerAddon;
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
        super(ID, "AniDB Information", PreferencesAddon.ID, DataAddons.ID, UIAddon.ID, ManagerAddon.ID);
    }

    @Override
    public void load() {
        MetadataProvidersManager.getInstance().registerProvider(new AnimeMetadataProvider());
    }

    @Override
    public void start() {
        AniDBUDPManager.getInstance();

        UIAddon.getInstance().addAction(new AcknowlegeNotification(), new ViewAnimeGroupPage(), new ViewAnimePage());
        notificationViewer = new NotificationViewer();
        ManagerAddon.getInstance().addContentPanel(notificationViewer);
    }

    private NotificationViewer notificationViewer;

    @Override
    public void ready() {

        PeriodicTaskManager.getInstance().addPeriodicTask(new NameReloadPeriodicTask());
        PeriodicTaskManager.getInstance().addPeriodicTask(new CategoryReloadPeriodicTask());
        PeriodicTaskManager.getInstance().addPeriodicTask(new NotificationsPeriodic());
        PeriodicTaskManager.getInstance().addPeriodicTask(new MyListExportPeriodicTask());

    }

    @Override
    public void stop() {
        super.stop();

        AniDBUDPManager.getInstance().shutdown();
    }

}
