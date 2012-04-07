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
            if (context.getUrl() == null || context.getUrl().length() == 0)
                UIUtils.openBrowser("http://anidb.net/perl-bin/animedb.pl?show=group&gid=" + context.getGroupId());
            else
                UIUtils.openBrowser(context.getUrl());
        } catch (Exception ex) {
            LoggerFactory.getLogger(getClass()).error("Error opening group page.", ex);
        }
    }
}
