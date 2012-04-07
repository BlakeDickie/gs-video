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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author bdickie
 */
public class PreferenceObject {
    private Class<?> context;
    private String prefName;
    private String defaultValue;

    public PreferenceObject(Class<?> context, String prefName, String defaultValue) {
        this.context = context;
        this.prefName = prefName;
        this.defaultValue = defaultValue;
    }
    
    public PreferenceObject(Class<?> context, String prefName, int defaultValue) {
        this(context, prefName, String.valueOf(defaultValue));
    }
    
    public PreferenceObject(Class<?> context, String prefName, long defaultValue) {
        this(context, prefName, String.valueOf(defaultValue));
    }
    
    public PreferenceObject(Class<?> context, String prefName, boolean defaultValue) {
        this(context, prefName, String.valueOf(defaultValue));
    }
    
    public PreferenceObject(Class<?> context, String prefName, String[] defaultValue) {
        this(context, prefName, convertFromStringList(Arrays.asList(defaultValue)));
    }

    public Class<?> getContext() {
        return context;
    }

    public String getPrefName() {
        return prefName;
    }
    
    public void setString(String value) {
        if (value == null)
            value = "";
        
        setCurrentValue(context, prefName, value);
    }
    
    public void setBoolean(boolean value) {
        setString(String.valueOf(value));
    }
    
    public void setInt(int value) {
        setString(String.valueOf(value));
    }
    
    public void setLong(long value) {
        setString(String.valueOf(value));
    }
    
    protected void setCurrentValue(Class<?> context, String prefName, String value) {
        Preferences.getInstance().setValue(context.getName(), prefName, value);
    }
    
    protected String getCurrentValue(Class<?> context, String prefName) {
        return Preferences.getInstance().getValue(context.getName(), prefName);
    }
    
    public String getString() {
        String value = getCurrentValue(context, prefName);
        if (value == null)
            return defaultValue;
        else
            return value;
    }
    
    public int getInt() {
        return Integer.parseInt(getString());
    }
    
    public long getLong() {
        return Long.parseLong(getString());
    }
    
    public boolean getBoolean() {
        return Boolean.parseBoolean(getString());
    }
    
    public List<String> getStringList() {
        String value = getString();
        return convertToStringList(value);
    }
    
    public void setStringList(List<String> value) {
        setString(convertFromStringList(value));
    }
    
    private static String convertFromStringList(List<String> items) {
        StringBuilder builder = new StringBuilder();
        for(String str: items) {
            builder.append(StringEscapeUtils.escapeJava(str));
            builder.append("\t");
        }
        return builder.toString();
        
    }
    
    private static List<String> convertToStringList(String value) {
        if (value.isEmpty())
            return Collections.EMPTY_LIST;
        
        List<String> result = new ArrayList<String>();
        int lastIndex = -1;
        int index;
        while((index = value.indexOf('\t', lastIndex + 1)) != -1) {
            result.add(StringEscapeUtils.unescapeJava(value.substring(lastIndex + 1, index)));
            lastIndex = index;
        }
        return result;
    }
}
