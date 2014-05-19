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
package net.landora.video.preferences;

import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AddonManager;
import net.landora.video.data.LocalStorage;
import net.landora.video.utils.ComparisionUtils;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bdickie
 */
public class FileSyncProperties {

    private String filename;

    public FileSyncProperties( String filename ) {
        this.filename = filename;

        values = new HashMap<String, String>();
        changedValues = new HashSet<String>();

        sync();

        AddonManager.getInstance().getAddonInstance( PreferencesAddon.class ).addShutdownCall( new QuitListener() );

        VideoManagerApp.getInstance().getScheduledExecutor().scheduleAtFixedRate( new Runnable() {
            public void run() {
                sync();
            }
        }, 1, 1, TimeUnit.MINUTES );
    }

    private Map<String, String> values;
    private Set<String> changedValues;

    public final synchronized void sync() {
        try {
            Properties props = new Properties();
            if ( LocalStorage.getInstance().doesSettingFileExist( filename ) ) {
                try {
                    InputStream is = LocalStorage.getInstance().openInputFile( filename );
                    props.load( is );
                    is.close();
                } catch ( Exception ignore ) {
                }
            }

            for ( String name : changedValues ) {
                String value = values.get( name );
                if ( value == null ) {
                    props.remove( name );
                } else {
                    props.setProperty( name, value );
                }
            }

            OutputStream os = LocalStorage.getInstance().openOutputFile( filename );
            props.store( os, "" );
            os.close();

            changedValues.clear();
            values.clear();

            for ( String name : props.stringPropertyNames() ) {
                values.put( name, props.getProperty( name ) );
            }

        } catch ( Exception e ) {
            LoggerFactory.getLogger( getClass() ).error( "Error syncing properties.", e );
        }
    }

    public synchronized String getValue( String name ) {
        return values.get( name );
    }

    public synchronized void setValue( String name, String value ) {
        String oldValue = values.put( name, value );
        if ( !ComparisionUtils.equals( oldValue, value ) ) {
            changedValues.add( name );
        }
    }

    private class QuitListener implements Runnable {

        @Override
        public void run() {
            sync();
        }

    }
}
