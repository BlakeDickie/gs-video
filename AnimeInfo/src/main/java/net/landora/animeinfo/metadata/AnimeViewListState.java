/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.metadata;

import java.util.Calendar;
import java.util.Collection;
import net.landora.videoinfo.ViewListState;
import net.landora.animeinfo.data.AnimeListItem;
import net.landora.animeinfo.data.AnimeListState;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.gsuiutils.ContextProducer;
import net.landora.gsuiutils.UIUtils;
import org.openide.util.Utilities;

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
        if (!Utilities.compareObjects(lastViewed, getLastViewed())) {
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
        if (!Utilities.compareObjects(state, getDiskState())) {
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
