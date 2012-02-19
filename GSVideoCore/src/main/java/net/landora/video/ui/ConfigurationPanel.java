/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
