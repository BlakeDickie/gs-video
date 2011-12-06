/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.gsuiutils;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import org.openide.explorer.ExplorerManager;

/**
 *
 * @author bdickie
 */
public class ExplorerManagerWrapper extends JComponent implements ExplorerManager.Provider {
    private ExplorerManager mng;
    
    public ExplorerManagerWrapper(JComponent comp, ExplorerManager mng) {
        this.mng = mng;
        
        setLayout(new BorderLayout());
        add(comp, BorderLayout.CENTER);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return mng;
    }
}
