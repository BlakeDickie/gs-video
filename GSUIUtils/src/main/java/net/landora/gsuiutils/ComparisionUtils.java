/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

/**
 *
 * @author bdickie
 */
public class ComparisionUtils {
    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2)
            return true;
        else if (o1 == null || o2 == null)
            return false;
        else
            return o1.equals(o2);
    }
}
