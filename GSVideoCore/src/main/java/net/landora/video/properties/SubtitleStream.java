/*
 * Subtitle.java
 *
 * Created on June 1, 2007, 11:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author bdickie
 */
public class SubtitleStream {
    private SubtitleFormat format = SubtitleFormat.Unknown;
    private String language;
    private int streamId;
    
    /** Creates a new instance of Subtitle */
    public SubtitleStream() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStreamId() {
        return streamId;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public SubtitleFormat getFormat() {
        return format;
    }

    public void setFormat(SubtitleFormat format) {
        this.format = format;
    }
    
    public String getLanguageDescription() {
        String lang = getLanguage();
        if (lang == null)
            return "";
        
        String result = Languages.lookup(lang);
        if (result == null)
            return lang;
        else
            return result;
    }
    
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        String lang = getLanguageDescription();
        if (!lang.equals("")) {
            result.append(lang);
            result.append(" ");
        }        
        
        result.append(getFormat());
        
        return result.toString();
    }

    public final boolean isNone() {
        return this == NONE_SUBTITLE;
    }

    public static final SubtitleStream NONE_SUBTITLE = new NoneSubtitle();

    private static class NoneSubtitle extends SubtitleStream {

        @Override
        public String toString() {
            return "None";
        }


    }
}
