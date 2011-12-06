/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

/**
 *
 * @author bdickie
 */
public class AnimeRelation implements java.io.Serializable {

    private AnimeStub anime;
    private int relatedAnimeId;
    private RelationType relationType;

    public AnimeRelation() {
    }

    public AnimeStub getAnime() {
        return anime;
    }

    public void setAnime(AnimeStub anime) {
        this.anime = anime;
    }

    public int getRelatedAnimeId() {
        return relatedAnimeId;
    }

    public void setRelatedAnimeId(int relatedAnimeId) {
        this.relatedAnimeId = relatedAnimeId;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", relationType.getName(), relatedAnimeId);
    }

    public int getRelationTypeId() {
        return relationType.getType();
    }

    public void setRelationTypeId(int id) {
        setRelationType(RelationType.lookupType(id));
    }
    
}
