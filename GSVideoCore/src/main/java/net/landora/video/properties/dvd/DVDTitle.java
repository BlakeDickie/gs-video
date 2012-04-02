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


package net.landora.video.properties.dvd;

import net.landora.video.properties.Video;

/**
 *
 * @author bdickie
 */
public class DVDTitle extends Video {
    
    private int number;
    private DVDDisk disk;
    
    /** Creates a new instance of DVDTitle */
    public DVDTitle(DVDDisk disk) {
        this.disk = disk;
    }

    public DVDDisk getDisk() {
        return disk;
    }

    

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DVD Title " + number;
    }

    @Override
    public boolean isDVD() {
        return true;
    }

    @Override
    public String getName() {
        return toString();
    }



}
