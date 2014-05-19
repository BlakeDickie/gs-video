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
public class VideoStream {

    private VideoFormat format = VideoFormat.Unknown;
    private String aspect;
    private String language;
    private int pixelWidth;
    private int pixelHeight;
    private String fps;

    /**
     * Creates a new instance of VideoStream
     */
    public VideoStream() {
    }

    public VideoFormat getFormat() {
        return format;
    }

    public void setFormat( VideoFormat format ) {
        this.format = format;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect( String aspect ) {
        this.aspect = aspect;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage( String language ) {
        this.language = language;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

    public void setPixelHeight( int pixelHeight ) {
        this.pixelHeight = pixelHeight;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public void setPixelWidth( int pixelWidth ) {
        this.pixelWidth = pixelWidth;
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

    public String getFps() {
        return fps;
    }

    public void setFps( String fps ) {
        this.fps = fps;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
//        String lang = getLanguageDescription();
//        if (!lang.equals("")) {
//            result.append(lang);
//            result.append(" ");
//        }        

        result.append( getFormat() );

        result.append( " Video" );

        return result.toString();
    }
}
