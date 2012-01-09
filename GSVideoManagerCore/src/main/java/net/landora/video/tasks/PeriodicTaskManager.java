/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.landora.video.utils.Touple;
import net.landora.video.utils.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class PeriodicTaskManager {
    
    private Logger log = LoggerFactory.getLogger(getClass()); 
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static PeriodicTaskManager instance = new PeriodicTaskManager();
    }

    public static PeriodicTaskManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private PeriodicTaskManager() {
        tasksState = new ArrayList<TaskState>();
        tasksToStart = new ArrayList<TaskState>();
        
        new Thread("Periodic Tasks") {

            @Override
            public void run() {
                taskThreadRun();
            }
            
        }.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                synchronized(waitLock) {
                    shutdown = true;
                    waitLock.notifyAll();
                }
            }
        });
    }
    
    private List<TaskState> tasksState;
    private final Object waitLock = new Object();
    private boolean shutdown;
    
    private List<TaskState> tasksToStart;
    private List<Touple<TaskState,NBTask<?,?>>> runningTasks = new ArrayList<Touple<TaskState, NBTask<?, ?>>>();
    
    public synchronized void addPeriodicTask(PeriodicTask task) {
        TaskState state = new TaskState(task);
        Long lastRun = task.getRunPeriodPreferenceValue();
        state.lastRun = (lastRun == null ? 0 : lastRun);
        
        scheduleTask(state);
    }
    
    private void scheduleTask(TaskState state) {
        synchronized(waitLock) {
            
            state.nextRun = state.lastRun + state.task.getRunPeriod();
            
            tasksToStart.add(state);
            
            waitLock.notifyAll();
        }
    }
    
    public void taskFinished(NBTask<?,?> task) {
        synchronized(waitLock) {
            waitLock.notifyAll();
        }
    }
    
    private void startTask(TaskState state) {
        synchronized(waitLock) {
            tasksToStart.remove(state);
            
            long currentTime = System.currentTimeMillis();
            state.lastRun = currentTime;
            state.task.setRunPeriodPreferenceValue(currentTime);
            
            final NBTask<?, ?> task = state.task.createTask();
            runningTasks.add(new Touple<TaskState, NBTask<?, ?>>(state, task));
            Runnable runnable = new Runnable() {

                public void run() {
                    task.startTask();
                }
            };
            UIUtils.invokeLaterInSwingThread(runnable);
        }
    }
    
    private void taskDone(TaskState state) {
        synchronized(waitLock) {
            long currentTime = System.currentTimeMillis();
            state.lastRun = currentTime;
            state.task.setRunPeriodPreferenceValue(currentTime);
            
            scheduleTask(state);
            
        }
    }
    
    private void taskThreadRun() {
        synchronized(waitLock) {
            while(!shutdown) {
                try {
                    long nextRun = Long.MAX_VALUE;
                    List<TaskState> readyTasks = new ArrayList<TaskState>();
                    long currentTime = System.currentTimeMillis();
                    
                    for(TaskState state: tasksToStart) {
                        nextRun = Math.min(state.nextRun, nextRun);
                        if (state.nextRun <= currentTime)
                            readyTasks.add(state);
                    }
                    
                    if (!readyTasks.isEmpty()) {
                        for(TaskState state: readyTasks)
                            startTask(state);
                        
                        continue;
                    }
                    
                    boolean stopped = false;
                    Iterator<Touple<TaskState, NBTask<?, ?>>> i = runningTasks.iterator();
                    while(i.hasNext()) {
                        Touple<TaskState, NBTask<?, ?>> running = i.next();
                        if (running.getSecond().isFinished()) {
                            i.remove();
                            taskDone(running.getFirst());
                            stopped = true;
                        }
                    }
                    if (stopped)
                        continue;
                    
                    
                    try {
                        if (nextRun == Long.MAX_VALUE)
                            waitLock.wait();
                        else
                            waitLock.wait(Math.max(1, nextRun - System.currentTimeMillis()));
                    } catch (InterruptedException ignore) { }
                } catch (Exception e) {
                    log.error("Error running periodic tasks.", e);
                }
                
            }
        }
    }
    
    
    private class TaskState {
        private PeriodicTask task;
        private long lastRun;
        private long nextRun;

        public TaskState(PeriodicTask task) {
            this.task = task;
        }
        
        
    }
}
