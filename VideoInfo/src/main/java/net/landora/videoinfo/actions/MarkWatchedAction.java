/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;
import net.landora.videoinfo.ViewListState;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "ViewListState",
id = "net.landora.videoinfo.actions.MarkWatchedAction")
@ActionRegistration(displayName = "#CTL_MarkWatchedAction")
@ActionReferences({
})
@Messages("CTL_MarkWatchedAction=Mark Watched")
public final class MarkWatchedAction implements ActionListener {

    private final List<ViewListState> context;

    public MarkWatchedAction(List<ViewListState> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Calendar now = Calendar.getInstance();
        for (ViewListState viewListState : context) {
            viewListState.setLastViewed(now);
        }
    }
}
