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

import net.landora.video.info.ViewListState.DiskState;

/**
 *
 * @author bdickie
 */
public enum AnimeListState {

    Unknown(0, DiskState.Unknown),
    HDD(1, DiskState.HardDisk),
    CD(2, DiskState.DVD),
    Deleted(3, DiskState.Deleted);

//    0 - unknown - state is unknown or the user doesn't want to provide this information
// 1 - on hdd - the file is stored on hdd (but is not shared)
// 2 - on cd - the file is stored on cd
// 3 - deleted - the file has been deleted or is not available for other reasons (i.e. reencoded)
    private int stateId;
    private String name;
    private DiskState diskState;

    private AnimeListState(int stateId, String name, DiskState diskState) {
        this.stateId = stateId;
        this.name = name;
        this.diskState = diskState;
    }

    private AnimeListState(int stateId, DiskState diskState) {
        this.stateId = stateId;
        this.diskState = diskState;
        StringBuilder buffer = new StringBuilder();
        name = name();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && java.lang.Character.isUpperCase(c)) {
                buffer.append(" ");
            }
            buffer.append(c);
        }
        name = buffer.toString();
    }

    public String getName() {
        return name;
    }

    public int getStateId() {
        return stateId;
    }

    public static AnimeListState lookupType(int id) {

        for (AnimeListState type : values()) {
            if (type.getStateId() == id) {
                return type;
            }
        }
        return null;
    }

    public static AnimeListState lookupDiskState(DiskState state) {
        if (state == null) {
            return Unknown;
        }

        for (AnimeListState type : values()) {
            if (type.getDiskState() == state) {
                return type;
            }
        }
        return Unknown;
    }

    public DiskState getDiskState() {
        return diskState;
    }

    @Override
    public String toString() {
        return getName();
    }
}
