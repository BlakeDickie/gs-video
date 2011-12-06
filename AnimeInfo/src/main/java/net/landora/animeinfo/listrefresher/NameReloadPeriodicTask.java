/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.listrefresher;

import net.landora.animeinfo.AnimeInfoPreference;
import net.landora.gsuiutils.NBTask;
import net.landora.gsuiutils.PeriodicTask;

/**
 *
 * @author bdickie
 */
public class NameReloadPeriodicTask extends PeriodicTask {

    private static final long AUTOMATICALLY_RELOAD_NAMES_EVERY = INTERVAL_WEEK;
    
    @Override
    protected Long getRunPeriodPreferenceValue() {
        return AnimeInfoPreference.NamesLastRefreshed.getLong();
    }

    @Override
    protected void setRunPeriodPreferenceValue(long lastRun) {
        AnimeInfoPreference.NamesLastRefreshed.setLong(lastRun);
    }

    @Override
    protected long getRunPeriod() {
        return AUTOMATICALLY_RELOAD_NAMES_EVERY;
    }

    @Override
    protected NBTask<?, ?> createTask() {
        return new NameReloadTask();
    }
    
}
