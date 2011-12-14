/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.landora.video.info.SeriesMetadata;

/**
 *
 * @author bdickie
 */
public class TestSeriesMetadata extends TestMetadata implements SeriesMetadata {

    public TestSeriesMetadata(boolean adult) {
        super(adult);
    }

    @Override
    public String getSeriesName() {
        return (adult ? "Test Adult Series" : "Test Series");
    }

    @Override
    public String getEpisodeName() {
        return "Episode Name";
    }

    @Override
    public String getEpisodeNumber() {
        return "01";
    }

    @Override
    protected void addContentObjectsImpl(Collection<Object> addTo) {
        
    }
    
    
}
