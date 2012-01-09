/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import net.landora.animeinfo.data.AnimeStub;
import net.landora.video.ui.UIAction;
import net.landora.video.utils.UIUtils;
import org.slf4j.LoggerFactory;

public final class ViewAnimePage extends UIAction<AnimeStub> {

    public ViewAnimePage() {
        super(AnimeStub.class, "View Anime Page");
    }

    @Override
    public void actionPerformed(ActionEvent evt, List<AnimeStub> objects) {
        AnimeStub context = objects.get(0);
        try {
            UIUtils.openBrowser("http://anidb.net/perl-bin/animedb.pl?show=anime&aid=" + context.getAnimeId());
        } catch (Exception ex) {
            LoggerFactory.getLogger(getClass()).error("Error opening anime page.", ex);
        }
    }
}
