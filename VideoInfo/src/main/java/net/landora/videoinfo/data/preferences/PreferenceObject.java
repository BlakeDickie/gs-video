/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo.data.preferences;

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
    private Class<? extends Enum> context;
    private String prefName;
    private String defaultValue;

    public PreferenceObject(Class<? extends Enum> context, String prefName, String defaultValue) {
        this.context = context;
        this.prefName = prefName;
        this.defaultValue = defaultValue;
    }
    
    public PreferenceObject(Class<? extends Enum> context, String prefName, int defaultValue) {
        this(context, prefName, String.valueOf(defaultValue));
    }
    
    public PreferenceObject(Class<? extends Enum> context, String prefName, long defaultValue) {
        this(context, prefName, String.valueOf(defaultValue));
    }
    
    public PreferenceObject(Class<? extends Enum> context, String prefName, boolean defaultValue) {
        this(context, prefName, String.valueOf(defaultValue));
    }
    
    public PreferenceObject(Class<? extends Enum> context, String prefName, String[] defaultValue) {
        this(context, prefName, convertFromStringList(Arrays.asList(defaultValue)));
    }

    public Class<? extends Enum> getContext() {
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
    
    protected void setCurrentValue(Class<? extends Enum> context, String prefName, String value) {
        Preferences.getInstance().setValue(context.getName(), prefName, value);
    }
    
    protected String getCurrentValue(Class<? extends Enum> context, String prefName) {
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
        StringBuilder builder = new StringBuilder();
        if (value.equals(""))
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
