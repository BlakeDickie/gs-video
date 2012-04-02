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
