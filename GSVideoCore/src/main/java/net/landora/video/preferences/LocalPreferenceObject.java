/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.landora.video.preferences;

import net.landora.video.VideoManagerApp;


/**
 *
 * @author bdickie
 */
public class LocalPreferenceObject extends PreferenceObject {

    public LocalPreferenceObject(Class<?> context, String prefName, String[] defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<?> context, String prefName, boolean defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<?> context, String prefName, long defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<?> context, String prefName, int defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<?> context, String prefName, String defaultValue) {
        super(context, prefName, defaultValue);
    }

    private static String getPropertyPrefix(Class<?> clazz, String prefName) {
        return clazz.getName() + "." + prefName;
    }
    
    @Override
    protected void setCurrentValue(Class<?> context, String prefName, String value) {
        VideoManagerApp.getInstance().getLocalProperties().setValue(getPropertyPrefix(context, prefName), value);
    }
    
    @Override
    protected String getCurrentValue(Class<?> context, String prefName) {
        return VideoManagerApp.getInstance().getLocalProperties().getValue(getPropertyPrefix(context, prefName));
    }
}
