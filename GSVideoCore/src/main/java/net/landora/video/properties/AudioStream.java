/*
 * AudioStream.java
 *
 * Created on June 1, 2007, 11:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
    
    public void setFormat(AudioFormat format) {
        this.format = format;
    }
    
    public int getChannels() {
        return channels;
    }
    
    public void setChannels(int channels) {
        this.channels = channels;
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
        
        result.append(getChannels());
        result.append("Ch ");
        
        
        result.append(getFormat());
        
        return result.toString();
    }
}
