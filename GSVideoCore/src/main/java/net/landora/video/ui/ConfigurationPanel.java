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

package net.landora.video.ui;

import javax.swing.JPanel;
import net.landora.video.ui.config.ConfigurationChangedEvent;
import net.landora.video.ui.config.ConfigurationChangedListener;

/**
 *
 * @author bdickie
 */
public class ConfigurationPanel extends JPanel {

    public ConfigurationPanel() {
    }

    private String panelName;

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }
    
    public void load() {
        
    }
    
    public void store() {
        
    }
    
    public boolean valid() {
        return true;
    }
    
    protected void changed() {
        ConfigurationChangedEvent e = null;
        for(ConfigurationChangedListener l: listenerList.getListeners(ConfigurationChangedListener.class)) {
            if (e == null)
                e = new ConfigurationChangedEvent(this);
            l.configurationPanelChanged(e);
        }
    }
    
    
    public void addConfigurationChangedListener(ConfigurationChangedListener l) {
        listenerList.add(ConfigurationChangedListener.class, l);
    }
    
    
    public void removeConfigurationChangedListener(ConfigurationChangedListener l) {
        listenerList.remove(ConfigurationChangedListener.class, l);
    }
}
