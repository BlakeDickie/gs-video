/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.data;

import java.util.ArrayList;
import java.util.List;
import net.landora.videoinfo.data.AbstractDataManager;
import net.landora.videoinfo.data.AbstractDataManager;

/**
 *
 * @author bdickie
 */
public class AnimeDataManager extends AbstractDataManager {

    @Override
    protected List<? extends Class> getMapperClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(AnimeMapper.class);
        result.add(AnimeNameLookupMapper.class);
        return result;
    }

    @Override
    protected List<? extends Class> getAliasTypeClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(AnimeStub.class);
        result.add(Anime.class);
        result.add(AnimeCategory.class);
        result.add(AnimeName.class);
        result.add(AnimeNameLookup.class);
        result.add(AnimeNameLookupSummary.class);
        result.add(AnimeCategoryWeight.class);
        result.add(AnimeRelation.class);
        result.add(AnimeEpisode.class);
        result.add(AnimeFile.class);
        result.add(AnimeGroup.class);
        result.add(AnimeListItem.class);
        result.add(AnimeNotification.class);
        result.add(AnimeMessage.class);
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static AnimeDataManager instance = new AnimeDataManager();
    }

    public static AnimeDataManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private AnimeDataManager() {
        
    }
}
