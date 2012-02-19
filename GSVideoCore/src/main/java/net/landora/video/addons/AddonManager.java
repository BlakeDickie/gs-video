/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.addons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import org.apache.commons.lang.mutable.MutableInt;

/**
 *
 * @author bdickie
 */
public class AddonManager {
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static AddonManager instance = new AddonManager();
    }

    public static AddonManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private AddonManager() {
        
    }
    
    private LinkedHashMap<Class<? extends Addon>, Addon> addons;
    private boolean started;
    private ShutdownMonitor shutdownHook;
    
    public void loadAddons() {
        if (addons != null)
            throw new IllegalStateException("Addons already running.");
        
        addons = new LinkedHashMap<Class<? extends Addon>, Addon>();
        for(Addon addon: createOrderedAddons()) {
            addons.put(addon.getClass(), addon);
        }
        
        for(Addon addon: addons.values())
            addon.load();
        
        shutdownHook = new ShutdownMonitor();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
    
    private void stopAddons() {
        if (addons == null)
            throw new IllegalStateException("Addons not running.");
        if (!started)
            return;
        
        List<Addon> addonsReversed = new ArrayList<Addon>(addons.values());
        Collections.reverse(addonsReversed);
        
        for(Addon addon: addonsReversed)
            addon.stop();
        
        started = false;
    }
    
    public void readyAddons() {
        if (addons == null || !started)
            throw new IllegalStateException("Addons not running.");
        
        for(Addon addon: addons.values())
            addon.ready();
        
    }
    
    public void startAddons() {
        if (addons == null)
            throw new IllegalStateException("Addons not running.");
        if (started)
            throw new IllegalStateException("Addons already started.");
        
        for(Addon addon: addons.values())
            addon.start();
        
        started = true;
    }
    
    
    public <T extends Addon> T getAddonInstance(Class<T> clazz) {
        return (T) addons.get(clazz);
    }
    
    
    private List<Addon> createOrderedAddons() {
        
        ServiceLoader<Addon> loader = ServiceLoader.load(Addon.class);
        
        final Map<String,Addon> allProviders = new HashMap<String, Addon>();
        for(Addon provider: loader)
            allProviders.put(provider.getAddonId(), provider);
        
        final Map<String,MutableInt> orders = new HashMap<String, MutableInt>();
        for(String id: allProviders.keySet())
            orders.put(id, new MutableInt());
        
        boolean ordersChanged;
        do {
            ordersChanged = false;
            for(Map.Entry<String,Addon> entry: allProviders.entrySet()) {
                String id = entry.getKey();
                MutableInt myOrder = orders.get(id);
                
                for(String requiredId: entry.getValue().getRequiredAddons()) {
                    MutableInt other = orders.get(requiredId);
                    if (other.intValue() >= myOrder.intValue()) {
                        myOrder.setValue(other.intValue() + 1);
                        ordersChanged = true;
                    }
                }
                
                if (myOrder.intValue() > 1000) {
                    throw new IllegalStateException("Possible circular dependancy detected: " + id);
                }
            }
        } while(ordersChanged);

        List<String> ids = new ArrayList<String>(allProviders.keySet());
        Comparator<String> cmp = new Comparator<String>() {

            public int compare(String o1, String o2) {
                return orders.get(o1).compareTo(orders.get(o2));
            }
        };
        Collections.sort(ids, cmp);
        
        List<Addon> result = new ArrayList<Addon>(ids.size());
        for(String id: ids)
            result.add(allProviders.get(id));
        
        return result;
    }
    
    private class ShutdownMonitor extends Thread {
        public void run() {
            stopAddons();
        }
    }
}
