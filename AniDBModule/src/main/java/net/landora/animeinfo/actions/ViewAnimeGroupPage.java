/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import net.landora.animeinfo.data.AnimeGroup;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.video.ui.UIAction;
import net.landora.video.utils.UIUtils;
import org.slf4j.LoggerFactory;


public final class ViewAnimeGroupPage extends UIAction<AnimeGroup> {

    public ViewAnimeGroupPage() {
        super(AnimeGroup.class, "View Group Site");
    }

    @Override
    public void actionPerformed(ActionEvent evt, List<AnimeGroup> objects) {
        AnimeGroup context = objects.get(0);
        
        try {
            AnimeManager.getInstance().ensureGroupIsFullyLoaded(context);
            if (context.getUrl() == null || context.getUrl().equals(""))
                UIUtils.openBrowser("http://anidb.net/perl-bin/animedb.pl?show=group&gid=" + context.getGroupId());
            else
                UIUtils.openBrowser(context.getUrl());
        } catch (Exception ex) {
            LoggerFactory.getLogger(getClass()).error("Error opening group page.", ex);
        }
    }
}
