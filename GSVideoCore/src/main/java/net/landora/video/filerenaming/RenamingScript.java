/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import net.landora.video.info.VideoMetadata;

/**
 *
 * @author bdickie
 */
public interface RenamingScript {
    public String findFolderName(VideoMetadata metadata);
    public String findFilename(VideoMetadata metadata);
}
