/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info.data.preferences;

import java.util.ArrayList;
import java.util.List;
import net.landora.video.info.data.AbstractDataManager;

/**
 *
 * @author bdickie
 */
public class PreferencesDataManager extends AbstractDataManager {

    @Override
    protected List<? extends Class> getMapperClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(PreferencesMapper.class);
        return result;
    }

    @Override
    protected List<? extends Class> getAliasTypeClasses() {
        List<Class> result = new ArrayList<Class>();
        
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static PreferencesDataManager instance = new PreferencesDataManager();
    }

    public static PreferencesDataManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private PreferencesDataManager() {
        
    }
}
