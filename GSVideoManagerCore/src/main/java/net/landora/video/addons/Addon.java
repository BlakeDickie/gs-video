/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.addons;

import java.util.List;
import org.mortbay.jetty.servlet.Context;

/**
 *
 * @author bdickie
 */
public interface Addon {
    
    public String getAddonId();
    public String getAddonName();
    public List<String> getRequiredAddons();
    
    public void start();
    public void stop();
    public void ready();
    public void addServlets(Context serverContext);
}
