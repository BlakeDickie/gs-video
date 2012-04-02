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

package net.landora.video.ui.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author bdickie
 */
public class LazyTreeNode extends DefaultMutableTreeNode {

    private boolean loadInBackground = true;
    private LazyTreeChildrenFactory factory;
    
    public LazyTreeNode(Object userObject, LazyTreeChildrenFactory factory) {
        super(userObject);
        this.factory = factory;
        
        add(LazyTreeLoadingManager.getInstance().createLoadingNode());
    }

    public boolean isLoadInBackground() {
        return loadInBackground;
    }

    public void setLoadInBackground(boolean loadInBackground) {
        this.loadInBackground = loadInBackground;
    }

    public LazyTreeChildrenFactory getFactory() {
        return factory;
    }

    public void setFactory(LazyTreeChildrenFactory factory) {
        this.factory = factory;
    }
    
    public boolean isLoaded() {
        return getChildCount() == 0 || !LazyTreeLoadingManager.getInstance().isLoadingNode(getFirstChild());
    }
    
}
