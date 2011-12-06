/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilerenaming;

import net.landora.videoinfo.VideoMetadata;

/**
 *
 * @author bdickie
 */
public interface RenamingScript {
    public String findFolderName(VideoMetadata metadata);
    public String findFilename(VideoMetadata metadata);
}
