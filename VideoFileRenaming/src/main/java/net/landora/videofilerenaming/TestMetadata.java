/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilerenaming;

import java.util.Map;
import net.landora.videoinfo.AbstractVideoMetadata;
import net.landora.videoinfo.ViewListManager;

/**
 *
 * @author bdickie
 */
public abstract class TestMetadata extends AbstractVideoMetadata {
    
    
    protected boolean adult;

    protected TestMetadata(boolean adult) {
        this.adult = adult;
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
    
    
    
}
