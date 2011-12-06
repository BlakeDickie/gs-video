/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.thetvdbinfo.data;

import java.util.ArrayList;
import java.util.List;
import net.landora.videoinfo.data.AbstractDataManager;

/**
 *
 * @author bdickie
 */
public class TVDBDataManager extends AbstractDataManager {

    @Override
    protected List<? extends Class> getMapperClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(TVDBMapper.class);
        return result;
    }

    @Override
    protected List<? extends Class> getAliasTypeClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(TVEpisode.class);
        result.add(TVShow.class);
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static TVDBDataManager instance = new TVDBDataManager();
    }

    public static TVDBDataManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private TVDBDataManager() {
        
    }
}
