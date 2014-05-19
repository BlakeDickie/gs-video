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
package net.landora.video.profile;

import java.util.Comparator;

/**
 *
 * @author bdickie
 */
public class ProfileSorter implements Comparator<RunProfile> {

    private int getPriority( RunProfile p ) {
        if ( p.isManager() ) {
            return 0;
        } else if ( p.isVideo() ) {
            return 1;
        } else {
            return 2;
        }
    }

    public int compare( RunProfile o1, RunProfile o2 ) {
        int cmp = getPriority( o1 ) - getPriority( o2 );
        if ( cmp != 0 ) {
            return cmp;
        }

        return o1.getProfileName().compareToIgnoreCase( o2.getProfileName() );
    }

}
