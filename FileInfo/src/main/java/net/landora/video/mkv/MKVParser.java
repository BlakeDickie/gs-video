/*
 * MKVParser.java
 *
 * Created on June 1, 2007, 11:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package net.landora.video.mkv;

import net.landora.video.file_info.FileInfoPreferences;
import net.landora.video.properties.NamedChapter;
import net.landora.video.properties.Video;
import java.io.File;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.video.programs.CommonPrograms;
import net.landora.video.programs.ProgramsAddon;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author bdickie
 */
public class MKVParser {

    public static final Pattern dataPattern = Pattern.compile("^((?:\\| *)?)\\+ ([^:\n]+)(?:: (.*))?$", Pattern.MULTILINE);
    private static final Pattern durationPattern = Pattern.compile("^\\s*(\\d+\\.\\d+)s");
    private static final Pattern segmentUIDPattern = Pattern.compile("\\s*0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s+0x([a-f0-9]{2})\\s*", Pattern.CASE_INSENSITIVE);

    public static Video parseFile(File f) {
        return new MKVParser().handleFile(f);
    }
    private MKVParserState state;
    private MKVFile file;
    private MKVVideo curVideo;
    private MKVAudio curAudio;
    private MKVSubtitle curSubtitle;
    private MKVAttachment curAttachment;
    private NamedChapter curChapter;
    private MKVStream curStream;
    private int trackNum;
    private long trackUID;
    private String field;
    private String value;
    private int depth;
    private int chapterCount;
    private int audioCount;
    private int subtitleCount;
    
    private boolean vfwVideo;

