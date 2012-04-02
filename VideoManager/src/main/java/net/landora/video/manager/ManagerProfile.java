/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
