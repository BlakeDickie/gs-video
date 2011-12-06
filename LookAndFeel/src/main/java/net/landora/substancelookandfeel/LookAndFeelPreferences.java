/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.substancelookandfeel;

import java.util.List;
import net.landora.videoinfo.data.preferences.NBPreferenceObject;

/**
 *
 * @author bdickie
 */
public enum LookAndFeelPreferences {
    UnixLookAndFeelType("GTK"),
    DecorateFrames(false);
    
    private LookAndFeelPreferences(String defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private LookAndFeelPreferences(int defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private LookAndFeelPreferences(long defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private LookAndFeelPreferences(boolean defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private LookAndFeelPreferences(String[] defaultValue) {
        pref = new NBPreferenceObject(getClass(), name(), defaultValue);
    }
    
    private NBPreferenceObject pref;

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
        return pref.getString();
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
