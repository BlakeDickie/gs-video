/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

/**
 *
 * @author bdickie
 */
public class AnimeNameLookupSummary implements java.io.Serializable {
    private int animeId;
    private String nameMain;
    private String nameEnglish;

    public AnimeNameLookupSummary() {
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }


    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getDisplayName() {
        if (nameEnglish != null)
            return nameEnglish;
        else
            return nameMain;
    }

    public String getNameMain() {
        return nameMain;
    }

    public void setNameMain(String nameMain) {
        this.nameMain = nameMain;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeNameLookupSummary other = (AnimeNameLookupSummary) obj;
        if (this.animeId != other.animeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }



    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.animeId;
        return hash;
    }
    
}
