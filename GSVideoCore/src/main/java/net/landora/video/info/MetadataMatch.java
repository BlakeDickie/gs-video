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

package net.landora.video.info;

/**
 *
 * @author bdickie
 */
public class MetadataMatch {
    
    private MatchType type;
    private String metadataId;
    private String uniqueVideoId;
    private MetadataProvider metadataProvider;

    public MetadataMatch(MatchType type, String metadataId, String uniqueVideoId) {
        this.type = type;
        this.metadataId = metadataId;
        this.uniqueVideoId = uniqueVideoId;
    }

    public String getUniqueVideoId() {
        return uniqueVideoId;
    }

    public void setUniqueVideoId(String uniqueVideoId) {
        this.uniqueVideoId = uniqueVideoId;
    }
    
    

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    public void setMetadataProvider(MetadataProvider metadataProvider) {
        this.metadataProvider = metadataProvider;
    }

    
    public MatchType getType() {
        return type;
    }

    public void setType(MatchType type) {
        this.type = type;
    }
    
    
    
    public static enum MatchType {
        HashMatch,
        PreviousMatch,
        FilenameMatch;
    };
}
