/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.videoinfo.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CheckedOutputStream;
import jonelo.jacksum.algorithm.Edonkey;
import net.landora.videoinfo.ExtensionUtils;
import net.landora.videoinfo.MetadataProvidersManager;
import net.landora.videoinfo.MovieMetadata;
import net.landora.videoinfo.SeriesMetadata;
import net.landora.videoinfo.VideoInfoFileUtils;
import net.landora.videoinfo.VideoMetadata;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class FileHasher {

    public static String getED2KHash(File file) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            Edonkey e2dkChecksum = new Edonkey();

            IOUtils.copy(is, new CheckedOutputStream(new NullOutputStream(), e2dkChecksum));

            return e2dkChecksum.getHexValue();
        } catch (Exception e) {
            LoggerFactory.getLogger(FileHasher.class).error("Error hashing file.", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    
    public static void checkDirectory(File dir) throws IOException {
        for(File file : dir.listFiles()) {
            if (file.isHidden())
                continue;
            
            if (file.isDirectory()) {
                checkDirectory(file);
            } else {
                
                String extension = ExtensionUtils.getExtension(file);
                if (!ExtensionUtils.isVideoExtension(extension))
                    continue;
                
                
                System.err.println(file);
                
                FileInfo info = FileInfoManager.getInstance().getFileInfo(file);
                VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);
                
                if (md == null)
                    continue;
                
                file = file.getCanonicalFile();
                
                
                
                String filename = getOutputFilename(md) + "." + extension;
                
                File outputFile = new File(getOutputFolder(md), filename);
                outputFile.getParentFile().mkdirs();
                outputFile = outputFile.getCanonicalFile();
                
                if (!outputFile.equals(file)) {
                    if (outputFile.exists()) {
                        System.err.println("File already exists: " + outputFile);
                    } else {
                        System.err.println("Move File: " + file + "   to   " + outputFile);
                        FileUtils.moveFile(file, outputFile);
                    }
                    
                }
                
            }
        }
    }
    
    private static File getOutputFolder(VideoMetadata md) {
        File parentDir = new File(md.isAdult() ? "/var/storage/blakes/Hentai" : "/var/storage/Videos/Anime");
        if (md.isMovie()) {
            MovieMetadata movie = (MovieMetadata)md;
            return new File(parentDir, replaceIllegalCharacters(movie.getMovieName()));
        } else {
            SeriesMetadata series = (SeriesMetadata)md;
            return new File(parentDir, replaceIllegalCharacters(series.getSeriesName()));
        }
    }
    
    private static String replaceIllegalCharacters(String str) {
        return VideoInfoFileUtils.filterInvalidFilenameCharacters(str);
    }
    
    private static String getOutputFilename(VideoMetadata md) {
        
        if (md.isMovie()) {
            MovieMetadata movie = (MovieMetadata)md;
            return replaceIllegalCharacters(movie.getMovieName());
        } else {
            SeriesMetadata series = (SeriesMetadata)md;
            return replaceIllegalCharacters(String.format("%s - %s - %s", series.getSeriesName(), series.getEpisodeNumber(), series.getEpisodeName()));
        }
    }
    
    
    public static void main(String[] args) throws Exception {
//        AniDBHTTPManager.getInstance().updateCategoryNames();
//        AniDBHTTPManager.getInstance().updateAnimeNames();
        
//        File file = new File("/var/storage/Videos/Anime");
        File file = new File("/var/storage/Downloads");
        checkDirectory(file);
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
