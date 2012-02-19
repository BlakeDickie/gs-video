/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.actions;

import net.landora.video.addons.AbstractAddon;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class ActionsAddon extends AbstractAddon {
    public static final String ID = "net.landora.video.actions.ActionsAddon";

    public ActionsAddon() {
        super(ID, "General Actions", UIAddon.ID);
    }

    @Override
    public void start() {
        UIAddon.getInstance().addAction(
                new MarkWatchedAction()
                );
    }
    
    
    
}
