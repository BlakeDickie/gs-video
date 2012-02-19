/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import java.util.Map;
import net.landora.video.info.AbstractVideoMetadata;
import net.landora.video.info.ViewListManager;

/**
 *
 * @author bdickie
 */
public abstract class TestMetadata extends AbstractVideoMetadata {
    
    
    protected boolean adult;

    protected TestMetadata(boolean adult) {
        this.adult = adult;
    }

    public String getNfoUrl() {
        return null;
    }
    
    
    
    @Override
    public byte[] getPosterImage() {
        return null;
    }

    @Override
    protected void addExtraInformationImpl(Map<String, String> addTo) {
    }

    @Override
    public ViewListManager getListManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAdult() {
        return adult;
    }

    public String getUniqueVideoId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
}
