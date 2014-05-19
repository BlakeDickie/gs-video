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
package net.landora.video.properties;

import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;

/**
 *
 * @author bdickie
 */
public enum AudioFormat {

    AC3( 1, ".*ac3.*" ),
    AAC( "faad", ".*aac.*" ),
    MP3( ".*mp3.*" ),
    MP2( "ffmp2" ),
    DTS( 2, ".*dca.*", ".*dts.*" ),
    Vorbis( ".*VORBIS.*" ),
    WMA( "ffwmav\\d*", "wma.*", "divx" ),
    RealAudio( "(?:ff)?ra.*" ),
    QuickTime( ".*qdm.*", "qclp", "qt.*" ),
    PCM( ".*pcm.*" ),
    FLAC( ".*flac.*" ),
    Unknown;

    private Pattern[] aliases;
    private int priority;

    private AudioFormat( String... aliases ) {
        this( 0, aliases );
    }

    private AudioFormat( int priority, String... aliases ) {
        this.priority = priority;
        this.aliases = new Pattern[aliases.length + 1];
        for ( int i = 0; i < aliases.length; i++ ) {
            this.aliases[i] = Pattern.compile( aliases[i], Pattern.CASE_INSENSITIVE );
        }
        this.aliases[aliases.length] = Pattern.compile( Pattern.quote( name() ), Pattern.CASE_INSENSITIVE );
    }

    public synchronized static AudioFormat getFormat( String formatDesc ) {
        for ( AudioFormat format : values() ) {
            for ( Pattern alias : format.aliases ) {
                if ( alias.matcher( formatDesc ).matches() ) {
                    return format;
                }
            }
        }

        LoggerFactory.getLogger( AudioFormat.class ).warn( "Unable to find audio format:" + formatDesc );
        return Unknown;

    }

    public int getPriority() {
        return priority;
    }
}
