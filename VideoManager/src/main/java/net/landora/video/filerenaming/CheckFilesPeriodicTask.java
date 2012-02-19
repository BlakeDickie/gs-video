/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import net.landora.video.tasks.NBTask;
import net.landora.video.tasks.PeriodicTask;


/**
 *
 * @author bdickie
 */
public class CheckFilesPeriodicTask extends PeriodicTask {

    @Override
    protected Long getRunPeriodPreferenceValue() {
        return null;
    }

    @Override
    protected void setRunPeriodPreferenceValue(long lastRun) {
        
    }

    @Override
    protected long getRunPeriod() {
        return INTERVAL_HOUR;
    }

    @Override
    protected NBTask<?, ?> createTask() {
        return new CheckFilesTask();
    }
    
}
