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
