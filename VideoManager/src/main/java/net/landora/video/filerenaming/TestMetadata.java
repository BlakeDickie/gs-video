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
    protected void addExtraInformationImpl(Map<String, String> addTo, boolean detailed) {
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
