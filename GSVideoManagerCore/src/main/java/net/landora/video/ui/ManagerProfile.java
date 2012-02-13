/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui;

import net.landora.video.profile.RunProfile;

/**
 *
 * @author bdickie
 */
public class ManagerProfile implements RunProfile {

    public static final String PROFILE_NAME = "manager";
    
    public String getProfileName() {
        return PROFILE_NAME;
    }

    public String getProfileDescription() {
        return "The full management profile.";
    }

    public boolean isManager() {
        return true;
    }

    public boolean isVideo() {
        return false;
    }

    public boolean isTool() {
        return false;
    }

    public boolean isGUIEnabled() {
        return true;
    }

    public boolean readCommandLine(String[] args) {
        if (args.length != 0) {
            System.err.println("The manager profile does not support any command line arguments.");
            return false;
        }
        return true;
    }

    public Integer runProfile() {
        return null;
    }
    
}
