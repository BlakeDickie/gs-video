/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

/**
 *
 * @author bdickie
 */
public class TaskCompletedEvent {
    private NBTask<?,?> task;

    public TaskCompletedEvent(NBTask<?, ?> task) {
        this.task = task;
    }
    
    public NBTask<?, ?> getTask() {
        return task;
    }
    
    public boolean isTaskOf(Class<? extends NBTask> clazz) {
        return clazz.isAssignableFrom(task.getClass());
    }
    
}
