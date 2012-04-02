/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.landora.video.utils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class EventBus {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor(new NamedThreadFactory("EventBus", true));
    
    public EventBus() {
        handlers = new ArrayList<BusHandlerReference>();
    }
    
    private List<BusHandlerReference> handlers;
    
    public void addHandlersWeak(Object handler) {
        addHandlders(handler, true);
    }
    
    public void addHandlers(Object handler) {
        addHandlders(handler, false);
    }
    
    public synchronized void removeHandlers(Object handler) {
        Iterator<BusHandlerReference> i = handlers.iterator();
        while(i.hasNext()) {
            BusHandlerReference ref = i.next();
            Object obj = ref.getObj();
            if (obj == null || obj == handler) {
                i.remove();
                continue;
            }
        }
    }
    
    private synchronized <T> void addHandlders(T handler, boolean weak) {
        for(Method m: handler.getClass().getMethods()) {
            BusReciever ann = m.getAnnotation(BusReciever.class);
            if (ann == null)
                continue;
            
            Class<?>[] parameterTypes = m.getParameterTypes();
            if (parameterTypes.length != 1)
                throw new IllegalArgumentException("Bus handler method does not have single argument: " + m.toString());
            
            BusHandlerReference ref = new BusHandlerReference();
            ref.setM(m);
            ref.setEventClass(parameterTypes[0]);
            ref.setAwt(ann.awt());
            if (weak)
                ref.setRef(new WeakReference<T>(handler));
            else
                ref.setObj(handler);
            
            handlers.add(ref);
        }
    }
    
    public synchronized void fireEvent(Object event) {
        Iterator<BusHandlerReference> i = handlers.iterator();
        while(i.hasNext()) {
            BusHandlerReference ref = i.next();
            Object obj = ref.getObj();
            if (obj == null) {
                i.remove();
                continue;
            }
            
            if (!ref.getEventClass().isAssignableFrom(event.getClass()))
                continue;
            
            Runnable run;
            if (ref.isAwt())
                run = new AwtRun(obj, ref.getM(), event);
            else
                run = new EventRun(obj, ref.getM(), event);
            
            executor.submit(run);
        }
    }
    
    private class EventRun implements Runnable {

        private Object obj;
        private Method m;
        private Object event;

        public EventRun(Object obj, Method m, Object event) {
            this.obj = obj;
            this.m = m;
            this.event = event;
        }
        
        @Override
        public void run() {
            try {
                m.invoke(obj, event);
            } catch (Exception e) {
                LoggerFactory.getLogger(obj.getClass()).error("Error running EventBus handler.", e);
            }
        }
        
    }
    
    private class AwtRun implements Runnable {

        private Object obj;
        private Method m;
        private Object event;

        public AwtRun(Object obj, Method m, Object event) {
            this.obj = obj;
            this.m = m;
            this.event = event;
        }
        
        @Override
        public void run() {
            UIUtils.invokeLaterInSwingThread(new EventRun(obj, m, event));
        }
        
    }
    
    private class BusHandlerReference {
        private Method m;
        private Object obj;
        private Reference<?> ref;
        private Class<?> eventClass;
        private boolean awt;

        public BusHandlerReference() {
        }

        public Class<?> getEventClass() {
            return eventClass;
        }

        public void setEventClass(Class<?> eventClass) {
            this.eventClass = eventClass;
        }

        public Method getM() {
            return m;
        }

        public void setM(Method m) {
            this.m = m;
        }

        public Object getObj() {
            if (obj != null)
                return obj;
            else if (ref != null)
                return ref.get();
            else
                return null;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Reference<?> getRef() {
            return ref;
        }

        public void setRef(Reference<?> ref) {
            this.ref = ref;
        }

        public boolean isAwt() {
            return awt;
        }

        public void setAwt(boolean awt) {
            this.awt = awt;
        }
        
        
    }
}
