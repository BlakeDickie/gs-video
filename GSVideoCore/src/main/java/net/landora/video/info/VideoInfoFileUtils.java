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
package net.landora.video.info;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bdickie
 */
public class VideoInfoFileUtils {

    private static final Map<String, String> filenameReplaceRules = new HashMap<String, String>();
    private static final Pattern filenameReplaceRulesPattern;

    static {
        filenameReplaceRules.put( "`", "'" );
        filenameReplaceRules.put( "\"", "" );
        filenameReplaceRules.put( "<", "" );
        filenameReplaceRules.put( ">", "" );
        filenameReplaceRules.put( "|", "" );
        filenameReplaceRules.put( "/", "" );
        filenameReplaceRules.put( "\\", "" );
        filenameReplaceRules.put( "?", "" );
        filenameReplaceRules.put( "*", "" );
//        filenameReplaceRules.put(":", " - ");
        StringBuilder b = new StringBuilder();
        b.append( "(" );
        for ( String search : filenameReplaceRules.keySet() ) {
            if ( b.length() > 1 ) {
                b.append( "|" );
            }
            b.append( "(?:" );
            b.append( Pattern.quote( search ) );
            b.append( ")" );
        }
        b.append( ")" );
        b.append( "|( ?: ?)" );
        filenameReplaceRulesPattern = Pattern.compile( b.toString() );
    }

    public static String filterInvalidFilenameCharacters( String filename ) {
        StringBuffer buffer = new StringBuffer( filename.length() + 5 );

        Matcher m = filenameReplaceRulesPattern.matcher( filename );
        while ( m.find() ) {
            String basicReplace = m.group( 1 );
            String replace = m.group();
            if ( basicReplace != null ) {
                replace = filenameReplaceRules.get( basicReplace );
            } else if ( m.group( 2 ) != null ) {
                replace = " ~ ";
            }

            m.appendReplacement( buffer, Matcher.quoteReplacement( replace ) );
        }
        m.appendTail( buffer );

        return buffer.toString();
    }

}
