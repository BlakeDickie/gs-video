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
package net.landora.video.filestate.data;

import net.landora.video.filestate.DirectoryUUIDChecker;
import net.landora.video.filestate.FileStatePreference;
import net.landora.video.utils.Touple;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bdickie
 */
public class LocalPathManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static LocalPathManager instance = new LocalPathManager();
    }

    public static LocalPathManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private LocalPathManager() {
        paths = new HashMap<String, File>();

        List<String> pathList = FileStatePreference.LocalPaths.getStringList();
        for ( int i = 0; i < pathList.size(); i += 2 ) {
            String uuid = pathList.get( i );
            String path = pathList.get( i + 1 );
            File file = new File( path );
            if ( file.isDirectory() && DirectoryUUIDChecker.validateUUID( file, uuid ) ) {
                paths.put( uuid, file );
            }
        }
    }

    private Map<String, File> paths;

    private void saveCurrentState() {
        List<String> pathList = new ArrayList<String>( paths.size() * 2 );
        for ( Map.Entry<String, File> entry : paths.entrySet() ) {
            pathList.add( entry.getKey() );
            pathList.add( entry.getValue().getAbsolutePath() );
        }
        FileStatePreference.LocalPaths.setStringList( pathList );
    }

    public File getLocalPath( SharedDirectory directory ) {
        File path = paths.get( directory.getUuid() );
        if ( path == null ) {
            File defaultPath = new File( directory.getDefaultPath() );
            if ( defaultPath.isAbsolute() && defaultPath.isDirectory() ) {
                if ( DirectoryUUIDChecker.validateUUID( defaultPath, directory.getUuid() ) ) {
                    setLocalPath( directory, defaultPath );
                }
            }
        }
        return path;
    }

    public File getLocalPath( FileRecord record ) {
        File parent = getLocalPath( record.getDirectory() );
        if ( parent == null ) {
            return null;
        }

        File temp = new File( parent, record.getSubPath().replace( '/', File.separatorChar ) );
        return temp;
    }

    public void setLocalPath( SharedDirectory directory, File path ) {
        if ( path == null ) {
            paths.remove( directory.getUuid() );
        } else {
            paths.put( directory.getUuid(), path );
        }

        saveCurrentState();
    }

    public List<SharedDirectory> getAvaliableLocalPaths() {
        List<SharedDirectory> result = new ArrayList<SharedDirectory>();
        for ( SharedDirectory dir : SharedDirectoryDBA.getSharedDirectories() ) {
            if ( getLocalPath( dir ) != null ) {
                result.add( dir );
            }
        }
        return result;
    }

    public Touple<SharedDirectory, String> findSubPath( File file ) {
        String path = file.getAbsolutePath();
        for ( SharedDirectory directory : getAvaliableLocalPaths() ) {
            String dirPath = getLocalPath( directory ).getAbsolutePath();
            if ( path.startsWith( dirPath ) ) {
                String subPath = path.substring( dirPath.length() ).replace( File.separatorChar, '/' );
                return new Touple<SharedDirectory, String>( directory, subPath );
            }
        }

        return null;
    }
}
