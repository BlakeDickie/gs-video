/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui;

import java.awt.event.ActionEvent;
import java.util.List;

/**
 *
 * @author bdickie
 */
public abstract class UIAction<T> {
    
    private Class<T> clazz;
    private String name;
    private boolean multipuleObjectSupport;

    public UIAction(Class<T> clazz, String name, boolean multipuleObjectSupport) {
        this.clazz = clazz;
        this.name = name;
        this.multipuleObjectSupport = multipuleObjectSupport;
    }

    public UIAction(Class<T> clazz, String name) {
        this(clazz, name, false);
    }

    
    
    public Class<T> getRequiredClass() {
        return clazz;
    }
    
    public abstract void actionPerformed(ActionEvent evt, List<T> objects);
    
    public String getName() {
        return name;
    }

    public boolean isMultipuleObjectSupport() {
        return multipuleObjectSupport;
    }
    
    
}
