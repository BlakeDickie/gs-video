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

package net.landora.video;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.landora.video.addons.AddonManager;
import net.landora.video.preferences.FileSyncProperties;
import net.landora.video.profile.ProfileSorter;
import net.landora.video.profile.RunProfile;
import net.landora.video.utils.EventBus;
import net.landora.video.utils.NamedThreadFactory;
import net.landora.video.utils.OSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public final class VideoManagerApp {

    private static final Logger log = LoggerFactory.getLogger(VideoManagerApp.class);
    
    private FileSyncProperties localProperties;
    private ScheduledExecutorService scheduledExecutor;
    
    private static VideoManagerApp instance;
    
    public static VideoManagerApp getInstance() {
        return instance;
    }

    private VideoManagerApp() {
        profiles = new TreeMap<String, RunProfile>(String.CASE_INSENSITIVE_ORDER);
    }
    
    public synchronized FileSyncProperties getLocalProperties() {
        if (localProperties == null)
            localProperties = new FileSyncProperties("user.properties");
        return localProperties;
    }
    
    public synchronized ScheduledExecutorService getScheduledExecutor() {
        if (scheduledExecutor == null)
            scheduledExecutor = Executors.newScheduledThreadPool(2, new NamedThreadFactory("Scheduled", true));
        return scheduledExecutor;
    }
    
    private Map<String,RunProfile> profiles;
    
    private EventBus eventBus = new EventBus();

    private RunProfile profile;

    public EventBus getEventBus() {
        return eventBus;
    }

    public RunProfile getProfile() {
        return profile;
    }
    
    public synchronized void addProfile(RunProfile profile) {
        String name = profile.getProfileName();
        if (name.equalsIgnoreCase("help"))
            throw new IllegalArgumentException("Reserved profile name: " + name);
        if (profiles.containsKey(name))
            throw new IllegalArgumentException("Profile with name already exists: " + name);
        profiles.put(name, profile);
    }
    
    protected void initialize(String[] args) {
        
        AddonManager.getInstance().loadAddons();
        
        String profileName = null;
        List<String> argsList = new ArrayList<String>(Arrays.asList(args));
        if (argsList.isEmpty()) {
            for(RunProfile p: profiles.values())
                if (p.isManager())
                    profileName = p.getProfileName();
            if (profileName == null) {
                log.error("No manager profile found.");
                System.exit(-1);
            }
            
        } else {
            profileName = argsList.remove(0);
        }
        
        if (profileName.equalsIgnoreCase("help")) {
            List<RunProfile> lists = new ArrayList<RunProfile>(profiles.values());
            Collections.sort(lists, new ProfileSorter());
            
            System.out.println("Avaliable profiles:");
            for(RunProfile p: lists)
                System.out.println("  " + p.getProfileName() + ": " + p.getProfileDescription());
            
            System.exit(0);
            return;
        }
        
        profile = profiles.get(profileName);
        if (profile == null) {
            log.error("No such profile: " + profileName);
            return;
        }
        
        if (!profile.readCommandLine(argsList.toArray(new String[argsList.size()]))) {
            profile = null;
            return;
        }
        
        
        try {
            if (OSUtils.isWindows() || OSUtils.isMac())
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            else
                UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
        } catch (Exception ex) {
            log.error("Error setting look and feel.", ex);
        }
        
        
        AddonManager.getInstance().startAddons();
        
    }

    protected void ready() {
        
        if (profile == null) {
            System.exit(-1);
            return;
        }
        
        AddonManager.getInstance().readyAddons();
        
        Integer returnCode = profile.runProfile();
        
        if (returnCode != null)
            System.exit(returnCode);
    }

    public static void main(final String[] args) {
        instance = new VideoManagerApp();
        
        final Runnable readyRun = new Runnable() {

            @Override
            public void run() {
                instance.ready();
            }
        };
        
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                instance.initialize(args);
                
                SwingUtilities.invokeLater(readyRun);
            }
        };
        
        SwingUtilities.invokeLater(runnable);
    }
    
    
}
