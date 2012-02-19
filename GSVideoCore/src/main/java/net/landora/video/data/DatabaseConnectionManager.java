/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.data;

import javax.sql.DataSource;
import net.landora.video.info.VideoInfoPreference;
import org.logicalcobwebs.proxool.ProxoolDataSource;


/**
 *
 * @author bdickie
 */
public class DatabaseConnectionManager {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static DatabaseConnectionManager instance = new DatabaseConnectionManager();
    }

    public static DatabaseConnectionManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    
    private DatabaseConnectionManager() {
        ProxoolDataSource ds = new ProxoolDataSource();
        ds.setAlias("videos");
        ds.setDriver(POSTGRESQL_DRIVER);
        ds.setDriverUrl(String.format("jdbc:postgresql://%s/%s", VideoInfoPreference.DatabaseHost.getString(), VideoInfoPreference.DatabaseName.getString()));
        ds.setUser(VideoInfoPreference.DatabaseUsername.getString());
        ds.setPassword(VideoInfoPreference.DatabasePassword.getString());
        ds.setMinimumConnectionCount(1);
        
        dataSource = ds;
        
    }
    
    private final DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }
}
