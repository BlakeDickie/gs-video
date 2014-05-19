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
package net.landora.video.properties.dvd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bdickie
 */
public class DVDDisk {

    private List<DVDTitle> titles;
    private String title;

    private String dvdDevice;

    /**
     * Creates a new instance of DVDDisk
     */
    public DVDDisk() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<DVDTitle> getTitles() {
        return titles;
    }

    public void setTitles( List<DVDTitle> titles ) {
        this.titles = titles;
    }

    public String getDvdDevice() {
        return dvdDevice;
    }

    public void setDvdDevice( String dvdDevice ) {
        this.dvdDevice = dvdDevice;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public List<DVDTitle> getFilteredTitles() {
        ArrayList<DVDTitle> titles = new ArrayList<DVDTitle>( getTitles().size() );
        for ( DVDTitle title : getTitles() ) {
            if ( title.getLength() >= FILTER_LENGTH ) {
                titles.add( title );
            }
        }

        return ( titles.isEmpty() ? getTitles() : titles );
    }

    private static final float FILTER_LENGTH = 300;
}
