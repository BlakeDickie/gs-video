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

import java.io.File;
import java.util.Comparator;

public class FileSorter implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o1.isDirectory() != o2.isDirectory()) {
            if (o1.isDirectory()) {
                return -1;
            } else {
                return 1;
            }
        }

        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
