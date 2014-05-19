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
