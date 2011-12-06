/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser;

import java.io.File;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author bdickie
 */
public class FSNodeFactory {
    
    public static Node createNode(File file) {
        return createNode(file, null);
    }
    
    public static Node createNode(File file, String customName) {
        AbstractNode node;
        if (file.isDirectory()) {
            node = new FolderNode(file, customName);
        } else {
            node = new AbstractNode(Children.LEAF, Lookups.singleton(file));
            if (customName != null)
                node.setName(customName);
        }
        node.setName(file.getName());
        
        return node;
    }
}
