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
