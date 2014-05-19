/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
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
