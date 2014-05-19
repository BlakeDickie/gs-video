/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.tasks;

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

    protected abstract void setRunPeriodPreferenceValue( long lastRun );

    protected abstract long getRunPeriod();

    protected abstract NBTask<?, ?> createTask();
}
