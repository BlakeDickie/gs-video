/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

@ActionID(category = "VideoFile",
id = "net.landora.videofilebrowser.actions.DeleteFileMenu")
@ActionRegistration(displayName = "#CTL_DeleteFileMenu")
@ActionReferences({})
@Messages("CTL_DeleteFileMenu=Delete")
public final class DeleteFileMenu extends AbstractAction implements Presenter.Menu, Presenter.Popup {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }

    @Override
    public JMenuItem getMenuPresenter() {
        return getPopupPresenter();
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenu menu = new JMenu(NbBundle.getMessage(DeleteFileMenu.class, "CTL_DeleteFileMenu"));
        List<? extends Action> actionsForPath = Utilities.actionsForPath("Actions/DeleteFile");
        for(Action a: actionsForPath)
            menu.add(a);
        
        return menu;
    }
}
