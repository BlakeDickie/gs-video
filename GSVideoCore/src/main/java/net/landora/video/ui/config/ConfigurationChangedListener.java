/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui.config;

import java.util.EventListener;

/**
 *
 * @author bdickie
 */
public interface ConfigurationChangedListener extends EventListener {
    public void configurationPanelChanged(ConfigurationChangedEvent e);
}
