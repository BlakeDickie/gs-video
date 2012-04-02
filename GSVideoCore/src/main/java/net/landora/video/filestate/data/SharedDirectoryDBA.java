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

package net.landora.video.filestate.data;

import java.util.Collection;
import java.util.List;
import net.landora.video.info.MetadataProvidersManager;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class SharedDirectoryDBA {
    private static Logger log = LoggerFactory.getLogger(SharedDirectoryDBA.class);
    
    
    public static List<SharedDirectory> getSharedDirectories() {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            return mapper.selectAllSharedDirectories();
        } catch (Exception e) {
            log.error("Error getting SharedDirectory.", e);
            
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static void saveSharedDirectory(SharedDirectory dir) {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            if (dir.getDirectoryId() < 1) {
                mapper.insertSharedDirectory(dir);
            } else {
                mapper.updateSharedDirectory(dir);
            }
            
            session.commit();
        } catch (Exception e) {
            log.error("Error saving SharedDirectory.", e);
            
            if (session != null)
                session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    public static void saveFileRecord(FileRecord file) {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            if (file.getFileId() < 1) {
                mapper.insertFileRecord(file);
            } else {
                mapper.updateFileRecord(file);
            }
            
            session.commit();
        } catch (Exception e) {
            log.error("Error saving FileRecord.", e);
            
            if (session != null)
                session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    
    public static void deleteFileRecord(FileRecord file) {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            mapper.deleteFileRecord(file);
            
            session.commit();
        } catch (Exception e) {
            log.error("Error deleting FileRecord.", e);
            
            if (session != null)
                session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static FileRecord getFileRecord(SharedDirectory directory, String subPath) {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            return mapper.selectFileRecordByPath(directory.getDirectoryId(), subPath);
        } catch (Exception e) {
            log.error("Error getting FileRecord.", e);
            
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static Collection<FileRecord> getFileRecords(SharedDirectory directory) {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            return mapper.selectFileRecordsInDirectory(directory.getDirectoryId());
        } catch (Exception e) {
            log.error("Error getting FileRecords.", e);
            
            return null;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static Collection<FileRecord> getUnmatchedFileRecords() {
        SqlSession session = null;

        try {
            session = FileStateDataManager.getInstance().openSession();
            SharedDirectoryMapper mapper = session.getMapper(SharedDirectoryMapper.class);

            return mapper.selectFileRecordsByProviders(MetadataProvidersManager.FAILURE_ID);
        } catch (Exception e) {
            log.error("Error getting FileRecords.", e);
            
            return null;
        } finally {
            if (session != null)
                session.close();
        }
        
    }
}
