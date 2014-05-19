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
package net.landora.video.filestate;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bdickie
 */
public class DirectoryUUIDChecker {

    private static final Logger log = LoggerFactory.getLogger( DirectoryUUIDChecker.class );

    public static final String UUID_FILE = ".gs_video_uuid";

    public static boolean validateUUID( File directory, String uuid ) {
        try {
            File uuidFile = new File( directory, UUID_FILE );
            if ( !uuidFile.exists() ) {
                return false;
            }
            List<String> lines = FileUtils.readLines( uuidFile );
            return lines.contains( uuid );
        } catch ( Exception e ) {
            log.error( "Error checking for directory uuid: " + directory, e );
            return false;
        }
    }

    public static void setUUID( File directory, String uuid ) {
        try {
            File uuidFile = new File( directory, UUID_FILE );
            Set<String> uuids = new LinkedHashSet<String>();

            if ( uuidFile.exists() ) {
                uuids.addAll( FileUtils.readLines( uuidFile ) );
            }
            uuids.add( uuid );
            FileUtils.writeLines( uuidFile, uuids );
        } catch ( Exception e ) {
            log.error( "Error adding directory uuid: " + directory, e );

        }
    }
}
