/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.mplayer;

import java.util.*;

/**
 *
 * @author bdickie
 */
public class MPlayerInfoParse {
    private Map<String,List<String>> values;

    public MPlayerInfoParse() {
        values = new HashMap<String,List<String>>();
    }
    
    
    public void add(String key, String value) {
        List<String> valList = values.get(key);
        if (valList == null) {
            valList = new ArrayList<String>();
            values.put(key, valList);
        }
        valList.add(value);
    }
    
    public Set<String> getKeys() {
        return values.keySet();
    }
    
    public String getSingle(String key) {
        List<String> valList = values.get(key);
        return (valList == null ? null : valList.get(valList.size() - 1));
    }
    
    public List<String> getList(String key) {
        List<String> valList = values.get(key);
        return (valList == null ? Collections.EMPTY_LIST : valList);
    }
}
