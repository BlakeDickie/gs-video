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

import jonelo.jacksum.algorithm.Edonkey;
import net.landora.video.info.ExtensionUtils;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.info.MovieMetadata;
import net.landora.video.info.SeriesMetadata;
import net.landora.video.info.VideoInfoFileUtils;
import net.landora.video.info.VideoMetadata;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.slf4j.LoggerFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CheckedOutputStream;

/**
 *
 * @author bdickie
 */
public class FileHasher {

    public static String getED2KHash( File file ) {
        InputStream is = null;
        try {
            is = new BufferedInputStream( new FileInputStream( file ) );
            Edonkey e2dkChecksum = new Edonkey();

            IOUtils.copy( is, new CheckedOutputStream( new NullOutputStream(), e2dkChecksum ) );

            return e2dkChecksum.getHexValue();
        } catch ( Exception e ) {
            LoggerFactory.getLogger( FileHasher.class ).error( "Error hashing file.", e );
            return null;
        } finally {
            IOUtils.closeQuietly( is );
        }
    }

    public static void checkDirectory( File dir ) throws IOException {
        for ( File file : dir.listFiles() ) {
            if ( file.isHidden() ) {
                continue;
            }

            if ( file.isDirectory() ) {
                checkDirectory( file );
            } else {

                String extension = ExtensionUtils.getExtension( file );
                if ( !ExtensionUtils.isVideoExtension( extension ) ) {
                    continue;
                }

                FileInfo info = FileInfoManager.getInstance().getFileInfo( file );
                VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata( info );

                if ( md == null ) {
                    continue;
                }

                file = file.getCanonicalFile();

                String filename = getOutputFilename( md ) + "." + extension;

                File outputFile = new File( getOutputFolder( md ), filename );
                outputFile.getParentFile().mkdirs();
                outputFile = outputFile.getCanonicalFile();

                if ( !outputFile.equals( file ) ) {
                    if ( outputFile.exists() ) {
                    } else {
                        FileUtils.moveFile( file, outputFile );
                    }

                }

            }
        }
    }

    private static File getOutputFolder( VideoMetadata md ) {
        File parentDir = new File( md.isAdult() ? "/var/storage/blakes/Hentai" : "/var/storage/Videos/Anime" );
        if ( md.isMovie() ) {
            MovieMetadata movie = (MovieMetadata) md;
            return new File( parentDir, replaceIllegalCharacters( movie.getMovieName() ) );
        } else {
            SeriesMetadata series = (SeriesMetadata) md;
            return new File( parentDir, replaceIllegalCharacters( series.getSeriesName() ) );
        }
    }

    private static String replaceIllegalCharacters( String str ) {
        return VideoInfoFileUtils.filterInvalidFilenameCharacters( str );
    }

    private static String getOutputFilename( VideoMetadata md ) {

        if ( md.isMovie() ) {
            MovieMetadata movie = (MovieMetadata) md;
            return replaceIllegalCharacters( movie.getMovieName() );
        } else {
            SeriesMetadata series = (SeriesMetadata) md;
            return replaceIllegalCharacters( String.format( "%s - %s - %s", series.getSeriesName(), series.getEpisodeNumber(), series.getEpisodeName() ) );
        }
    }

    public static void main( String[] args ) throws Exception {
//        AniDBHTTPManager.getInstance().updateCategoryNames();
//        AniDBHTTPManager.getInstance().updateAnimeNames();

//        File file = new File("/var/storage/Videos/Anime");
        File file = new File( "/var/storage/Downloads" );
        checkDirectory( file );
//        for(File f : file.listFiles()) {
//            if (!f.isHidden() && f.isFile()) {
//                FileInfo info = FileInfoManager.getInstance().getFileInfo(f);
//                
//                System.out.println(info);
//                VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);
//                System.out.println(md);
//                System.out.println("-----------------");
//            }
//        }

//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine engine = factory.getEngineByName("python");
//        engine.put("testing", 125);
//        Object tmp = engine.eval("123123");
//        System.out.println(tmp);
    }

}
