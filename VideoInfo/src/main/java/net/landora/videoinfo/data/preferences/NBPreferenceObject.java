/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo.data.preferences;

import org.openide.util.NbPreferences;

/**
 *
 * @author bdickie
 */
public class NBPreferenceObject extends PreferenceObject {

    public NBPreferenceObject(Class<? extends Enum> context, String prefName, String[] defaultValue) {
        super(context, prefName, defaultValue);
    }

    public NBPreferenceObject(Class<? extends Enum> context, String prefName, boolean defaultValue) {
        super(context, prefName, defaultValue);
    }

    public NBPreferenceObject(Class<? extends Enum> context, String prefName, long defaultValue) {
        super(context, prefName, defaultValue);
    }

    public NBPreferenceObject(Class<? extends Enum> context, String prefName, int defaultValue) {
        super(context, prefName, defaultValue);
    }

    public NBPreferenceObject(Class<? extends Enum> context, String prefName, String defaultValue) {
        super(context, prefName, defaultValue);
    }

    @Override
    protected void setCurrentValue(Class<? extends Enum> context, String prefName, String value) {
        java.util.prefs.Preferences prefs = NbPreferences.forModule(context);
        prefs.put(prefName, value);
    }
    
    @Override
    protected String getCurrentValue(Class<? extends Enum> context, String prefName) {
        java.util.prefs.Preferences prefs = NbPreferences.forModule(context);
        return prefs.get(prefName, null);
    }
}
