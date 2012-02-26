/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.properties;

import java.io.File;
import net.landora.video.module.ModuleInterface;

/**
 *
 * @author bdickie
 */
public interface VideoFileReader extends ModuleInterface {
    public Video parseFile(File file);
    public boolean supportsFile(File file);
}
