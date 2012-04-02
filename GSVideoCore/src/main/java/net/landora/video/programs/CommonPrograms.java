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

package net.landora.video.programs;

import java.util.Collections;

/**
 *
 * @author bdickie
 */
public class CommonPrograms {
    public static final Program MPLAYER = new Program("core.mplayer", "MPlayer", "mplayer", Collections.EMPTY_LIST);
    public static final Program MEDIAINFO = new Program("core.mediainfo", "MediaInfo", "mediainfo", Collections.EMPTY_LIST);
    public static final Program LSDVD = new Program("core.lsdvd", "lsdvd", "lsdvd", null, Collections.singletonList("-V"));
    public static final Program MKVINFO = new Program("core.mkvinfo", "mkvinfo", "mkvinfo", Collections.singletonList("-V"));
}
