/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 *
 * @author bdickie
 */
public class GSAbstractNode extends AbstractNode {

    public GSAbstractNode(Children children, Object lookupObject) {
        super(children, UIUtils.createObjectLookup(lookupObject));
    }
    
    public GSAbstractNode(Children children, Lookup lookup) {
        super(children, lookup);
    }

    public GSAbstractNode(Children children) {
        super(children);
    }

    @Override
    public Action[] getActions(boolean context) {
        return UIUtils.getActions(getLookup());
    }
    
    
}
