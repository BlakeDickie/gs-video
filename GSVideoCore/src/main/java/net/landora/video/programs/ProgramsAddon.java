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

package net.landora.video.programs;

import java.util.*;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.preferences.LocalPreferenceObject;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.UIAddon;
import net.landora.video.utils.UIUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;

/**
 *
 * @author bdickie
 */
public class ProgramsAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.programs.ProgramsAddon";

    public ProgramsAddon() {
        super(ID, "Programs", PreferencesAddon.ID, UIAddon.ID);
        
        preferences = new HashMap<String,LocalPreferenceObject>();
        programs = new HashMap<String, Program>();
    }
    
    public static ProgramsAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(ProgramsAddon.class);
    }
    
    private Map<String,LocalPreferenceObject> preferences;
    private Map<String,Program> programs;

    @Override
    public void start() {
        registerProgram(CommonPrograms.LSDVD);
        registerProgram(CommonPrograms.MPLAYER);
        registerProgram(CommonPrograms.MEDIAINFO);
        registerProgram(CommonPrograms.MKVINFO);
        
        UIAddon.getInstance().addConfigurationPanel(ProgramConfigurationPanel.class);
    }
    
    
    
    public synchronized void registerProgram(Program program) {
        if (preferences.containsKey(program.getId())) {
            throw new IllegalStateException("Program already registered: " + program.getId());
        }
        
        // TODO: Add OS detection.
        LocalPreferenceObject pref = new LocalPreferenceObject(Program.class, program.getId(), program.getDefaultUnixPath());
        preferences.put(program.getId(), pref);
        programs.put(program.getId(), program);
    }
    
    public synchronized String getConfiguredPath(Program program) {
        LocalPreferenceObject pref = preferences.get(program.getId());
        if (pref == null) {
            throw new IllegalStateException("Program not registered: " + program.getId());
        }
        
        return pref.getString();
    }
    
    public boolean isConfigured(Program program) {
        return getConfiguredPath(program) != null;
    }
    
    public boolean isAvaliable(Program program) {
        String path = getConfiguredPath(program);
        if (path == null)
            return false;
        
        ArrayList<String> command = new ArrayList<String>();
        command.add(path);
        command.addAll(program.getTestArguments());
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        
        try {
            Process p = builder.start();
            IOUtils.copy(p.getInputStream(), new NullOutputStream());
            p.waitFor();
            return true;
        } catch (Exception e) {
            log.info("Error checking for program: " + program, e);
            return false;
        }
    }
    
    public List<Program> getAllPrograms() {
        ArrayList<Program> result = new ArrayList<Program>(programs.values());
        Collections.sort(result, UIUtils.LEXICAL_SORTER);
        return result;
    }
}
