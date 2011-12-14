/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.filerenaming;

import java.util.Collection;
import net.landora.video.info.MultiSeasonSeriesMetadata;

/**
 *
 * @author bdickie
 */
public class TestMultiSeasonSeriesMetadata extends TestMetadata implements MultiSeasonSeriesMetadata {

    public TestMultiSeasonSeriesMetadata(boolean adult) {
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
        return "S1E01";
    }

    @Override
    public int getSeasonNumber() {
        return 1;
    }

    @Override
    public int getEpisodeInSeason() {
        return 01;
    }

    @Override
    protected void addContentObjectsImpl(Collection<Object> addTo) {
        
    }
}
