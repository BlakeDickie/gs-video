/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.preferences;

import net.landora.video.VideoManagerApp;


/**
 *
 * @author bdickie
 */
public class LocalPreferenceObject extends PreferenceObject {

    public LocalPreferenceObject(Class<? extends Enum> context, String prefName, String[] defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<? extends Enum> context, String prefName, boolean defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<? extends Enum> context, String prefName, long defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<? extends Enum> context, String prefName, int defaultValue) {
        super(context, prefName, defaultValue);
    }

    public LocalPreferenceObject(Class<? extends Enum> context, String prefName, String defaultValue) {
        super(context, prefName, defaultValue);
    }

    private static String getPropertyPrefix(Class<?> clazz, String prefName) {
        return clazz.getName() + "." + prefName;
    }
    
    @Override
    protected void setCurrentValue(Class<? extends Enum> context, String prefName, String value) {
        VideoManagerApp.getInstance().getLocalProperties().setValue(getPropertyPrefix(context, prefName), value);
    }
    
    @Override
    protected String getCurrentValue(Class<? extends Enum> context, String prefName) {
        return VideoManagerApp.getInstance().getLocalProperties().getValue(getPropertyPrefix(context, prefName));
    }
}
