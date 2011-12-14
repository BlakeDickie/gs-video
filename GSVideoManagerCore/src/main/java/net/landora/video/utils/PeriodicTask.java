/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public abstract class PeriodicTask {
    public static final long INTERVAL_SECOND = 1000;
    public static final long INTERVAL_MINUTE = INTERVAL_SECOND * 60;
    public static final long INTERVAL_HOUR = INTERVAL_MINUTE * 60;
    public static final long INTERVAL_DAY = INTERVAL_HOUR * 24;
    public static final long INTERVAL_WEEK = INTERVAL_DAY * 7;
    
    protected abstract Long getRunPeriodPreferenceValue();
    protected abstract void setRunPeriodPreferenceValue(long lastRun);
    protected abstract long getRunPeriod();
    protected abstract NBTask<?,?> createTask();
}
