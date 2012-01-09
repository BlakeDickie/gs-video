/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

/**
 *
 * @author bdickie
 */
public class AnimeCategory implements java.io.Serializable {
    private int id;
    private AnimeCategory parentCategory;
    private boolean hentai;
    private String name;
    private String description;

    public AnimeCategory() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHentai() {
        return hentai;
    }

    public void setHentai(boolean hentai) {
        this.hentai = hentai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimeCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(AnimeCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeCategory other = (AnimeCategory) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }


}
