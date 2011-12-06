/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo;

/**
 *
 * @author bdickie
 */
public class MetadataMatch {
    
    private MatchType type;
    private String metadataId;
    private MetadataProvider metadataProvider;

    public MetadataMatch(MatchType type, String metadataId) {
        this.type = type;
        this.metadataId = metadataId;
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
