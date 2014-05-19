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
package net.landora.animeinfo.metadata;

import java.util.Calendar;
import java.util.Collection;
import net.landora.animeinfo.data.AnimeListItem;
import net.landora.animeinfo.data.AnimeListState;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.video.info.ViewListState;
import net.landora.video.info.ViewListState.DiskState;
import net.landora.video.ui.ContextProducer;
import net.landora.video.utils.ComparisionUtils;
import net.landora.video.utils.UIUtils;

/**
 *
 * @author bdickie
 */
public class AnimeViewListState implements ViewListState, ContextProducer {

    private AnimeListItem item;

    public AnimeViewListState(AnimeListItem item) {
        this.item = item;
    }

    public AnimeListItem getItem() {
        return item;
    }

    @Override
    public Calendar getLastViewed() {
        return item.getViewDate();
    }

    @Override
    public void setLastViewed(Calendar lastViewed) {
        if (!ComparisionUtils.equals(lastViewed, getLastViewed())) {
            item.setViewDate(lastViewed);
            AnimeManager.getInstance().saveListItem(item);
        }
    }

    @Override
    public DiskState getDiskState() {
        return item.getState().getDiskState();
    }

    @Override
    public void setDiskState(DiskState state) {
        if (!ComparisionUtils.equals(state, getDiskState())) {
            item.setState(AnimeListState.lookupDiskState(state));
            AnimeManager.getInstance().saveListItem(item);
        }
    }

    @Override
    public void addContentObjects(Collection<Object> addTo) {
        addTo.add(this);
        UIUtils.addContentObject(item, addTo);
    }

    public void remove() {
        AnimeManager.getInstance().deleteListItem(item);
    }
}
