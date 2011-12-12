/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.info.data.preferences;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author bdickie
 */
public interface PreferencesMapper {
    public List<String> getVideoExtensions();
    public void deleteVideoExtensions();
    public void insertVideoExtension(String extension);
    
    public List<HashMap<String,String>> getPreferences(String context);
    public int updatePreference(String context, String prefName, String prefValue);
    public void insertPreference(String context, String prefName, String prefValue);
}
