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

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author bdickie
 */
public class PreferencesDBA {

    private static Logger log = LoggerFactory.getLogger( PreferencesDBA.class );

    public static Set<String> getExtensions() {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper( PreferencesMapper.class );

            TreeSet<String> result = new TreeSet<String>( String.CASE_INSENSITIVE_ORDER );
            result.addAll( mapper.getVideoExtensions() );
            return result;

        } catch ( Exception e ) {
            log.error( "Error getting extensions.", e );
            return Collections.EMPTY_SET;
        } finally {
            if ( session != null ) {
                session.close();
            }
        }
    }

    public static void setExtensions( Collection<String> extensions ) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper( PreferencesMapper.class );

            mapper.deleteVideoExtensions();
            for ( String extension : extensions ) {
                mapper.insertVideoExtension( extension );
            }

            session.commit();
        } catch ( Exception e ) {
            log.error( "Error saving extensions.", e );
            session.rollback();
        } finally {
            if ( session != null ) {
                session.close();
            }
        }
    }

    public static Map<String, String> getPreferenceValues( String context ) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper( PreferencesMapper.class );

            Map<String, String> result = new HashMap<String, String>();
            List<HashMap<String, String>> preferences = mapper.getPreferences( context );
            for ( HashMap<String, String> row : preferences ) {
                result.put( row.get( "prefname" ), row.get( "prefvalue" ) );
            }
            return result;

        } catch ( Exception e ) {
            log.error( "Error getting preferences.", e );
            return Collections.EMPTY_MAP;
        } finally {
            if ( session != null ) {
                session.close();
            }
        }
    }

    public static void setPreferenceValue( String content, String prefName, String prefValue ) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper( PreferencesMapper.class );

            if ( mapper.updatePreference( content, prefName, prefValue ) == 0 ) {
                mapper.insertPreference( content, prefName, prefValue );
            }

            session.commit();
        } catch ( Exception e ) {
            log.error( "Error saving preferences.", e );
            session.rollback();
        } finally {
            if ( session != null ) {
                session.close();
            }
        }
    }

}
