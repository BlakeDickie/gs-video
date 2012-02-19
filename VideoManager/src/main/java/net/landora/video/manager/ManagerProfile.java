/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.manager;

import net.landora.video.profile.RunProfile;

/**
 *
 * @author bdickie
 */
public class ManagerProfile implements RunProfile {

    public static final String PROFILE_NAME = "manager";
    
    @Override
    public String getProfileName() {
        return PROFILE_NAME;
    }

    @Override
    public String getProfileDescription() {
        return "The full management profile.";
    }

    @Override
    public boolean isManager() {
        return true;
    }

    @Override
    public boolean isVideo() {
        return false;
    }

    @Override
    public boolean isTool() {
        return false;
    }

    @Override
    public boolean isGUIEnabled() {
        return true;
    }

    @Override
    public boolean readCommandLine(String[] args) {
        if (args.length != 0) {
            System.err.println("The manager profile does not support any command line arguments.");
            return false;
        }
        return true;
    }

    @Override
    public Integer runProfile() {
        return null;
    }
    
}
