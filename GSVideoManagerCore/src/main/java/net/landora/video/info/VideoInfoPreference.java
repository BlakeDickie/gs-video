/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info;

import java.util.List;
import java.util.UUID;
import net.landora.video.info.data.preferences.LocalPreferenceObject;
import net.landora.video.info.data.preferences.PreferenceObject;

/**
 *
 * @author bdickie
 */
public enum VideoInfoPreference {
    VideoExtensions(true, new String[] { "mkv", "mp4", "m4v", "rm", "rmv", "avi", "ogg", "wmv" }),
    MachineUUID(false, "8a8e26ea-90c1-4b7e-88e3-f5c455fe9878"),
    DatabaseHost(false, "quon"),
    DatabaseName(false, "videos"),
    DatabaseUsername(false, "videos"),
    DatabasePassword(false, "hawkpath");
    
    private VideoInfoPreference(boolean global, String defaultValue) {
        if (global)
            pref = new PreferenceObject(getClass(), name(), defaultValue);
        else
            pref = new LocalPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private VideoInfoPreference(boolean global, int defaultValue) {
        if (global)
            pref = new PreferenceObject(getClass(), name(), defaultValue);
        else
            pref = new LocalPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private VideoInfoPreference(boolean global, boolean defaultValue) {
        if (global)
            pref = new PreferenceObject(getClass(), name(), defaultValue);
        else
            pref = new LocalPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private VideoInfoPreference(boolean global, String[] defaultValue) {
        if (global)
            pref = new PreferenceObject(getClass(), name(), defaultValue);
        else
            pref = new LocalPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private VideoInfoPreference(boolean global, long defaultValue) {
        if (global)
            pref = new PreferenceObject(getClass(), name(), defaultValue);
        else
            pref = new LocalPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private PreferenceObject pref;

    public void setStringList(List<String> value) {
        pref.setStringList(value);
    }

    public void setString(String value) {
        pref.setString(value);
    }

    public void setInt(int value) {
        pref.setInt(value);
    }

    public void setBoolean(boolean value) {
        pref.setBoolean(value);
    }

    public List<String> getStringList() {
        return pref.getStringList();
    }

    public String getString() {
        String value = pref.getString();
        if (this == MachineUUID && value.isEmpty()) {
            value = UUID.randomUUID().toString();
            setString(value);
        }
        return value;
    }

    public String getPrefName() {
        return pref.getPrefName();
    }

    public int getInt() {
        return pref.getInt();
    }

    public boolean getBoolean() {
        return pref.getBoolean();
    }

    public void setLong(long value) {
        pref.setLong(value);
    }

    public long getLong() {
        return pref.getLong();
    }
    
}
