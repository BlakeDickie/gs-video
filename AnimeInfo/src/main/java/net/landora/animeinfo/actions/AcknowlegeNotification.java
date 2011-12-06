/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeNotification;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "AnimeNotification",
id = "net.landora.animeinfo.notifications.AcknowlegeNotification")
@ActionRegistration(displayName = "#CTL_AcknowlegeNotification")
@ActionReferences({ })
@Messages("CTL_AcknowlegeNotification=Acknowlege Notification")
public final class AcknowlegeNotification implements ActionListener {

    private final List<AnimeNotification> context;

    public AcknowlegeNotification(List<AnimeNotification> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        for (AnimeNotification animeNotification : context) {
            if (animeNotification.getRemovedDate() == null) {
                if (animeNotification.getViewDate() == null)
                    animeNotification.setViewDate(Calendar.getInstance());
                animeNotification.setRemovedDate(Calendar.getInstance());
                AnimeDBA.saveAnimeNotification(animeNotification);
            }
        }
    }
}
