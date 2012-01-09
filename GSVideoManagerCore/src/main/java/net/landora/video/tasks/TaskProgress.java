/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

/**
 *
 * @author bdickie
 */
public interface TaskProgress {
    public void startDeterminate(String name, long current, long total);
    public void startDeterminate(String name, double current, double total);
    public void startIndeterminate(String name);
    
    public void progressDeterminate(long current, long total);
    public void progressDeterminate(double current, double total);
    public void progressIndeterminate();
    
    public void setName(String name);
    public void setMessage(String message);
    
    public void finished();
}
