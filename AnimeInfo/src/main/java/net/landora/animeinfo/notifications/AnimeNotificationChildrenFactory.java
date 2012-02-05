/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.notifications;

import java.util.List;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeStub;
import net.landora.gsuiutils.GSAbstractNode;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author bdickie
 */
public class AnimeNotificationChildrenFactory extends ChildFactory<Object> {

    private Object obj;

    public AnimeNotificationChildrenFactory(Object obj) {
        this.obj = obj;
    }
    
    @Override
    protected boolean createKeys(List<Object> list) {
        if (obj == null) {
            List<AnimeStub> notifications = AnimeDBA.getAnimeWithWaitingNotification();
            list.addAll(notifications);
        } else if (obj instanceof AnimeStub) {
            AnimeStub anime = (AnimeStub)obj;
            list.addAll(AnimeDBA.getEpisodesForNotifications(anime.getAnimeId()));
        } else if (obj instanceof AnimeEpisode) {
            AnimeEpisode anime = (AnimeEpisode)obj;
            list.addAll(AnimeDBA.getNotificationsForEpisode(anime.getEpisodeId()));
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Object key) {
        AbstractNode node = new GSAbstractNode(Children.create(new AnimeNotificationChildrenFactory(key), true), key);
        node.setName(key.toString());
        
        return node;
    }
    
    
}
