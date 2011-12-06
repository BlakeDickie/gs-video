/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser;

import java.io.File;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author bdickie
 */
public class FolderNode extends AbstractNode {
    public FolderNode(File dir, String customName) {
        super(createChildren(dir), Lookups.singleton(dir));
        
        if (customName != null)
            setName(customName);
        else
            setName(dir.getName());
        
        setDisplayName(getName());
    }
    
    private static Children createChildren(File dir) {
        return Children.create(new FSChildrenFactory(dir), false);
    }
}
