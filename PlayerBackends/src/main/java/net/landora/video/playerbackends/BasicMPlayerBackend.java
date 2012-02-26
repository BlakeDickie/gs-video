/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.playerbackends;

import net.landora.video.programs.CommonPrograms;

/**
 *
 * @author bdickie
 */
public class BasicMPlayerBackend extends GenericPlayerBackend {

    public BasicMPlayerBackend() {
        super(CommonPrograms.MPLAYER, "-fs");
    }

    @Override
    public String getModuleName() {
        return "MPlayer Basic Backend";
    }

    @Override
    public String getModuleDescription() {
        return "A simple video backend using MPlayer.";
    }

    @Override
    public int getModulePriority() {
        return MODULE_PRIORITY_FALLBACK;
    }
    
    
}
