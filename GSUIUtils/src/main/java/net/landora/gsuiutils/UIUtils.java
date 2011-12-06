/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.SwingUtilities;
import javax.swing.Action;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;

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
    
    public static Action[] getActions(Lookup context) {
        Collection<? extends Object> values = context.lookupAll(Object.class);
        Set<Class<?>> classes = new HashSet<Class<?>>();
        Set<String> actionPaths = new TreeSet<String>();
        
        for(Object obj: values) {
            classes.add(obj.getClass());
        }
        
        Set<Class<?>> doneClasses = new HashSet<Class<?>>();
        
        while(!classes.isEmpty()) {
            classes.removeAll(doneClasses);
            
            List<Class<?>> todo = new ArrayList<Class<?>>(classes);
            for(Class<?> c: todo) {
                doneClasses.add(c);
                
                if (c == null || c.equals(Object.class))
                    continue;
                
                classes.addAll(Arrays.asList(c.getInterfaces()));
                classes.add(c.getSuperclass());
                
                ContextActionObject cao = c.getAnnotation(ContextActionObject.class);
                if (cao != null) {
                    actionPaths.add(cao.value());
                }
            }
        }
        
        List<Action> actions = new ArrayList<Action>();
        for(String path: actionPaths)
            actions.addAll(Utilities.actionsForPath(path));
        
        
        
        return actions.toArray(new Action[actions.size()]);
    }
    
    public static void addContentObject(Object obj, Collection<Object> addTo) {
        if (obj == null)
            return;
        
        if (obj instanceof ContextProducer) {
            ((ContextProducer)obj).addContentObjects(addTo);
        } else
            addTo.add(obj);
    }
    
    public static Lookup createObjectLookup(Object obj) {
        List<Object> objects = new ArrayList<Object>();
        UIUtils.addContentObject(obj, objects);
        
        if (objects.size() == 1)
            return Lookups.singleton(objects.get(0));
        
        InstanceContent content = new InstanceContent();
        for(Object o: objects)
            content.add(o);
            
        return new AbstractLookup(content);
    }
}
