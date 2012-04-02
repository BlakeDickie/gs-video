/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package net.landora.video.info.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.event.EventListenerList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import jonelo.jacksum.algorithm.Edonkey;
import net.landora.video.utils.XMLUtilities;
import net.landora.video.info.MetadataProvidersManager;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class FileInfoManager {
    private Logger log = LoggerFactory.getLogger(getClass());

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static FileInfoManager instance = new FileInfoManager();
    }

    public static FileInfoManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private FileInfoManager() {
        cachedItems = new HashMap<File, DirectoryCacheItem>();
    }

    private static final String DIRECTORY_INFO_FILE_COMPRESSED = ".gs_video_info.xml.gz";
    private static final String DIRECTORY_INFO_FILE_UNCOMPRESSED = ".gs_video_info.xml";

    private static final boolean COMPRESS_INFO_FILE = false;
    private static final String DIRECTORY_INFO_FILE = (COMPRESS_INFO_FILE ? DIRECTORY_INFO_FILE_COMPRESSED : DIRECTORY_INFO_FILE_UNCOMPRESSED);

    private final Map<File,DirectoryCacheItem> cachedItems;


    public FileInfo getFileInfo(File file) {
        return getFileInfo(file, false);
    }
    
    public FileInfo getFileInfo(File file, boolean cachedOnly) {
        if (!file.exists())
            throw new IllegalArgumentException("Unable to find file.");

        DirectoryCacheItem cache;
        synchronized(this) {
            cache = getDirectoryCacheItem(file.getParentFile());
        }
        FileInfo info = cache.getFileInfo(file.getName());
        
        if (cachedOnly)
            return info;
        
        if (info != null && info.getLastModified() == file.lastModified() && info.getFileSize() == file.length()) {
            if (MetadataProvidersManager.getInstance().checkForMetadataUpdate(info)) {
                cache.setFileInfo(file.getName(), info);
                fireFileChanged(file, info);
            }
            return info;
        }

        synchronized(this) {
            info = createInfo(file);
            cache.setFileInfo(file.getName(), info);
            fireFileChanged(file, info);
        }

        return info;
    }
    
    void removeFileInfo(File file) {
        DirectoryCacheItem cache;
        synchronized(this) {
            cache = getDirectoryCacheItem(file.getParentFile());
            cache.removeFileInfo(file.getName());
            fireFileRemoved(file, null);
        }
    }
    
    void setFileInfo(File file, FileInfo info) {
        DirectoryCacheItem cache;
        synchronized(this) {
            cache = getDirectoryCacheItem(file.getParentFile());
            cache.setFileInfo(file.getName(), info);
            fireFileChanged(file, info);
        }
    }

    private synchronized FileInfo createInfo(File file) {
        FileInfo result = new FileInfo();
        result.setFileSize(file.length());
        result.setLastModified(file.lastModified());
        result.setFilename(file.getName());

        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            Edonkey e2dkChecksum = new Edonkey();

            IOUtils.copy(is, new CheckedOutputStream(new NullOutputStream(), e2dkChecksum));

            result.setE2dkHash(e2dkChecksum.getHexValue());
        } catch (Exception e) {
            log.error("Error hashing file.", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }

        MetadataProvidersManager.getInstance().checkForMetadata(result);

        return result;
    }

    private DirectoryCacheItem getDirectoryCacheItem(File directory) {
        synchronized(cachedItems) {
            File cacheFile = new File(directory, DIRECTORY_INFO_FILE);
            DirectoryCacheItem cache = cachedItems.get(directory);

            if (cache == null || (cacheFile.exists() && cacheFile.lastModified() > cache.getLastLoaded())) {
                
                if (cacheFile.exists()) {
                    cache = new DirectoryCacheItem(parseCacheFile(cacheFile));
                    cache.setLastLoaded(cacheFile.lastModified());
                } else {
                    cache = new DirectoryCacheItem();
                    cache.setLastLoaded(System.currentTimeMillis());
                }
                cache.setDirectory(directory);
                
                cachedItems.put(directory, cache);
            }
            
            return cache;
        }
    }

    private void updateDirectoryCache(DirectoryCacheItem cache, String changedFileName) {
        synchronized(cachedItems) {
            File directory = cache.getDirectory();

            File cacheFile = new File(directory, DIRECTORY_INFO_FILE);

            if (cacheFile.exists() && cacheFile.lastModified() > cache.getLastLoaded()) {
                DirectoryCacheItem newCache;
                if (cacheFile.exists()) {
                    newCache = new DirectoryCacheItem(parseCacheFile(cacheFile));
                    newCache.setLastLoaded(cacheFile.lastModified());
                } else {
                    newCache = new DirectoryCacheItem();
                    newCache.setLastLoaded(System.currentTimeMillis());
                }
                newCache.setDirectory(directory);

                FileInfo info = cache.getFileInfo(changedFileName);
                if (info != null) {
                    newCache.files.put(changedFileName, info);
                } else {
                    newCache.files.remove(changedFileName);
                }

                cachedItems.put(directory, newCache);
                cache = newCache;
            }

            if (cache.files.isEmpty())
                cacheFile.delete();
            else
                writeCacheFile(cacheFile, cache.files);
        }
    }

    private synchronized Map<String,FileInfo> parseCacheFile(File file) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            if (COMPRESS_INFO_FILE)
                is = new GZIPInputStream(is);

            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

            reader.nextTag();
            reader.require(XMLStreamReader.START_ELEMENT, null, "files");

            Map<String,FileInfo> files = new HashMap<String, FileInfo>();

            while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                reader.require(XMLStreamReader.START_ELEMENT, null, "file");

                FileInfo info = new FileInfo();
                String filename = reader.getAttributeValue(null, "filename");
                info.setFilename(filename);
                info.setE2dkHash(reader.getAttributeValue(null, "ed2k"));
                info.setFileSize(Long.parseLong(reader.getAttributeValue(null, "length")));
                info.setLastModified(Long.parseLong(reader.getAttributeValue(null, "lastmodified")));
                info.setMetadataSource(reader.getAttributeValue(null, "metadatasource"));
                info.setMetadataId(reader.getAttributeValue(null, "metadataid"));
                info.setVideoId(reader.getAttributeValue(null, "videoid"));

                files.put(filename, info);
                
                XMLUtilities.ignoreTag(reader);
                
            }
            reader.close();

            return files;

        } catch (Exception e) {
            log.error("Error parsing file cache.", e);
            return new HashMap<String, FileInfo>();
        } finally {
            if (is != null)
                IOUtils.closeQuietly(is);
        }
    }

    private synchronized void writeCacheFile(File file, Map<String,FileInfo> infoMap) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            if (COMPRESS_INFO_FILE)
                os = new GZIPOutputStream(os);

            XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(os);
            writer.writeStartDocument();

            writer.writeStartElement("files");
            writer.writeCharacters("\n");

            for(Map.Entry<String,FileInfo> entry: infoMap.entrySet()) {
                FileInfo info = entry.getValue();

                writer.writeStartElement("file");
                writer.writeAttribute("filename", entry.getKey());

                writer.writeAttribute("ed2k", info.getE2dkHash());
                writer.writeAttribute("length", String.valueOf(info.getFileSize()));
                writer.writeAttribute("lastmodified", String.valueOf(info.getLastModified()));


                if (info.getMetadataSource() != null)
                    writer.writeAttribute("metadatasource", info.getMetadataSource());
                if (info.getMetadataId() != null)
                    writer.writeAttribute("metadataid", info.getMetadataId());
                if (info.getVideoId() != null)
                    writer.writeAttribute("videoid", info.getVideoId());

                writer.writeEndElement();
                writer.writeCharacters("\n");
            }

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
            
        } catch (Exception e) {
            log.error("Error writing file cache.", e);
        } finally {
            if (os != null)
                IOUtils.closeQuietly(os);
        }
    }

    private class DirectoryCacheItem {
        private long lastLoaded;

        private File directory;
        private Map<String,FileInfo> files;

        public DirectoryCacheItem() {
            files = new HashMap<String, FileInfo>();
        }

        public DirectoryCacheItem(Map<String, FileInfo> files) {
            this.files = files;
        }

        public File getDirectory() {
            return directory;
        }

        public void setDirectory(File directory) {
            this.directory = directory;
        }

        
        

        public long getLastLoaded() {
            return lastLoaded;
        }

        public void setLastLoaded(long lastLoaded) {
            this.lastLoaded = lastLoaded;
        }

        public synchronized FileInfo getFileInfo(String filename) {
            return files.get(filename);
        }

        public synchronized void setFileInfo(String filename, FileInfo info) {
            files.put(filename, info);

            updateDirectoryCache(this, filename);
        }

        public synchronized void removeFileInfo(String filename) {
            files.remove(filename);

            updateDirectoryCache(this, filename);
        }
    }
    
    
    
    // ************ Listener Support ***********************
    private EventListenerList listenerList = new EventListenerList();
    
    public void addFileInfoChangedListener(FileInfoChangedListener l) {
        listenerList.add(FileInfoChangedListener.class, l);
    }
    
    public void removeFileInfoChangedListener(FileInfoChangedListener l) {
        listenerList.remove(FileInfoChangedListener.class, l);
    }
    
    private void fireFileChanged(File file, FileInfo info) {
        FileInfoChangedEvent e = null;
        for(FileInfoChangedListener l: listenerList.getListeners(FileInfoChangedListener.class)) {
            if (e == null)
                e = new FileInfoChangedEvent(file, info, this);
            l.fileChanged(e);
        }
    }
    
    private void fireFileRemoved(File file, FileInfo info) {
        FileInfoChangedEvent e = null;
        for(FileInfoChangedListener l: listenerList.getListeners(FileInfoChangedListener.class)) {
            if (e == null)
                e = new FileInfoChangedEvent(file, info, this);
            l.fileRemoved(e);
        }
    }
    
    
}
