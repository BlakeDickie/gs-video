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

import net.landora.video.properties.SubtitleFormat;
import net.landora.video.properties.SubtitleStream;

/**
 *
 * @author bdickie
 */
public class DVDSubtitle extends SubtitleStream {

    private String content;

    /**
     * Creates a new instance of DVDSubtitle
     */
    public DVDSubtitle() {
        setFormat( SubtitleFormat.VobSub );
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    @Override
    public String toString() {
        if ( content != null && !content.isEmpty() && !content.equalsIgnoreCase( "Undefined" ) ) {
            return content + " (" + super.toString() + ")";
        } else {
            return super.toString();
        }
    }

}
