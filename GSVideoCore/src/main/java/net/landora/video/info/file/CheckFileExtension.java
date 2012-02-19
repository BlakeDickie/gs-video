/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info.file;

import java.io.File;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.file.FileInfo;

/**
 *
 * @author bdickie
 */
public interface CheckFileExtension {
    public void checkFile(File file, VideoMetadata md, FileInfo fileInfo);
}
