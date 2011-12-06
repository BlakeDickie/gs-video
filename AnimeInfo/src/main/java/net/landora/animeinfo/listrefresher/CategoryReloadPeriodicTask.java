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
public class CategoryReloadPeriodicTask extends PeriodicTask {

    private static final long AUTOMATICALLY_RELOAD_CATEGORIES_EVERY = INTERVAL_WEEK * 4;
    
    @Override
    protected Long getRunPeriodPreferenceValue() {
        return AnimeInfoPreference.CategoriesLastRefreshed.getLong();
    }

    @Override
    protected void setRunPeriodPreferenceValue(long lastRun) {
        AnimeInfoPreference.CategoriesLastRefreshed.setLong(lastRun);
    }

    @Override
    protected long getRunPeriod() {
        return AUTOMATICALLY_RELOAD_CATEGORIES_EVERY;
    }

    @Override
    protected NBTask<?, ?> createTask() {
        return new CategoryReloadTask();
    }
    
}
