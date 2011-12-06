/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

import java.util.Comparator;

/**
 *
 * @author bdickie
 */
public class RepresentationComparator<T> implements Comparator<Representation<T>> {

    private Comparator<T> comp;

    public RepresentationComparator(Comparator<T> comp) {
        this.comp = comp;
    }    
    
    public int compare(Representation<T> o1, Representation<T> o2) {
        if (o1 == o2)
            return 0;
        else if (o1 == null)
            return -1;
        else if (o2 == null)
            return 1;
        else
            return comp.compare(o1.getObject(), o2.getObject());
    }
    
}
