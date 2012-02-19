/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

import java.util.EventObject;

/**
 *
 * @author bdickie
 */
public class TaskProgressImplEvent extends EventObject {

    public TaskProgressImplEvent(TaskProgressImpl source) {
        super(source);
    }

    @Override
    public TaskProgressImpl getSource() {
        return (TaskProgressImpl)super.getSource();
    }
    
    
    
}
