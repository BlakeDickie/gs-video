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
package net.landora.video.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.landora.video.info.VideoInfoPreference;
import javax.sql.DataSource;

/**
 *
 * @author bdickie
 */
public class DatabaseConnectionManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.instance ,
     * not before.
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
        HikariConfig config = new HikariConfig();
        config.setDriverClassName( POSTGRESQL_DRIVER );
        config.setPoolName( "gsvideo" );
        config.setJdbcUrl( String.format( "jdbc:postgresql://%s/%s", VideoInfoPreference.DatabaseHost.getString(), VideoInfoPreference.DatabaseName.getString() ) );
        config.setUsername( VideoInfoPreference.DatabaseUsername.getString() );
        config.setPassword( VideoInfoPreference.DatabasePassword.getString() );
        config.setMinimumIdle( 1 );

        dataSource = new HikariDataSource( config );

    }

    private final DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }
}
