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


package net.landora.video.properties;

import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public enum VideoFormat {

    H264(".*h264.*", ".*avc.*"),
    MPEG4(".*xvid.*", ".*divx.*", ".*mpeg4.*"),
    MPEG2(".*mpeg1?2.*"),
    WMV(".*wmv.*"),
    QuickTime("(?:ff)?qt.*", "ffrpza"),
    RealVideo("(?:ff)?rv.*"),
    Unknown;
    
    private Pattern[] aliases;

    private VideoFormat(String... aliases) {
        this.aliases = new Pattern[aliases.length + 1];
        for (int i = 0; i < aliases.length; i++) {
            this.aliases[i] = Pattern.compile(aliases[i], Pattern.CASE_INSENSITIVE);
        }
        this.aliases[aliases.length] = Pattern.compile(Pattern.quote(name()), Pattern.CASE_INSENSITIVE);
    }

    public synchronized static VideoFormat getFormat(String formatDesc) {
        for (VideoFormat format : values()) {
            for (Pattern alias : format.aliases) {
                if (alias.matcher(formatDesc).matches()) {
                    return format;
                }
            }
        }

        LoggerFactory.getLogger(VideoFormat.class).warn("Unable to find video format:" + formatDesc);
        return Unknown;

    }
}
