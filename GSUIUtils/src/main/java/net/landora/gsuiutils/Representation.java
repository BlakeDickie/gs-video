/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

/**
 *
 * @author bdickie
 */
public class Representation<T> {
    
    private String display;
    private T object;

    public Representation(String display, T object) {
        this.display = display;
        this.object = object;
    }

    public Representation(T object) {
        this(String.valueOf(object), object);
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return getDisplay();
    }
    
}
