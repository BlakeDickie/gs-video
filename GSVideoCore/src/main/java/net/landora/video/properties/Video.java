/*
 * Video.java
 *
 * Created on June 1, 2007, 11:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bdickie
 */
public class Video {
    
    private List<VideoStream> videoStreams;
    private List<AudioStream> audioStreams;
    private List<SubtitleStream> subtitleStreams;
    private List<Chapter> chapters;
    private File file;
    
    private float length;
    
    /** Creates a new instance of Video */
    public Video() {
        setVideoStreams(new ArrayList<VideoStream>());
        setAudioStreams(new ArrayList<AudioStream>());
        setSubtitleStreams(new ArrayList<SubtitleStream>());
        setChapters(new ArrayList<Chapter>());
    }

    public List<VideoStream> getVideoStreams() {
        return videoStreams;
    }

    public void setVideoStreams(List<VideoStream> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public List<AudioStream> getAudioStreams() {
        return audioStreams;
    }

    public void setAudioStreams(List<AudioStream> audioStreams) {
        this.audioStreams = audioStreams;
    }

    public List<SubtitleStream> getSubtitleStreams() {
        return subtitleStreams;
    }

    public void setSubtitleStreams(List<SubtitleStream> subtitleStreams) {
        this.subtitleStreams = subtitleStreams;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
    
    
    @Override
    public String toString() {
        return file.getName();
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        if (file != null) {
            String name = file.getName();
            int lastDot = name.lastIndexOf('.');
            if (lastDot <= 0)
                return name;
            else
                return name.substring(0, lastDot);
        } else
            return "Unknown";
    }
    
//    public Map<Parameter,String> getSelectionParameters() {
//        Map<Parameter,String> result = new HashMap<Parameter,String>();
//        if (this instanceof DVDTitle) {
//            result.put(Parameter.Source, "DVD");
//        } else {
//            result.put(Parameter.Source, "File");
//        }
//        
//        result.put(Parameter.SubtitleTrackNumber, String.valueOf(subtitleStreams.size()));
//        result.put(Parameter.AudioTrackNumber, String.valueOf(audioStreams.size()));
//        
//        LinkedHashSet<String> languages = new LinkedHashSet<String>();
//        for(AudioStream stream: audioStreams)
//            languages.add(stream.getLanguageDescription());
//        result.put(Parameter.AudioLanguages, CollectionsUtil.join(languages, ","));
//        
//        languages = new LinkedHashSet<String>();
//        for(SubtitleStream stream: subtitleStreams)
//            languages.add(stream.getLanguageDescription());
//        result.put(Parameter.SubtitleLanguages, CollectionsUtil.join(languages, ","));
//        
//        return result;
//    }

    public boolean isDVD() {
        return false;
    }
}
