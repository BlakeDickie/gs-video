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

package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public class ComparisionUtils {
    
    public static boolean equals(Object s1, Object s2) {
        if (s1 == s2)
            return true;
        else if (s1 == null || s2 == null)
            return false;
        else
            return s1.equals(s2);
    }
    
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2)
            return true;
        else if (s1 == null || s2 == null)
            return false;
        else
            return s1.equalsIgnoreCase(s2);
    }
    
}
