/*
 * VideoStream.java
 *
 * Created on June 1, 2007, 11:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
    
    /** Creates a new instance of VideoStream */
    public VideoStream() {
    }

    public VideoFormat getFormat() {
        return format;
    }

    public void setFormat(VideoFormat format) {
        this.format = format;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    
    public int getPixelHeight() {
        return pixelHeight;
    }

    public void setPixelHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public void setPixelWidth(int pixelWidth) {
        this.pixelWidth = pixelWidth;
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

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }
    
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
//        String lang = getLanguageDescription();
//        if (!lang.equals("")) {
//            result.append(lang);
//            result.append(" ");
//        }        
        
        result.append(getFormat());
        
        result.append(" Video");
        
        return result.toString();
    }
}
