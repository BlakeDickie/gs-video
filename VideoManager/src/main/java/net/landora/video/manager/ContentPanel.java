/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.manager;

import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import net.landora.video.ui.UIAddon;

/**
 *
 * @author bdickie
 */
public class ContentPanel<S> extends JComponent {

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

    public void loadView() {
        throw new IllegalStateException("Implementations of ContentPanel must implement loadView()");
    }

    protected boolean maybePopup(MouseEvent e) {
        if (!e.isPopupTrigger()) {
            return false;
        }

        e.consume();

        JPopupMenu menu = UIAddon.getInstance().createPopupMenu(getCurrentContext());
        if (menu != null) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }

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
