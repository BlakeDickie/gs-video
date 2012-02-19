/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public class Representation<T> {
    
    private T value;
    
    private String toString;

    public Representation(String toString, T value) {
        this.value = value;
        this.toString = toString;
    }

    public Representation(T value) {
        this(String.valueOf(value), value);
    }

    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return toString;
    }
    
}
