/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
