/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.filestate.data;

import java.util.ArrayList;
import java.util.List;
import net.landora.videoinfo.data.AbstractDataManager;

/**
 *
 * @author bdickie
 */
public class FileStateDataManager extends AbstractDataManager {

    @Override
    protected List<? extends Class> getMapperClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(SharedDirectoryMapper.class);
        return result;
    }

    @Override
    protected List<? extends Class> getAliasTypeClasses() {
        List<Class> result = new ArrayList<Class>();
        result.add(SharedDirectory.class);
        result.add(FileRecord.class);
        result.add(FileConflict.class);
        result.add(FileConfictItem.class);
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static FileStateDataManager instance = new FileStateDataManager();
    }

    public static FileStateDataManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private FileStateDataManager() {
        
    }
}
