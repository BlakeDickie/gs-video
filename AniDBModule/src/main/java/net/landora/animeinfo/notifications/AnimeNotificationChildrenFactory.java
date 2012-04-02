/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.landora.animeinfo.notifications;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.animeinfo.data.AnimeNotification;
import net.landora.animeinfo.data.AnimeStub;
import net.landora.video.ui.tree.LazyTreeChildrenFactory;
import net.landora.video.ui.tree.LazyTreeNode;

/**
 *
 * @author bdickie
 */
public class AnimeNotificationChildrenFactory implements LazyTreeChildrenFactory {

    public AnimeNotificationChildrenFactory() {
    }

    public List<? extends MutableTreeNode> getChildren(Object obj) {
        
        List<Object> list = new ArrayList<Object>();
        if (obj instanceof String && obj.equals("Notifications")) {
            List<AnimeStub> notifications = AnimeDBA.getAnimeWithWaitingNotification();
            list.addAll(notifications);
        } else if (obj instanceof AnimeStub) {
            AnimeStub anime = (AnimeStub)obj;
            list.addAll(AnimeDBA.getEpisodesForNotifications(anime.getAnimeId()));
        } else if (obj instanceof AnimeEpisode) {
            AnimeEpisode anime = (AnimeEpisode)obj;
            list.addAll(AnimeDBA.getNotificationsForEpisode(anime.getEpisodeId()));
        }
        
        List<DefaultMutableTreeNode> result = new ArrayList();
        for(Object o: list) {
            if (o instanceof AnimeNotification)
                result.add(new DefaultMutableTreeNode(o));
            else
                result.add(new LazyTreeNode(o, this));
        }
        return result;
    }

    
    
}
