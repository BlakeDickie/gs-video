/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.metadata;

import java.util.Calendar;
import net.landora.videoinfo.ViewListManager;
import net.landora.videoinfo.ViewListState.DiskState;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.animeinfo.data.AnimeListItem;
import net.landora.animeinfo.data.AnimeListState;
import net.landora.animeinfo.data.AnimeManager;

/**
 *
 * @author bdickie
 */
public class AnimeListManager implements ViewListManager<AnimeMetadata,AnimeViewListState> {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static AnimeListManager instance = new AnimeListManager();
    }

    public static AnimeListManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>


    private AnimeListManager() {
        
    }
    
    @Override
    public AnimeViewListState getViewListState(AnimeMetadata videoMetadata) {
        AnimeFile file = videoMetadata.getFile();
        if (file == null) {
            return null;
        } else {
            AnimeListItem item = AnimeManager.getInstance().findListItemByFileId(file.getFileId(), true);
            if (item == null)
                return null;
            else
                return new AnimeViewListState(item);
        }
    }

    @Override
    public AnimeViewListState createViewListState(AnimeMetadata videoMetadata, Calendar viewDate, DiskState diskState) {
        AnimeListState listState = AnimeListState.lookupDiskState(diskState);
        
        AnimeListItem listItem;
        
        AnimeFile file = videoMetadata.getFile();
        if (file == null) {
            AnimeEpisode episode = videoMetadata.getEpisode();
            listItem = AnimeManager.getInstance().getOrCreateGenericListItem(episode, viewDate, listState);
        } else {
            listItem = AnimeManager.getInstance().getOrCreateListItemByFileId(file.getFileId(), viewDate, listState);
        }
        
        if (listItem == null)
            return null;
        else
            return new AnimeViewListState(listItem);
    }
}
