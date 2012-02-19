/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.actions;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;
import net.landora.video.info.ViewListState;
import net.landora.video.ui.UIAction;


public final class MarkWatchedAction extends UIAction<ViewListState> {

    public MarkWatchedAction() {
        super(ViewListState.class, "Mark Watched", true);
    }

    

    @Override
    public void actionPerformed(ActionEvent evt, List<ViewListState> context) {
        Calendar now = Calendar.getInstance();
        for (ViewListState viewListState : context) {
            viewListState.setLastViewed(now);
        }
    }
}