    public Video handleFile(File f) {

        try {
            ProcessBuilder process = new ProcessBuilder(ProgramsAddon.getInstance().getConfiguredPath(CommonPrograms.MKVINFO), f.getAbsolutePath());
            process.redirectErrorStream(true);
            Process p = process.start();


            StringWriter buffer = new StringWriter();
            IOUtils.copy(p.getInputStream(), buffer);
            p.waitFor();
            
            String reply = buffer.toString();
            buffer = null;
            
            if (reply.contains("No segment/level 0 element found."))
                return null;

            Matcher mkvInfoMatcher = dataPattern.matcher(reply);

            file = new MKVFile();
            file.setFile(f);
            state = MKVParserState.Other;

            while (mkvInfoMatcher.find()) {
                depth = mkvInfoMatcher.group(1).length();
                field = mkvInfoMatcher.group(2);
                value = mkvInfoMatcher.group(3);

                switch (state) {
                    case Segment_Header:
                        handleSegmentHeader();
                        break;
                    case UnknownTrack:
                        handleUnknownTrack();
                        break;
                    case VideoTrack:
                        handleVideo();

                        break;
                    case AudioTrack:
                        handleAudo();

                        break;
                    case SubtitleTrack:
                        handleGeneralStreamFields();

                        break;
                    case Attachment:
                        handleAttachment();
                        break;
                        
                    case Chapter:
                        handleChapter();
                        break;
                        
                    case Other:
                    default:
                        handleStateless();
                }
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Pattern FOURCC_PATTERN = Pattern.compile("FourCC\\s+([^, ]+)", Pattern.CASE_INSENSITIVE);
    private static Pattern FPS_PATTERN = Pattern.compile("(\\d+\\.\\d+)\\s*fps", Pattern.CASE_INSENSITIVE);
    
    private void handleVideo() {
        if (field.equalsIgnoreCase("Pixel width")) {
            curVideo.setPixelWidth(Integer.parseInt(value));
        } else if (field.equalsIgnoreCase("Pixel height")) {
            curVideo.setPixelHeight(Integer.parseInt(value));
        } else if (field.equalsIgnoreCase("Display width")) {
            curVideo.setDisplayWidth(Integer.parseInt(value));
        } else if (field.equalsIgnoreCase("Display height")) {
            curVideo.setDisplayHeight(Integer.parseInt(value));
        } else if (field.equalsIgnoreCase("Interlaced")) {
            curVideo.setInterlaced(value.equals("1"));
        } else if (field.equalsIgnoreCase("Default duration")) {
            Matcher m = FPS_PATTERN.matcher(value);
            if (m.find())
                curVideo.setFps(m.group(1));
        } else if (vfwVideo && field.startsWith("CodecPrivate")) {
            Matcher m = FOURCC_PATTERN.matcher(field +" " + value);
            if (m.find())
                curVideo.setFormat(m.group(1));
        } else
            handleGeneralStreamFields();
    }
    
    private void handleAudo() {
        if (field.equalsIgnoreCase("Channels")) {
            curAudio.setChannels(Integer.parseInt(value));
        } else
            handleGeneralStreamFields();
    }

    private void handleGeneralStreamFields() {
        if (field.equalsIgnoreCase("Track number")) {
            trackNum = Integer.parseInt(value);
            curStream.setTrackId(trackNum);
        } else if (field.equalsIgnoreCase("Track UID")) {
            trackUID = Long.parseLong(value);
            curStream.setUid(trackUID);
        } else if (field.equalsIgnoreCase("A track")) {
            curStream = null;
            curVideo = null;
            curSubtitle = null;
            curAudio = null;
            state = MKVParserState.UnknownTrack;
        } else if (field.equalsIgnoreCase("Attachments") ||
                field.equalsIgnoreCase("Chapters")) {
            curStream = null;
            curVideo = null;
            curSubtitle = null;
            curAudio = null;
            state = MKVParserState.Other;
        } else if (field.equalsIgnoreCase("Default flag")) {
            curStream.setDefaultTrack(value.equals("1"));
        } else if (field.equalsIgnoreCase("Forced flag")) {
            curStream.setForcedTrack(value.equals("1"));
        } else if (field.equalsIgnoreCase("Codec ID")) {
            if (curStream instanceof MKVVideo && value.equalsIgnoreCase("V_MS/VFW/FOURCC")) {
                // Special handling for when the format is specified with FOURCC.
                vfwVideo = true;
            } else
                curStream.setFormat(value);
        } else if (field.equalsIgnoreCase("Language")) {
            curStream.setLanguage(value);
        } else if (field.equalsIgnoreCase("Name")) {
            curStream.setName(value);
        } else
            handleStateless();

    }
    
    private void handleAttachment() {
        if (field.equalsIgnoreCase("File name")) {
            curAttachment.setFilename(value);
        } else if (field.equalsIgnoreCase("Mime type")) {
            curAttachment.setMimeType(value);
        } else if (field.equalsIgnoreCase("File data, size")) {
            curAttachment.setSize(Long.parseLong(value));
        } else if (field.equalsIgnoreCase("File UID")) {
            curAttachment.setUid(Long.parseLong(value));
        } else if (field.equalsIgnoreCase("File description")) {
            curAttachment.setDescription(value);
        } else
            handleStateless();
    }
    
    private void handleChapter() {
        if (field.equalsIgnoreCase("ChapterString")) {
            curChapter.setTitle(value);
        } else if (field.equalsIgnoreCase("ChapterTimeStart")) {
            curChapter.setStartTime(parseChapterTime(value));
        } else if (field.equalsIgnoreCase("ChapterTimeEnd")) {
            float endTime = parseChapterTime(value);
            curChapter.setLength(endTime - curChapter.getStartTime());
        } else
            handleStateless();
    }

    private void handleStateless() {
        if (field.equalsIgnoreCase("Segment information")) {
            state = MKVParserState.Segment_Header;
        } else if (field.equalsIgnoreCase("A track")) {
            state = MKVParserState.UnknownTrack;
        } else if (field.equalsIgnoreCase("Attached")) {
            state = MKVParserState.Attachment;
            curAttachment = new MKVAttachment();
            file.getAttachments().add(curAttachment);
        } else if (field.equalsIgnoreCase("ChapterAtom")) {
            state = MKVParserState.Chapter;
            curChapter = new NamedChapter();
            curChapter.setChapterNumber(++chapterCount);
            file.getChapters().add(curChapter);
        }
    }
    
    private static Pattern timePattern = Pattern.compile("(\\d+):(\\d+):(\\d+)\\.(\\d+)");
    
    private static float parseChapterTime(String time) {
        Matcher m = timePattern.matcher(time);
        if (m.matches()) {
            int hour = Integer.parseInt(m.group(1));
            int min = Integer.parseInt(m.group(2));
            int sec = Integer.parseInt(m.group(3));
            float msec = Float.parseFloat("0." + m.group(4));
            
            return ((hour * 60f) + min) * 60f + sec + msec;
            
        } else {
            throw new IllegalArgumentException("Invalid time: " + time);
        }
    }

    private void handleSegmentHeader() {
        if (field.equalsIgnoreCase("Segment tracks")) {
            state = MKVParserState.Other;
        } else if (field.equalsIgnoreCase("Duration")) {
            Matcher m = durationPattern.matcher(value);
            if (m.find()) {
                file.setLength(Float.parseFloat(m.group(1)));
            } else {
                throw new IllegalArgumentException("Unable to parse duraction: " + value);
            }
        } else if (field.equals("Segment UID")) {
            Matcher m = segmentUIDPattern.matcher(value);
            if (m.matches()) {
                short[] data = new short[16];
                for (int i = 0; i < data.length; i++) {
                    String hex = m.group(i + 1);
                    data[i] = (short) Integer.parseInt(hex, 16);
                }
                file.setUid(data);
            }
        } else
            handleStateless();
    }

    private void handleUnknownTrack() {
        if (field.equalsIgnoreCase("Track number")) {
            trackNum = Integer.parseInt(value);
        } else if (field.equalsIgnoreCase("Track UID")) {
            trackUID = Long.parseLong(value);
        } else if (field.equalsIgnoreCase("Track type")) {
            if (value.equalsIgnoreCase("video")) {
                curVideo = new MKVVideo();
                vfwVideo = false;
                file.getVideoStreams().add(curVideo);
                state = MKVParserState.VideoTrack;
                curStream = curVideo;
            } else if (value.equalsIgnoreCase("audio")) {
                curAudio = new MKVAudio();
                curAudio.setStreamId(audioCount++);
                file.getAudioStreams().add(curAudio);
                state = MKVParserState.AudioTrack;
                curStream = curAudio;
            } else if (value.equalsIgnoreCase("subtitles")) {
                curSubtitle = new MKVSubtitle();
                curSubtitle.setStreamId(subtitleCount++);
                file.getSubtitleStreams().add(curSubtitle);
                state = MKVParserState.SubtitleTrack;
                curStream = curSubtitle;
            }

            curStream.setUid(trackUID);
            curStream.setTrackId(trackNum);
        } else
            handleStateless();
    }

    public static void main(String[] args) {
        System.out.println(parseFile(new File("/var/storage/Videos/Anime/Shana/Shana - 11 - Yuji, Shana, and Kisses.mkv")));
    }
}
