/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.ui;

import javax.swing.JPanel;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author bdickie
 */
public class InfoPanel extends JPanel {

    public InfoPanel() {
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

    public boolean supportsContext(MultiValueMap context) {
        return false;
    }
    
    public void loadContext(MultiValueMap context) {
        
    }
    
    public void clearCurrentContext() {
        
    }
}
