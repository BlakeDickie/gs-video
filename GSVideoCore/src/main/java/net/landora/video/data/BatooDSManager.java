/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.data;

import org.batoo.jpa.jdbc.AbstractDataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author bdickie
 */
public class BatooDSManager extends AbstractDataSource {

    private static final Map<String, DataSource> datasources = Collections.synchronizedMap( new HashMap<String, DataSource>() );

    private DataSource ds;

    public BatooDSManager() {
    }

    public static void registryDataSource( String hintName, DataSource ds ) {
        datasources.put( hintName, ds );
    }

    @Override
    public void close() {
        ds = null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void open( String persistanceUnitName, String hintName ) {
        ds = datasources.get( hintName );
    }

    @Override
    public void releaseConnection( Connection conn ) {
        try {
            conn.close();
        } catch ( SQLException ex ) {
            Logger.getLogger( BatooDSManager.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    /* Regular Data Source */
    @Override
    public Connection getConnection( String username, String password ) throws SQLException {
        return ds.getConnection( username, password );
    }

    @Override
    public <T> T unwrap( Class<T> iface ) throws SQLException {
        return ds.unwrap( iface );
    }

    @Override
    public boolean isWrapperFor( Class<?> iface ) throws SQLException {
        return ds.isWrapperFor( iface );
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return ds.getLogWriter();
    }

    @Override
    public void setLogWriter( PrintWriter out ) throws SQLException {
        ds.setLogWriter( out );
    }

    @Override
    public void setLoginTimeout( int seconds ) throws SQLException {
        ds.setLoginTimeout( seconds );
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return ds.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ds.getParentLogger();
    }

}
