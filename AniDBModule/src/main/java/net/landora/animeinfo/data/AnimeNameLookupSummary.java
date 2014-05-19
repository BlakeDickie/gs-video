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
        if (nameEnglish != null) {
            return nameEnglish;
        } else {
            return nameMain;
        }
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
