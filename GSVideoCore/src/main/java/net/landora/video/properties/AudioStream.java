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

/**
 *
 * @author bdickie
 */
public class AudioStream {

    private AudioFormat format = AudioFormat.Unknown;
    private int channels;
    private String language;
    private int streamId;

    public AudioFormat getFormat() {
        return format;
    }

    public void setFormat( AudioFormat format ) {
        this.format = format;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels( int channels ) {
        this.channels = channels;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage( String language ) {
        this.language = language;
    }

    public int getStreamId() {
        return streamId;
    }

    public void setStreamId( int streamId ) {
        this.streamId = streamId;
    }

    public String getLanguageDescription() {
        String lang = getLanguage();
        if ( lang == null ) {
            return "";
        }

        String result = Languages.lookup( lang );
        if ( result == null ) {
            return lang;
        } else {
            return result;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String lang = getLanguageDescription();
        if ( !lang.isEmpty() ) {
            result.append( lang );
            result.append( " " );
        }

        result.append( getChannels() );
        result.append( "Ch " );

        result.append( getFormat() );

        return result.toString();
    }
}
