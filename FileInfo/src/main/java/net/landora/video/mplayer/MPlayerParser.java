/*
 * MKVParser.java
 *
 * Created on June 1, 2007, 11:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package net.landora.video.mplayer;

import net.landora.video.file_info.FileInfoPreferences;
import net.landora.video.properties.SubtitleStream;
import net.landora.video.properties.AudioFormat;
import net.landora.video.properties.Video;
import net.landora.video.properties.VideoStream;
import net.landora.video.properties.SubtitleFormat;
import net.landora.video.properties.VideoFormat;
import net.landora.video.properties.AudioStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.video.programs.CommonPrograms;
import net.landora.video.programs.ProgramsAddon;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author bdickie
 */
public class MPlayerParser {

    public static final Pattern dataPattern = Pattern.compile("^ID_([^=]+)=([^:\\n]+)$", Pattern.MULTILINE);
    private static final Pattern durationPattern = Pattern.compile("^\\s*(\\d+\\.\\d+)s");
    private static final Pattern segmentUIDPattern = Pattern.compile("\\s*0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s*", Pattern.CASE_INSENSITIVE);

    public static Video parseFile(File f) {
        return new MPlayerParser().handleFile(f);
    }
    
    private Set<Integer> videoTracks;
    private Set<Integer> audioTracks;
    private Set<Integer> subtitleTracks;
    
    private Video file;

    public Video handleFile(File f) {
        try {
            MPlayerInfoParse info = mplayerIdentify(f, null, null, null);
            detectTracks(info);
            
            file = new Video();
            file.setFile(f);
            
            String length = info.getSingle("LENGTH");
            if (length != null)
                file.setLength(Float.parseFloat(length));
            
            while(!videoTracks.isEmpty() || !audioTracks.isEmpty() ||
                    !subtitleTracks.isEmpty()) {
                doPass(f);
            }
            
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    private void doPass(File f) throws IOException, InterruptedException {
        Integer video = selectAndRemove(videoTracks);
        Integer audio = selectAndRemove(audioTracks);
        Integer subtitle = selectAndRemove(subtitleTracks);
        
        MPlayerInfoParse info = mplayerIdentify(f, video, audio, subtitle);
        parseVideo(info, video);
        parseAudio(info, audio);
        parseSubtitle(info, subtitle);
    }
    
    private void parseVideo(MPlayerInfoParse info, Integer videoId) {
        if (videoId == null)
            return;
        VideoStream stream = new VideoStream();
        stream.setFormat(VideoFormat.getFormat(info.getSingle("VIDEO_CODEC")));
        stream.setAspect(info.getSingle("VIDEO_ASPECT"));
        stream.setPixelHeight(Integer.parseInt(info.getSingle("VIDEO_HEIGHT")));
        stream.setPixelWidth(Integer.parseInt(info.getSingle("VIDEO_WIDTH")));
        stream.setFps(info.getSingle("VIDEO_FPS"));
        
        String language = info.getSingle("VID_" + videoId + "_LANG");
        stream.setLanguage(parseLanguage(language));
        
        file.getVideoStreams().add(stream);
    }
    
    private void parseAudio(MPlayerInfoParse info, Integer audioId) {
        if (audioId == null)
            return;
        AudioStream stream = new AudioStream();
        stream.setFormat(AudioFormat.getFormat(info.getSingle("AUDIO_CODEC")));
        stream.setChannels(Integer.parseInt(info.getSingle("AUDIO_NCH")));
        String language = info.getSingle("AID_" + audioId + "_LANG");
        stream.setLanguage(parseLanguage(language));
        
        stream.setStreamId(audioId);
        
        file.getAudioStreams().add(stream);
    }
    
    private void parseSubtitle(MPlayerInfoParse info, Integer subtitleId) {
        if (subtitleId == null)
            return;
        SubtitleStream stream = new SubtitleStream();
        stream.setFormat(SubtitleFormat.Unknown);
        
        String language = info.getSingle("SID_" + subtitleId + "_LANG");
        stream.setLanguage(parseLanguage(language));
        stream.setStreamId(subtitleId);
        
        file.getSubtitleStreams().add(stream);
    }
    
    
    private static Pattern languagePattern = Pattern.compile("\\[([^\\]]+)\\]$");
    
    private static String parseLanguage(String lang) {
        if (lang == null)
            return null;
        
        Matcher m = languagePattern.matcher(lang);
        if (m.find())
            return m.group(1);
        else
            return lang;
    }
    
    private Integer selectAndRemove(Collection<Integer> collection) {
        Iterator<Integer> i = collection.iterator();
        if (i.hasNext()) {
            Integer result = i.next();
            i.remove();
            return result;
        } else
            return null;
    }
    
    private void detectTracks(MPlayerInfoParse info) {
        videoTracks = new HashSet<Integer>();
        audioTracks = new HashSet<Integer>();
        subtitleTracks = new HashSet<Integer>();
        
        copyTracks(info.getList("AUDIO_ID"), audioTracks);
        copyTracks(info.getList("VIDEO_ID"), videoTracks);
        copyTracks(info.getList("SUBTITLE_ID"), subtitleTracks);
    }
    
    private void copyTracks(List<String> tracks, Collection<Integer> saveTo) {
        for(String track: tracks)
            saveTo.add(new Integer(track));
    }

    private MPlayerInfoParse mplayerIdentify(File f, Integer videoId, Integer audioId, Integer subtitleId) throws IOException, InterruptedException {
        //mplayer -frames 0 -msglevel identify=9 "$1" -vo null -ao null 2>/dev/null | grep "^ID"
        List<String> cmd = new ArrayList<String>();
        cmd.add(ProgramsAddon.getInstance().getConfiguredPath(CommonPrograms.MPLAYER));
        cmd.add("-frames");
        cmd.add("0");
        cmd.add("-msglevel");
        cmd.add("identify=9");
        cmd.add("-vo");
        cmd.add("null");
        cmd.add("-ao");
        cmd.add("null");

        if (videoId != null) {
            cmd.add("-vid");
            cmd.add(videoId.toString());
        }

        if (audioId != null) {
            cmd.add("-aid");
            cmd.add(audioId.toString());
        }

        if (subtitleId != null) {
            cmd.add("-sid");
            cmd.add(subtitleId.toString());
        }

        cmd.add(f.getAbsolutePath());

        ProcessBuilder process = new ProcessBuilder(cmd);
        process.redirectErrorStream(true);
        Process p = process.start();


        StringWriter buffer = new StringWriter();
        IOUtils.copy(p.getInputStream(), buffer);
        p.waitFor();

        Matcher infoMatcher = dataPattern.matcher(buffer.toString());
        MPlayerInfoParse result = new MPlayerInfoParse();
        while(infoMatcher.find()) {
            result.add(infoMatcher.group(1), infoMatcher.group(2));
        }
        
        return result;
    }

    public static void main(String[] args) {
//        System.out.println(parseFile(new File("/media/quon/storage/blakes/Hentai/Princess 69/Princess 69 Lesson 1.ogm")));
        System.out.println(parseFile(new File("/media/quon/storage/Videos/Anime/Madlax/mk2/Madlax20.mkv")));
        
    }
}
