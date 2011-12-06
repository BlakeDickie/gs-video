/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.videoinfo;

import java.util.List;
import net.landora.videoinfo.file.FileInfo;

/**
 *
 * @author bdickie
 */
public interface MetadataProvider {

    public int getProviderVersion();
    public String getProviderId();
    public List<MetadataMatch> checkForMatch(FileInfo info);
    public VideoMetadata getMetadata(String metadataId);
//    public List<?> searchMetadata(String searchTerm);
}
