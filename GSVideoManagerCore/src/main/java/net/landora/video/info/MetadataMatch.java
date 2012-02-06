/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
