/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
}
