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
