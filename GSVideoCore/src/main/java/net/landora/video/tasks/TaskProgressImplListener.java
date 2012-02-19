/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

import java.util.EventListener;

/**
 *
 * @author bdickie
 */
public interface TaskProgressImplListener extends EventListener {
    public void taskStarted(TaskProgressImplEvent evt);
    public void taskFinished(TaskProgressImplEvent evt);
}
