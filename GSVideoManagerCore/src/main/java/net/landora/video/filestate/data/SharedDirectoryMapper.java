/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filestate.data;

import java.util.List;

/**
 *
 * @author bdickie
 */
public interface SharedDirectoryMapper {
    public void insertSharedDirectory(SharedDirectory directory);
    public void updateSharedDirectory(SharedDirectory directory);
    public SharedDirectory selectSharedDirectoryByUUID(String uuid);
    public SharedDirectory selectSharedDirectoryByDirectoryId(int directoryId);
    public List<SharedDirectory> selectAllSharedDirectories();
    
    
    public void insertFileRecord(FileRecord record);
    public void updateFileRecord(FileRecord record);
    public void deleteFileRecord(FileRecord record);
    public FileRecord selectFileRecordByPath(int directoryId, String subPath);
    public List<FileRecord> selectFileRecordsInDirectory(int directoryId);
    public List<FileRecord> selectFileRecordsByProviders(String metadataProvider);
}
