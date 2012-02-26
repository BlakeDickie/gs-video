/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.playerbackends;

/**
 *
 * @author bdickie
 */
public class SMPlayerBackend extends GenericPlayerBackend {

    public SMPlayerBackend() {
        super(PlayerBackendAddon.smplayerProgram, "-close-at-end", "-fullscreen");
    }

    @Override
    public int getModulePriority() {
        return MODULE_PRIORITY_GENERAL;
    }
    
    
}
