/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.properties;

import net.landora.video.properties.Chapter;

/**
 *
 * @author bdickie
 */
public class NamedChapter extends Chapter {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String toString() {
        if (title != null && !title.equals("")) {
            return super.toString() + " (" + title + ")";
        } else
            return super.toString();
    }
    
}
