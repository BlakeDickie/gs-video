/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.mylistreader;

import net.landora.animeinfo.AnimeInfoPreference;
import net.landora.gsuiutils.NBTask;
import net.landora.gsuiutils.PeriodicTask;

/**
 *
 * @author bdickie
 */
public class MyListExportPeriodicTask extends PeriodicTask {

    @Override
    protected Long getRunPeriodPreferenceValue() {
        return AnimeInfoPreference.LastMyListExport.getLong();
    }

    @Override
    protected void setRunPeriodPreferenceValue(long lastRun) {
        AnimeInfoPreference.LastMyListExport.setLong(Math.max(lastRun, AnimeInfoPreference.LastMyListExport.getLong()));
    }

    @Override
    protected long getRunPeriod() {
        return INTERVAL_WEEK;
    }

    @Override
    protected NBTask<?, ?> createTask() {
        return new MyListExportTask();
    }
    
}
