/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.listrefresher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Tools",
id = "net.landora.animelistrefresher.UpdateAnimeTitles")
@ActionRegistration(displayName = "#CTL_UpdateAnimeTitles")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 150, separatorBefore = 125)
})
@Messages("CTL_UpdateAnimeTitles=Update Anime Titles")
public final class UpdateAnimeTitles implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        new NameReloadTask().startTask();
    }
}
