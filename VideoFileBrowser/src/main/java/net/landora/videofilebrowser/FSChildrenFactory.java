/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import net.landora.gsuiutils.UIUtils;

/**
 *
 * @author bdickie
 */
public class FSChildrenFactory extends ChildFactory<File> {

    private File rootFolder;

    public FSChildrenFactory(File rootFolder) {
        this.rootFolder = rootFolder;
    }
    
    @Override
    protected boolean createKeys(List<File> toPopulate) {
        File[] files = rootFolder.listFiles();
        if (files != null) {
            Arrays.sort(files, UIUtils.LEXICAL_SORTER);
            for(File file: files) {
                if (file.isDirectory())
                    toPopulate.add(file);
            }
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(File key) {
        return FSNodeFactory.createNode(key);
    }
    
    
    
    
}
