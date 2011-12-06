/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

/**
 *
 * @author bdickie
 */
public class AnimeCategoryWeight implements java.io.Serializable {
    private AnimeStub anime;
    private AnimeCategory category;
    private int weight;

    public AnimeCategoryWeight() {
    }

    public AnimeStub getAnime() {
        return anime;
    }

    public void setAnime(AnimeStub anime) {
        this.anime = anime;
    }

    public AnimeCategory getCategory() {
        return category;
    }

    public void setCategory(AnimeCategory category) {
        this.category = category;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeCategoryWeight other = (AnimeCategoryWeight) obj;
        if (this.anime != other.anime && (this.anime == null || !this.anime.equals(other.anime))) {
            return false;
        }
        if (this.category != other.category && (this.category == null || !this.category.equals(other.category))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.anime != null ? this.anime.hashCode() : 0);
        hash = 73 * hash + (this.category != null ? this.category.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s [%d]", category.getName(), weight);
    }

    
}
