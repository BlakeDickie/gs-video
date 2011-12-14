/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public class ComparisionUtils {
    
    public static boolean equals(Object s1, Object s2) {
        if (s1 == s2)
            return true;
        else if (s1 == null || s2 == null)
            return false;
        else
            return s1.equals(s2);
    }
    
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2)
            return true;
        else if (s1 == null || s2 == null)
            return false;
        else
            return s1.equalsIgnoreCase(s2);
    }
    
}
