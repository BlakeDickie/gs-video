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

package net.landora.video.playerbackends;

import java.util.Collections;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.module.ModulesAddon;
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
        super(ID, "Core Player Backends", VideoFilePlayerAddon.ID, ProgramsAddon.ID, ModulesAddon.ID);
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
