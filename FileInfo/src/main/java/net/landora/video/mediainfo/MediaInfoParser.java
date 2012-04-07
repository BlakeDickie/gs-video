/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.landora.video.mediainfo;


import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.video.programs.CommonPrograms;
import net.landora.video.programs.ProgramsAddon;
import net.landora.video.properties.*;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author bdickie
 */
public class MediaInfoParser {

    public static final Pattern dataPattern = Pattern.compile("([^:]+?)\\s*:\\s*(.*?)\\s*");
    private static final Pattern numberPattern = Pattern.compile("\\d+");
    private static final Pattern chapterStartPattern = Pattern.compile("(\\d+):(\\d+):(\\d+\\.\\d+)\\s*(.*?)");

    public static Video parseFile(File f) {
        return new MediaInfoParser().handleFile(f);
    }
    
    private State state;
    private Video file;
    private VideoStream curVideo;
    private AudioStream curAudio;
    private SubtitleStream curSubtitle;
    private int audioCount;
    private int subtitleCount;
    

    public Video handleFile(File f) {

        try {
            //ProcessBuilder process = new ProcessBuilder(Program.MediaInfo.getConfiguredPath(), "-Full", "--Language=raw", f.getAbsolutePath());
            ProcessBuilder process = new ProcessBuilder(ProgramsAddon.getInstance().getConfiguredPath(CommonPrograms.MEDIAINFO), "-Full", "--Language=raw", f.getAbsolutePath());
            process.redirectErrorStream(true);
            Process p = process.start();


            StringWriter buffer = new StringWriter();
            IOUtils.copy(p.getInputStream(), buffer);
            p.waitFor();
            
            String reply = buffer.toString();
            buffer = null;

            file = new Video();
            file.setFile(f);

            state = State.EmptyLine;

            BufferedReader reader = new BufferedReader(new StringReader(reply));
            String line;
            while((line = reader.readLine()) != null) {

                Matcher m = dataPattern.matcher(line);
                if (m.matches()) {
                    String field = m.group(1);
                    String value = m.group(2);

                    switch(state) {
                        case General:
                            handleGeneral(field, value);
                            break;

                        case Video:
                            handleVideo(field, value);
                            break;

                        case Audio:
                            handleAudio(field, value);
                            break;

                        case Text:
                            handleSubtitle(field, value);
                            break;

                        case Chapters:
                            handleChapter(field, value);
                            break;
                    }

                } else if (line.trim().isEmpty()) {
                    state = State.EmptyLine;
                } else {
                    line = line.trim();
                    if (line.equals("General")) {
                        state = State.General;
                    } else if (line.equals("Video")) {
                        state = State.Video;
                        curVideo = new VideoStream();
                        file.getVideoStreams().add(curVideo);
                    } else if (line.equals("Audio")) {
                        state = State.Audio;
                        curAudio = new AudioStream();
                        curAudio.setStreamId(++audioCount);
                        file.getAudioStreams().add(curAudio);
                    } else if (line.equals("Text")) {
                        state = State.Text;
                        curSubtitle = new SubtitleStream();
                        curSubtitle.setStreamId(subtitleCount++);
                        file.getSubtitleStreams().add(curSubtitle);
                    } else if (line.equals("Chapters")) {
                        state = State.Chapters;
                    }
                }
                
            }

            cleanupChapters();

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private void handleGeneral(String field, String value) {
        if (field.equals("Duration")) {
            long lengthMS = Long.parseLong(value);
            file.setLength(lengthMS / 1000f);
        }
    }

    private void handleVideo(String field, String value) {
        if (field.equals("Format")) {
            curVideo.setFormat(VideoFormat.getFormat(value));
        } else if (field.equals("DisplayAspectRatio/String")) {
            curVideo.setAspect(value);
        } else if (field.equals("Language")) {
            curVideo.setLanguage(value);
        } else if (field.equals("FrameRate")) {
            curVideo.setFps(value);
        } else if (field.equals("Width")) {
            curVideo.setPixelWidth(Integer.parseInt(value));
        } else if (field.equals("Height")) {
            curVideo.setPixelHeight(Integer.parseInt(value));
        }
    }

    private void handleAudio(String field, String value) {
        if (field.equals("Format")) {
            curAudio.setFormat(AudioFormat.getFormat(value));
        } else if (field.equals("Language")) {
            curAudio.setLanguage(value);
        } else if (field.equals("Channel(s)")) {
            curAudio.setChannels(Integer.parseInt(value));
        }
    }

    private void handleSubtitle(String field, String value) {
        if (field.equals("Format")) {
            curSubtitle.setFormat(SubtitleFormat.getFormat(value));
        } else if (field.equals("Language")) {
            curSubtitle.setLanguage(value);
        }
    }

    private void handleChapter(String field, String value) {
        if (!numberPattern.matcher(field).matches())
            return;

        int chapterNumber = Integer.parseInt(field);

        Matcher m = chapterStartPattern.matcher(value);
        if (!m.matches())
            return;

        NamedChapter chapter = new NamedChapter();
        chapter.setTitle(m.group(4));

        float seconds = Float.parseFloat(m.group(3));
        int mins = Integer.parseInt(m.group(2));
        int hours = Integer.parseInt(m.group(1));
        chapter.setChapterNumber(chapterNumber);
        chapter.setStartTime(seconds + 60 * (mins + 60 * hours));

        file.getChapters().add(chapter);
        
    }

    private void cleanupChapters() {
        int num = file.getChapters().size();
        for (int i = 0; i < num; i++) {
            Chapter chapter = file.getChapters().get(i);
            float nextChap;
            if (i == num - 1) {
                nextChap = file.getLength();
            } else
                nextChap = file.getChapters().get(i + 1).getStartTime();
            chapter.setLength(nextChap - chapter.getStartTime());
        }
    }
    

    public static void main(String[] args) {
        Video v = parseFile(new File("/var/storage/Videos/Anime/To Aru Kagaku no Railgun/To Aru Kagaku no Railgun - 02 - For Working Under the Blazing Sun, Proper Hydration is Required.mkv"));
    }

    private static enum State {
        General,
        Video,
        Audio,
        Text,
        Chapters,
        EmptyLine
    }
}
