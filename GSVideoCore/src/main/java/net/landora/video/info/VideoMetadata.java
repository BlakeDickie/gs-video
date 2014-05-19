/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.info;

import java.util.Map;

/**
 *
 * @author bdickie
 */
public interface VideoMetadata {

    public boolean isSeries();

    public boolean isMultiSeasonSeries();

    public boolean isMovie();

    public Type getType();

    public byte[] getPosterImage();

    public void addExtraInformation( Map<String, String> addTo, boolean detailed );

    public Map<String, String> getAllInformation( boolean detailed );

    public ViewListManager getListManager();

    public String getTypeDescription();

    public boolean isAnime();

    public String getNfoUrl();

    public String getUniqueVideoId();

    public boolean isAdult();

    public static enum Type {

        Movie,
        SingleSeasonSeries,
        MultiSeasonSeries;
    }
}
