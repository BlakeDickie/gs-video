/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.playerbackends;

/**
 *
 * @author bdickie
 */
public class VLCBackend extends GenericPlayerBackend {

    public VLCBackend() {
        super(PlayerBackendAddon.vlcProgram, "--play-and-exit", "--fullscreen");
    }

    @Override
    public int getModulePriority() {
        return MODULE_PRIORITY_FALLBACK + 1;
    }
    
    
}
