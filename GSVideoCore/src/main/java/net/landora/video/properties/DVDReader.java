/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.properties;

import net.landora.video.module.ModuleInterface;
import net.landora.video.properties.dvd.DVDDisk;

/**
 *
 * @author bdickie
 */
public interface DVDReader extends ModuleInterface {
    public DVDDisk readDisk();
    public DVDDisk readDisk(String devicePath);
}
