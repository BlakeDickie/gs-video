/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.manager;

import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public abstract class ContentPanel<S> extends JPanel {

    public ContentPanel() {
    }
    
    protected List<? extends S> currentContext;
    public static final String PROP_CURRENTCONTEXT = "currentContext";

    public List<? extends S> getCurrentContext() {
        return currentContext;
    }

    protected void setCurrentContext(List<? extends S> currentContext) {
        List<? extends S> oldCurrentContext = this.currentContext;
        this.currentContext = currentContext;
        firePropertyChange(PROP_CURRENTCONTEXT, oldCurrentContext, currentContext);
    }

    protected String title;
    public static final String PROP_TITLE = "title";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String oldTitle = this.title;
        this.title = title;
        firePropertyChange(PROP_TITLE, oldTitle, title);
    }

    protected Priority priority = Priority.Minor;
    public static final String PROP_PRIORITY = "priority";

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        Priority oldPriority = this.priority;
        this.priority = priority;
        firePropertyChange(PROP_PRIORITY, oldPriority, priority);
    }

    public abstract void loadView();
    
    protected boolean maybePopup(MouseEvent e) {
        if (!e.isPopupTrigger())
            return false;
        
        e.consume();
        
        JPopupMenu menu = UIAddon.getInstance().createPopupMenu(getCurrentContext());
        if (menu != null)
            menu.show(e.getComponent(), e.getX(), e.getY());
        
        return true;
    }
    
    public enum Priority {
        Main(0),
        Minor(1);
        
        private int order;

        private Priority(int order) {
            this.order = order;
        }
        
        
    }
}
