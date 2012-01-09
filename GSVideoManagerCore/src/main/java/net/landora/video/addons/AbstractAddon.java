/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.addons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mortbay.jetty.servlet.Context;

/**
 *
 * @author bdickie
 */
public abstract class AbstractAddon implements Addon {

    private List<String> requiredAddons;
    private String id;
    private String name;
    private List<Runnable> shutdownCalls;
    
    protected AbstractAddon(String id, String name, String...requiredAddons) {
        this.requiredAddons = new ArrayList<String>(Arrays.asList(requiredAddons));
        this.id = id;
        this.name = name;
        
         shutdownCalls = new ArrayList<Runnable>();
    }
    
    public List<String> getRequiredAddons() {
        return requiredAddons;
    }

    public String getAddonId() {
        return id;
    }

    public String getAddonName() {
        return name;
    }

    public void start() {
        
    }

    public void addServlets(Context serverContext) {
        
    }

    public void ready() {
        
    }

    public void stop() {
        for(Runnable runnable: shutdownCalls)
            runnable.run();
    }
    
    public void addShutdownCall(Runnable runnable) {
        shutdownCalls.add(runnable);
    }
    
    public void removeShutdownCall(Runnable runnable) {
        shutdownCalls.remove(runnable);
    }
    
}
