/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.tasks;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import net.landora.video.utils.UIUtils;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author bdickie
 */
public class TaskProgressImpl implements TaskProgress {

    public TaskProgressImpl() {
    }
    
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    protected String name;
    public static final String PROP_NAME = "name";

    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        Runnable runnable = new Runnable() {

            public void run() {
                String oldName = TaskProgressImpl.this.name;
                TaskProgressImpl.this.name = name;
                propertyChangeSupport.firePropertyChange(PROP_NAME, oldName, name);
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }

    protected String message;
    public static final String PROP_MESSAGE = "message";

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(final String message) {
        Runnable runnable = new Runnable() {

            public void run() {
                String oldMessage = TaskProgressImpl.this.message;
                TaskProgressImpl.this.message = message;
                propertyChangeSupport.firePropertyChange(PROP_MESSAGE, oldMessage, message);
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }

    protected boolean determinate;
    public static final String PROP_DETERMINATE = "determinate";

    public boolean isDeterminate() {
        return determinate;
    }

    public void setDeterminate(boolean determinate) {
        boolean oldDeterminate = this.determinate;
        this.determinate = determinate;
        propertyChangeSupport.firePropertyChange(PROP_DETERMINATE, oldDeterminate, determinate);
    }

    protected int progress;
    public static final String PROP_PROGRESS = "progress";

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        double oldProgress = this.progress;
        this.progress = progress;
        propertyChangeSupport.firePropertyChange(PROP_PROGRESS, oldProgress, progress);
    }

    
    @Override
    public void startDeterminate(final String name, final long current, final long total) {
        Runnable runnable = new Runnable() {

            public void run() {
                setName(name);
                progressDeterminate(current, total);
                start();
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    @Override
    public void startDeterminate(final String name, final double current, final double total) {
        Runnable runnable = new Runnable() {

            public void run() {
                setName(name);
                progressDeterminate(current, total);
                start();
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    @Override
    public void startIndeterminate(final String name) {
        Runnable runnable = new Runnable() {

            public void run() {
                setName(name);
                progressIndeterminate();
                start();
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    @Override
    public void progressDeterminate(final long current, final long total) {
        Runnable runnable = new Runnable() {

            public void run() {
                setDeterminate(true);
                setProgress((int)Math.round(100 * (double) current / (double) total));
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    @Override
    public void progressDeterminate(final double current, final double total) {
        Runnable runnable = new Runnable() {

            public void run() {
                setDeterminate(true);
                setProgress((int)Math.round(100 * (double) current / (double) total));
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    @Override
    public void progressIndeterminate() {
        Runnable runnable = new Runnable() {

            public void run() {
                setDeterminate(false);
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    @Override
    public void finished() {
        Runnable runnable = new Runnable() {

            public void run() {
                finishedImpl();
            }
        };
        UIUtils.invokeInSwingThread(runnable);
    }
    
    private void start() {
        TaskProgressManager.getInstance().fireTaskStarted(this);
    }

    
    private void finishedImpl() {
        TaskProgressManager.getInstance().fireTaskFinished(this);
    }
}
