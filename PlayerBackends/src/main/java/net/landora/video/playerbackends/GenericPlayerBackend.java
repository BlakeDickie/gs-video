/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.playerbackends;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.landora.video.module.AbstractModule;
import net.landora.video.player.VideoFilePlayer;
import net.landora.video.programs.Program;
import net.landora.video.programs.ProgramsAddon;
import net.landora.video.utils.MutableObject;
import net.landora.video.utils.OSUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author bdickie
 */
public abstract class GenericPlayerBackend extends AbstractModule implements VideoFilePlayer {

    private Program program;
    private String[] extraArgs;
    
    public GenericPlayerBackend(Program program, String...extraArgs) {
        this.program = program;
        this.extraArgs = extraArgs;
    }

    @Override
    public boolean canPlayFile(File file) {
        return true;
    }

    @Override
    public void playFile(File file) {
        try {
            List<String> args = new ArrayList<String>();
            args.add(ProgramsAddon.getInstance().getConfiguredPath(program));
            args.addAll(Arrays.asList(extraArgs));
            args.add(file.getAbsolutePath());
            
            ProcessBuilder process = new ProcessBuilder(args);
            process.redirectErrorStream(true);
            Process p = process.start();


            StringWriter buffer = new StringWriter();
            IOUtils.copy(p.getInputStream(), buffer);
            p.waitFor();
            
            
        } catch (Exception e) {
            log.error("Error playing file with " + program.getName() + ": " + file, e);
        }
    }

    @Override
    protected void loadModuleState(MutableObject<Boolean> usable, MutableObject<Boolean> configured, MutableObject<String> errorMessage) {
        if (OSUtils.isWindows() && program.getDefaultWindowsPath() == null) {
            errorMessage.setValue(program.getName() + " is not avaliable on Windows.");
            return;
        } else if (OSUtils.isUnix() && program.getDefaultUnixPath() == null) {
            errorMessage.setValue(program.getName() + " is not avaliable on Unix.");
            return;
        }
        
        usable.setValue(true);
        
        if (!ProgramsAddon.getInstance().isAvaliable(program)) {
            errorMessage.setValue(program.getName() + " not installed or configured.");
            return;
        }
        
        configured.setValue(true);
    }
    
    @Override
    public String getModuleName() {
        return program.getName() + " Backend";
    }

    @Override
    public String getModuleDescription() {
        return "A video backend using " + program.getName() + ".";
    }
}
