/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;
import org.apache.commons.io.input.ProxyInputStream;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public abstract class NBTask<R,I> implements Cancellable {

    private static final ExecutorService executor;
    static {
        executor = Executors.newCachedThreadPool();
    }
    
    private ProgressHandle progressHandle;
    private String name;
    private Future<?> future;
    private boolean finished;

    public NBTask(String name) {
        this.name = name;
    }
    
    public void startTask() {
        synchronized(awtLock) {
            future = executor.submit(new RunTask());
        }
    }
    
    protected boolean isCancellable() {
        return false;
    }
    
    private List<I> awtArgs;
    private final Object awtLock = new Object();
    
    protected final void passToAWT(I...args) {
        synchronized(awtLock) {
            if (awtArgs == null) {
                awtArgs = new ArrayList<I>();
                SwingUtilities.invokeLater(new AWTRunner());
            }
            awtArgs.addAll(Arrays.asList(args));
        }
        
    }
    
    private class AWTRunner implements Runnable {

        @Override
        public void run() {
            List<I> args;
            synchronized(awtLock) {
                args = awtArgs;
                awtArgs = null;
                
                if (future.isCancelled())
                    return;
            }
            doInAWT(args);
        }
        
    }
    
    protected void doInAWT(List<I> passedArgs) {
    }
    
    protected abstract R doInBackground() throws Throwable;
    
    protected abstract void success(R result);
    
    protected void failure(Throwable t) {
        LoggerFactory.getLogger(getClass()).error("Error running background task: " + getClass().getSimpleName(), t);
    }
    
    protected void cancelled() {
        
    }
    
    protected void finished() {
        
    }

    protected void switchToIndeterminate() {
        progressHandle.switchToIndeterminate();
    }

    protected void switchToDeterminate(int workunits, long estimate) {
        progressHandle.switchToDeterminate(workunits, estimate);
    }

    protected void switchToDeterminate(int workunits) {
        progressHandle.switchToDeterminate(workunits);
    }

    protected void suspend(String message) {
        progressHandle.suspend(message);
    }

    boolean started = false;
    
    protected void start(int workunits, long estimate) {
        progressHandle.start(workunits, estimate);
        started = true;
    }

    protected void start(int workunits) {
        progressHandle.start(workunits);
        started = true;
    }

    protected void start() {
        progressHandle.start();
        started = true;
    }

    protected void setInitialDelay(int millis) {
        progressHandle.setInitialDelay(millis);
    }

    protected void setDisplayName(String newDisplayName) {
        progressHandle.setDisplayName(newDisplayName);
    }

    protected void progress(String message, int workunit) {
        progressHandle.progress(message, workunit);
    }

    protected void progress(String message) {
        progressHandle.progress(message);
    }

    protected void progress(int workunit) {
        progressHandle.progress(workunit);
    }

    @Override
    public final boolean cancel() {
        synchronized(awtLock) {
            if (future != null)
                return future.cancel(true);
            else
                return false;
        }
        
    }
    
    private void taskDone() {
        finished();
        progressHandle.finish();
        finished = true;
        PeriodicTaskManager.getInstance().taskFinished(this);
    }

    public boolean isFinished() {
        return finished;
    }
    
    
    private class RunTask implements Runnable {
        
        @Override
        public void run() {
            synchronized(awtLock) {
                if (isCancellable())
                    progressHandle = ProgressHandleFactory.createHandle(name, NBTask.this);
                else
                    progressHandle = ProgressHandleFactory.createHandle(name);
            }

            R result = null;
            Throwable t = null;
            try {
                result = doInBackground();
            } catch (Throwable e) {
                t = e;
            }

            Runnable r;
            if (future.isCancelled()) {
                r = new CancelledRunnable();
            } else if (t != null) {
                r = new FailureRunnable(t);
            } else {
                r = new SuccessRunnable(result);
            }

            SwingUtilities.invokeLater(r);

        }
    }
    
    private class FailureRunnable implements Runnable {

        private Throwable t;

        public FailureRunnable(Throwable t) {
            this.t = t;
        }
        
        
        @Override
        public void run() {
            failure(t);
            taskDone();
        }
        
    }
    
    private class SuccessRunnable implements Runnable {

        private R t;

        public SuccessRunnable(R t) {
            this.t = t;
        }
        
        
        @Override
        public void run() {
            success(t);
            taskDone();
        }
        
    }
    
    private class CancelledRunnable implements Runnable {

        public CancelledRunnable() {
            
        }
        
        
        @Override
        public void run() {
            cancelled();
            taskDone();
        }
        
    }
    
    protected class NBTaskProgressInputStream extends ProxyInputStream {

        private long maxSize;
        private long progress;
        private double scale;
        
        public NBTaskProgressInputStream(InputStream in, long maxSize) {
            super(in);
            
            this.maxSize = maxSize;
            progress = 0;
            
            if (maxSize > Integer.MAX_VALUE) {
                scale = (double)maxSize / (double)Integer.MAX_VALUE;
            } else
                scale = 1;
            
            if (started) {
                switchToDeterminate((int)Math.round(maxSize / scale));
            } else
                start((int)Math.round(maxSize / scale));
        }

        @Override
        public int read() throws IOException {
            int result = super.read();
            if (result >= 0)
                increaseCount(1);
            return result;
        }

        @Override
        public int read(byte[] bts) throws IOException {
            int result = super.read(bts);
            increaseCount(result);
            return result;
        }

        @Override
        public int read(byte[] bts, int off, int len) throws IOException {
            int result = super.read(bts, off, len);
            increaseCount(result);
            return result;
        }
        
        private void increaseCount(int count) {
            progress += count;
            progress((int)Math.round(progress / scale));
        }
        
    }
}
