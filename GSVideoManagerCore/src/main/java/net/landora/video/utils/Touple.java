/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public class Touple<U,V> {

    private U first;
    private V second;

    public Touple() {
    }

    public Touple(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", first, second);
    }

    


}
