/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.actions;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.List;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeNotification;

import net.landora.video.ui.UIAction;

//@Messages("CTL_AcknowlegeNotification=Acknowlege Notification")
public final class AcknowlegeNotification extends UIAction<AnimeNotification> {

    public AcknowlegeNotification() {
        super(AnimeNotification.class, "Acknowlege Notification", true);
    }

    @Override
    public void actionPerformed(ActionEvent evt, List<AnimeNotification> context) {
        
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
