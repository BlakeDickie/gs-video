/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.module;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class ModulesConfigAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.module.ModulesConfigAddon";

    public ModulesConfigAddon() {
        super(ID, "Module Config", UIAddon.ID, ModulesAddon.ID);
    }

    @Override
    public void start() {
        UIAddon.getInstance().addConfigurationPanel(ModuleConfigPanel.class);
    }
    
    
    
}
