/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui.tree;

import java.util.List;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author bdickie
 */
public interface LazyTreeChildrenFactory {
    public List<? extends MutableTreeNode> getChildren(Object value);
}
