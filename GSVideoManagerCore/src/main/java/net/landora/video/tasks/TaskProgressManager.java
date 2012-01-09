/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import net.landora.video.VideoManagerApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class TaskProgressManager {
    
    private Logger log = LoggerFactory.getLogger(getClass()); 
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static TaskProgressManager instance = new TaskProgressManager();
    }

    public static TaskProgressManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    
    private TaskProgressManager() {
        
    }
    
    public TaskProgress newTaskProgress() {
        if (VideoManagerApp.getInstance().isGuiMode())
            return new TaskProgressImpl();
        else
            return new NullTaskProgress();
    }
    
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    
    private EventListenerList listenerList = new EventListenerList();
    
    public void addTaskProgressImplListener(TaskProgressImplListener l) {
        listenerList.add(TaskProgressImplListener.class, l);
    }
    
    public void removeTaskProgressImplListener(TaskProgressImplListener l) {
        listenerList.remove(TaskProgressImplListener.class, l);
    }
    
    void fireTaskStarted(TaskProgressImpl task) {
        TaskProgressImplEvent e = null;
        for(TaskProgressImplListener l: listenerList.getListeners(TaskProgressImplListener.class)) {
            if (e == null)
                e = new TaskProgressImplEvent(task);
            
            l.taskStarted(e);
        }
        
        currentTasks.add(task);
        checkForMainTask();
    } 
    
    void fireTaskFinished(TaskProgressImpl task) {
        currentTasks.remove(task);
        checkForMainTask();
        
        TaskProgressImplEvent e = null;
        for(TaskProgressImplListener l: listenerList.getListeners(TaskProgressImplListener.class)) {
            if (e == null)
                e = new TaskProgressImplEvent(task);
            
            l.taskFinished(e);
        }
    } 
    
    
    
    
    
    protected TaskProgressImpl currentMainTask;
    public static final String PROP_CURRENTMAINTASK = "currentMainTask";

    public TaskProgressImpl getCurrentMainTask() {
        return currentMainTask;
    }

    private void setCurrentMainTask(TaskProgressImpl currentMainTask) {
        TaskProgressImpl oldCurrentMainTask = this.currentMainTask;
        this.currentMainTask = currentMainTask;
        propertyChangeSupport.firePropertyChange(PROP_CURRENTMAINTASK, oldCurrentMainTask, currentMainTask);
    }
    
    
    private List<TaskProgressImpl> currentTasks = new ArrayList<TaskProgressImpl>();
    
    private void checkForMainTask() {
        if (currentMainTask != null && currentTasks.contains(currentMainTask))
            return;
        
        if (currentTasks.isEmpty())
            setCurrentMainTask(null);
        else
            setCurrentMainTask(currentTasks.get(0));
    }
}
