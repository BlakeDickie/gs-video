/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
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
