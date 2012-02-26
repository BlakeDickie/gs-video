/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.playerbackends;

import java.util.Collections;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.module.ModulesManager;
import net.landora.video.player.VideoFilePlayer;
import net.landora.video.player.VideoFilePlayerAddon;
import net.landora.video.programs.Program;
import net.landora.video.programs.ProgramsAddon;

/**
 *
 * @author bdickie
 */
public class PlayerBackendAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.playerbackends.PlayerBackendAddon";

    public PlayerBackendAddon() {
        super(ID, "Core Player Backends", VideoFilePlayerAddon.ID, ProgramsAddon.ID);
    }

    @Override
    public void start() {
        
        ProgramsAddon.getInstance().registerProgram(vlcProgram);
        ProgramsAddon.getInstance().registerProgram(smplayerProgram);
        
        ModulesManager.getInstance().addModule(VideoFilePlayer.class, new SMPlayerBackend());
        ModulesManager.getInstance().addModule(VideoFilePlayer.class, new VLCBackend());
        ModulesManager.getInstance().addModule(VideoFilePlayer.class, new BasicMPlayerBackend());
    }
    
    public static PlayerBackendAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(PlayerBackendAddon.class);
    }
    
    public static final Program smplayerProgram = new Program("playerbackend.smplayer", "SMPlayer", "smplayer", Collections.singletonList("-h"));
    public static final Program vlcProgram = new Program("playerbackend.vlc", "VLC", "vlc", Collections.singletonList("-h"));

}
