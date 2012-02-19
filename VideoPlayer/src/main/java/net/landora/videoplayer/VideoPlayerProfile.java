/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoplayer;

import net.landora.video.profile.RunProfile;

/**
 *
 * @author bdickie
 */
public class VideoPlayerProfile implements RunProfile {

    public String getProfileName() {
        return "player";
    }

    public String getProfileDescription() {
        return "Video Player";
    }

    public boolean isManager() {
        return false;
    }

    public boolean isVideo() {
        return true;
    }

    public boolean isTool() {
        return false;
    }

    public boolean isGUIEnabled() {
        return true;
    }

    public boolean readCommandLine(String[] args) {
        return true;
    }

    public Integer runProfile() {
        
        
        return null;
    }
    
}
