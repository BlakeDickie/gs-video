/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    public boolean sameSeries(SeriesMetadata other) {
        return false;
    }
    
    
    
}
