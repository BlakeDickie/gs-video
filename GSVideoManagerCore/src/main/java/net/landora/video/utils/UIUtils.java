/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.utils;

import java.awt.EventQueue;
import java.util.Collection;
import java.util.Comparator;
import javax.swing.SwingUtilities;

/**
 *
 * @author bdickie
 */
public class UIUtils {
    
    public static final Comparator LEXICAL_SORTER = new LexicalSorter();
    public static final Comparator COMPARABLE_SORTER = new ComparableSorter();
    
    private static class LexicalSorter implements Comparator<Object> {

        public int compare(Object o1, Object o2) {
            if (o1 == o2)
                return 0;
            else if (o1 == null)
                return -1;
            else if (o2 == null)
                return 1;
            else
                return o1.toString().compareToIgnoreCase(o2.toString());
        }
        
    }
    
    private static class ComparableSorter<T extends Comparable> implements Comparator<T> {

        public int compare(T o1, T o2) {
            if (o1 == o2)
                return 0;
            else if (o1 == null)
                return -1;
            else if (o2 == null)
                return 1;
            else
                return o1.compareTo(o2);
        }
        
    }
    
    public static void invokeLaterInSwingThread(Runnable r) {
        if (EventQueue.isDispatchThread())
            r.run();
        else
            SwingUtilities.invokeLater(r);
    }
    
    public static <T> T select(Collection<T> collection) {
        if (collection == null || collection.isEmpty())
            return null;
        else
            return collection.iterator().next();
    }
    
}
