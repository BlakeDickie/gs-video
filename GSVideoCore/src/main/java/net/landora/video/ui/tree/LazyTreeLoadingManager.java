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

import java.util.List;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;
import net.landora.video.utils.UIUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class LazyTreeLoadingManager {
    
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static LazyTreeLoadingManager instance = new LazyTreeLoadingManager();
    }

    public static LazyTreeLoadingManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private LazyTreeLoadingManager() {
        
    }
    
    public void setup(JTree tree) {
        tree.addTreeWillExpandListener(new ExpandListener((DefaultTreeModel)tree.getModel()));
    }
    
    DefaultMutableTreeNode createLoadingNode() {
        return new LoadingNode();
    }
    
    public boolean isLoadingNode(TreeNode node) {
        return node instanceof LoadingNode;
    }
    
    private void nodeExpanding(LazyTreeNode node, DefaultTreeModel model) {
        if (node.isLoaded())
            return;
        
        if (node.isLoadInBackground()) {
            BackgroundLoader loader = new BackgroundLoader(node, model);
            loader.execute();
        } else {
            List<? extends MutableTreeNode> children = getChildren(node);
            applyChildren(node, model, children);
        }
    }
    
    private List<? extends MutableTreeNode> getChildren(LazyTreeNode node) {
        return node.getFactory().getChildren(UIUtils.unwrap(node.getUserObject()));
    }
    
    private void applyChildren(LazyTreeNode node, DefaultTreeModel model, List<? extends MutableTreeNode> children) {
        if (children != null) {
            for(MutableTreeNode child: children) {
                model.insertNodeInto(child, node, node.getChildCount());
            }
        }
        
        model.removeNodeFromParent((MutableTreeNode)node.getFirstChild());
    }
    
    private class ExpandListener implements TreeWillExpandListener {
        
        private DefaultTreeModel model;

        public ExpandListener(DefaultTreeModel model) {
            this.model = model;
        }
        
        

        public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
            TreePath path = event.getPath();
            Object lastPathComponent = path.getLastPathComponent();
            if (lastPathComponent instanceof LazyTreeNode) {
                    LazyTreeNode lazyNode = (LazyTreeNode) lastPathComponent;
                    nodeExpanding(lazyNode, model);
            }
        }

        public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
            
        }
        
    }
    
    private class LoadingNode extends DefaultMutableTreeNode {

        public LoadingNode() {
            super("Loading...");
        }
        
    }
    
    private class BackgroundLoader extends SwingWorker<List<? extends MutableTreeNode>, Object> {

        private LazyTreeNode node;
        private DefaultTreeModel model;

        public BackgroundLoader(LazyTreeNode node, DefaultTreeModel model) {
            this.node = node;
            this.model = model;
        }
        
        
        
        @Override
        protected List<? extends MutableTreeNode> doInBackground() throws Exception {
            return getChildren(node);
        }

        @Override
        protected void done() {
            try {
                applyChildren(node, model, get());
            } catch (Exception ex) {
                LoggerFactory.getLogger(LazyTreeLoadingManager.class).error("Error loading node children.", ex);
            }
        }
        
        
        
    }
}
