/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.notifications;

import net.landora.animeinfo.AnimeInfoPreference;
import net.landora.gsuiutils.NBTask;
import net.landora.gsuiutils.PeriodicTask;

/**
 *
 * @author bdickie
 */
public class NotificationsPeriodic extends PeriodicTask {
    
    @Override
    protected Long getRunPeriodPreferenceValue() {
        return AnimeInfoPreference.NotificationsLastRefreshed.getLong();
    }

    @Override
    protected void setRunPeriodPreferenceValue(long lastRun) {
        AnimeInfoPreference.NotificationsLastRefreshed.setLong(lastRun);
    }

    @Override
    protected long getRunPeriod() {
        return INTERVAL_MINUTE * 30;
    }

    @Override
    protected NBTask<?, ?> createTask() {
        return new NotificationsTask();
    }
    
}
