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

import javax.swing.JFrame;
import net.landora.video.VideoManagerApp;
import net.landora.video.addons.AbstractAddon;
import net.landora.video.addons.AddonManager;
import net.landora.video.preferences.PreferencesAddon;
import net.landora.video.ui.InfoPanel;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class ManagerAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.manager.ManagerAddon";

    public ManagerAddon() {
        super(ID, "Video Manager", PreferencesAddon.ID, UIAddon.ID);
    }
    
    public static ManagerAddon getInstance() {
        return AddonManager.getInstance().getAddonInstance(ManagerAddon.class);
    }

    @Override
    public void load() {
        VideoManagerApp.getInstance().addProfile(new ManagerProfile());
    }
    
    
    private ManagerFrame frame;
    
    @Override
    public void start() {
        
        if (VideoManagerApp.getInstance().getProfile().isManager()) {
            frame = new ManagerFrame();
        }
        
    }

    @Override
    public void ready() {
        if (frame != null) {
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
    }

    @Override
    public void stop() {
        if (frame != null)
            frame.setVisible(false);
    }
    
    public void addContentPanel(ContentPanel panel) {
        if (frame != null)
            frame.addContentPanel(panel);
    }

    public void addInfoPanel(InfoPanel panel) {
        if (frame != null)
            frame.addInfoPanel(panel);
    }
    
}
