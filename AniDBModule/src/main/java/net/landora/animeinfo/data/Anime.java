/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import java.util.List;

/**
 *
 * @author bdickie
 */
public class Anime extends AnimeStub implements java.io.Serializable {

    private List<AnimeName> names;
    private List<AnimeCategoryWeight> categories;
    private List<AnimeRelation> relations;

    public Anime() {
        
    }

    public List<AnimeName> getNames() {
        return names;
    }

    public void setNames(List<AnimeName> names) {
        this.names = names;
    }

    public List<AnimeCategoryWeight> getCategories() {
        return categories;
    }

    public void setCategories(List<AnimeCategoryWeight> categories) {
        this.categories = categories;
    }

    public List<AnimeRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<AnimeRelation> relations) {
        this.relations = relations;
    }

    
}
