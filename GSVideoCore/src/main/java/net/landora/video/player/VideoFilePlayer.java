/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.player;

import java.io.File;
import net.landora.video.module.ModuleInterface;

/**
 *
 * @author bdickie
 */
public interface VideoFilePlayer extends ModuleInterface {
    public boolean canPlayFile(File file);
    public void playFile(File file);
}
