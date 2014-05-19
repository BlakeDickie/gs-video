/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.tasks;

import net.landora.video.utils.UIUtils;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author bdickie
 */
public class TaskProgressImpl implements TaskProgress {

    public TaskProgressImpl() {
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport( this );

    public void addPropertyChangeListener( PropertyChangeListener listener ) {
        propertyChangeSupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener ) {
        propertyChangeSupport.removePropertyChangeListener( listener );
    }

    protected String name;
    public static final String PROP_NAME = "name";

    public String getName() {
        return name;
    }

    @Override
    public void setName( final String name ) {
        Runnable runnable = new Runnable() {

            public void run() {
                String oldName = TaskProgressImpl.this.name;
                TaskProgressImpl.this.name = name;
                propertyChangeSupport.firePropertyChange( PROP_NAME, oldName, name );
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    protected String message;
    public static final String PROP_MESSAGE = "message";

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage( final String message ) {
        Runnable runnable = new Runnable() {

            public void run() {
                String oldMessage = TaskProgressImpl.this.message;
                TaskProgressImpl.this.message = message;
                propertyChangeSupport.firePropertyChange( PROP_MESSAGE, oldMessage, message );
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    protected boolean determinate;
    public static final String PROP_DETERMINATE = "determinate";

    public boolean isDeterminate() {
        return determinate;
    }

    public void setDeterminate( boolean determinate ) {
        boolean oldDeterminate = this.determinate;
        this.determinate = determinate;
        propertyChangeSupport.firePropertyChange( PROP_DETERMINATE, oldDeterminate, determinate );
    }

    protected int progress;
    public static final String PROP_PROGRESS = "progress";

    public int getProgress() {
        return progress;
    }

    public void setProgress( int progress ) {
        double oldProgress = this.progress;
        this.progress = progress;
        propertyChangeSupport.firePropertyChange( PROP_PROGRESS, oldProgress, progress );
    }

    @Override
    public void startDeterminate( final String name, final long current, final long total ) {
        Runnable runnable = new Runnable() {

            public void run() {
                setName( name );
                progressDeterminate( current, total );
                start();
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    @Override
    public void startDeterminate( final String name, final double current, final double total ) {
        Runnable runnable = new Runnable() {

            public void run() {
                setName( name );
                progressDeterminate( current, total );
                start();
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    @Override
    public void startIndeterminate( final String name ) {
        Runnable runnable = new Runnable() {

            public void run() {
                setName( name );
                progressIndeterminate();
                start();
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    @Override
    public void progressDeterminate( final long current, final long total ) {
        Runnable runnable = new Runnable() {

            public void run() {
                setDeterminate( true );
                setProgress( (int) Math.round( 100 * (double) current / (double) total ) );
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    @Override
    public void progressDeterminate( final double current, final double total ) {
        Runnable runnable = new Runnable() {

            public void run() {
                setDeterminate( true );
                setProgress( (int) Math.round( 100 * (double) current / (double) total ) );
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    @Override
    public void progressIndeterminate() {
        Runnable runnable = new Runnable() {

            public void run() {
                setDeterminate( false );
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    @Override
    public void finished() {
        Runnable runnable = new Runnable() {

            public void run() {
                finishedImpl();
            }
        };
        UIUtils.invokeInSwingThread( runnable );
    }

    private void start() {
        TaskProgressManager.getInstance().fireTaskStarted( this );
    }

    private void finishedImpl() {
        TaskProgressManager.getInstance().fireTaskFinished( this );
    }
}
