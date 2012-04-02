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


package net.landora.animeinfo.metadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.video.utils.Touple;

/**
 *
 * @author bdickie
 */
public enum AnimeFileNamePattern {
    //Amagami SS - 01 - Morishima Haruka - Chapter 1 Yearning.mkv

    NumberedBasic("(.+?) - ([STCPO]?\\d+)(?: - .*)?"),  //Amagami SS - 01 - Morishima Haruka - Chapter 1 Yearning.mkv
    NumberedBasic2("(.+) - ([STCPO]?\\d+)(?: - .*?)"),

    VolPattern("(.+)\\s+Vol\\.?(\\d+)(?:[ -].*)?"),
    Fullname("(.*+)")
            ;

    private Pattern pattern;
    private Integer nameGroup;
    private Integer numberGroup;

    private AnimeFileNamePattern(String pattern, int nameGroup, int numberGroup) {
        this(pattern);
        this.nameGroup = nameGroup;
        this.numberGroup = numberGroup;
    }

    private AnimeFileNamePattern(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    public Touple<String, String> attemptParsing(String filename) {
        Matcher m = pattern.matcher(filename);
        if (m.matches()) {
            

            Touple<String, String> result = new Touple<String, String>();
            if (nameGroup == null) {
                result.setFirst(m.group(1));
            } else
                result.setFirst(m.group(nameGroup));

            if (nameGroup != null && numberGroup > 0)
                result.setSecond(m.group(numberGroup));
            else if (m.groupCount() > 1)
                result.setSecond(m.group(2));
            
            return result;
        } else
            return null;
    }

}
