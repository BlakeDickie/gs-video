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

package net.landora.video.addons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public abstract class AbstractAddon implements Addon {
    
    protected Logger log = LoggerFactory.getLogger(getClass());

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
    
    @Override
    public List<String> getRequiredAddons() {
        return requiredAddons;
    }

    @Override
    public String getAddonId() {
        return id;
    }

    @Override
    public String getAddonName() {
        return name;
    }

    @Override
    public void load() {
        
    }
    
    @Override
    public void start() {
        
    }

    @Override
    public void ready() {
        
    }

    @Override
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
