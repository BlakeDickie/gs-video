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
package net.landora.video.info.file;

import net.landora.video.VideoManagerApp;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author bdickie
 */
public class FileManager {

    private Logger log = LoggerFactory.getLogger( getClass() );

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static FileManager instance = new FileManager();
    }

    public static FileManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private FileManager() {
        infoManager = FileInfoManager.getInstance();
    }

    private FileInfoManager infoManager;

    public boolean deleteFile( File file ) {
        boolean result = file.delete();
        if ( result ) {
            infoManager.removeFileInfo( file );
        }
        return result;
    }

    public boolean moveFile( File srcFile, File destFile ) {
        return moveFile( srcFile, destFile, true );
    }

    public boolean moveFile( File srcFile, File destFile, boolean deleteSourceIfEmpty ) {
        FileInfo info = infoManager.getFileInfo( srcFile );
        info = info.clone();

        try {
            FileUtils.moveFile( srcFile, destFile );
        } catch ( Exception e ) {
            log.warn( String.format( "Error moving file \"%s\" to \"%s\".", srcFile.getPath(), destFile.getPath() ), e );
            return false;
        }
        infoManager.removeFileInfo( srcFile );
        info.setFilename( destFile.getName() );
        infoManager.setFileInfo( destFile, info );

        if ( deleteSourceIfEmpty ) {
            File srcDir = srcFile.getParentFile();
            File[] children = srcDir.listFiles();
            boolean empty = true;
            if ( children == null ) {
                for ( File file : children ) {
                    if ( !file.getName().equals( "." ) && !file.getName().equals( ".." ) ) {
                        empty = false;
                        break;
                    }
                }
            }
            if ( empty ) {
                srcDir.delete();
            }
        }

        VideoManagerApp.getInstance().getEventBus().fireEvent( new FileMovedEvent( this, srcFile, destFile ) );

        return true;
    }

    private List<CheckFileExtension> checkFileExtensions = new ArrayList<CheckFileExtension>();

    public void addCheckFileExtension( CheckFileExtension e ) {
        checkFileExtensions.add( e );
    }

    public void removeCheckFileExtension( CheckFileExtension e ) {
        checkFileExtensions.remove( e );
    }

    public Collection<CheckFileExtension> getCheckFileExtensions() {
        return checkFileExtensions;
    }

}
