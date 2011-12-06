/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import net.landora.videoinfo.VideoInfoFileUtils;

/**
 *
 * @author bdickie
 */
public class AnimeNameLookup implements java.io.Serializable {
    private AnimeNameLookupSummary anime;
    private String name;
    private String type;
    private String language;

    public AnimeNameLookup() {
    }

    public AnimeNameLookupSummary getAnime() {
        return anime;
    }

    public void setAnime(AnimeNameLookupSummary anime) {
        this.anime = anime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameEscaped() {
        return VideoInfoFileUtils.filterInvalidFilenameCharacters(getName());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeNameLookup other = (AnimeNameLookup) obj;
        if (this.anime != other.anime && (this.anime == null || this.anime.getAnimeId() != other.anime.getAnimeId())) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        if ((this.language == null) ? (other.language != null) : !this.language.equals(other.language)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.anime != null ? this.anime.hashCode() : 0);
        hash = 19 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 19 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 19 * hash + (this.language != null ? this.language.hashCode() : 0);
        return hash;
    }

}
