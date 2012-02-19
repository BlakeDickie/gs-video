/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author bdickie
 */
public class Preferences {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static Preferences instance = new Preferences();
    }

    public static Preferences getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    
    private Preferences() {
        Timer t = new Timer("Preferences Save", true);
        t.schedule(new SavePreferencesTimer(), 1000*60, 1000*60);
        
        values = new HashMap<String, Map<String, String>>();
        updatedValues = new HashMap<String, Map<String, String>>();
    }
    
    private Map<String,Map<String,String>> values;
    private Map<String,Map<String,String>> updatedValues;
    
    public synchronized String getValue(String context, String prefName) {
        Map<String,String> contentValues = values.get(context);
        if (contentValues == null) {
            contentValues = new HashMap<String, String>();
            contentValues.putAll(PreferencesDBA.getPreferenceValues(context));
            values.put(context, contentValues);
        }
        
        return contentValues.get(prefName);
    }
    
    public synchronized void setValue(String context, String prefName, String prefValue) {
        Map<String,String> contentValues = values.get(context);
        if (contentValues == null) {
            contentValues = new HashMap<String, String>();
            contentValues.putAll(PreferencesDBA.getPreferenceValues(context));
            values.put(context, contentValues);
        }
        
//        if ( Utilities.compareObjects(contentValues.get(prefName), prefValue))
//            return;
        
        Map<String,String> contentUpdatedValues = updatedValues.get(context);
        if (contentUpdatedValues == null) {
            contentUpdatedValues = new HashMap<String, String>();
            updatedValues.put(context, contentUpdatedValues);
        }
        
        contentValues.put(prefName, prefValue);
        contentUpdatedValues.put(prefName, prefValue);
    }
    
    
    
    public synchronized void save() {
        for(Map.Entry<String,Map<String,String>> items: updatedValues.entrySet()) {
            String context = items.getKey();
            for(Map.Entry<String,String> entry: items.getValue().entrySet()) {
                PreferencesDBA.setPreferenceValue(context, entry.getKey(), entry.getValue());
            }
        }
        
        updatedValues.clear();
        values.clear();
    }
    
    private class SavePreferencesTimer extends TimerTask {

        @Override
        public void run() {
            save();
        }
        
    }
}
