/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info.file;

import java.util.EventListener;

/**
 *
 * @author bdickie
 */
public interface FileInfoChangedListener extends EventListener {
    public void fileChanged(FileInfoChangedEvent e);
    public void fileRemoved(FileInfoChangedEvent e);
}
