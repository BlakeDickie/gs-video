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

package net.landora.video.dvd;

import net.landora.video.file_info.FileInfoPreferences;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.landora.video.module.AbstractModule;
import net.landora.video.programs.CommonPrograms;
import net.landora.video.programs.ProgramsAddon;
import net.landora.video.properties.AudioFormat;
import net.landora.video.properties.dvd.*;
import net.landora.video.utils.MutableObject;
import net.landora.video.utils.OSUtils;
import net.landora.video.utils.SAXHandler;
import net.landora.video.utils.XMLUtilities;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author bdickie
 */
public class LsDVDReader extends AbstractModule implements net.landora.video.properties.DVDReader {

    /** Creates a new instance of DVDReader */
    public LsDVDReader() {
    }

    private DVDDisk disk;
    private int subtitleId;

    @Override
    public DVDDisk readDisk() {
        return readDisk(FileInfoPreferences.DVDPath.getString());
    }

    @Override
    public DVDDisk readDisk(String dvdPath) {

        try {
            ProcessBuilder process = new ProcessBuilder(ProgramsAddon.getInstance().getConfiguredPath(CommonPrograms.LSDVD),
                    "-acsvnOx", dvdPath);
            process.redirectErrorStream(true);
            Process p = process.start();


            String xml = readAndStripDVDReadMessages(p);
            

            if (xml.startsWith("Can't open disc") || xml.startsWith("Can't find device"))
                return null;
            
            XMLUtilities.saxParse(xml.replaceAll("&", "&amp;"),
                    new LsDVDParser(), false);
            disk.setDvdDevice(dvdPath);
            return disk;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String readAndStripDVDReadMessages(Process p) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder output = new StringBuilder();
        boolean prolog = true;
        String line;
        while((line = reader.readLine()) != null) {
            if (line.startsWith("libdvdread: "))
                System.err.println(line);
            else if (line.equals("Couldn't read enough bytes for title."))
                continue;
            else if (prolog &&
                    ( line.trim().equals("") ||
                    line.startsWith("***") ))
                continue;
            else {
                prolog = true;
                output.append(line);
                output.append('\n');
            }
        }
        p.waitFor();
        

        return output.toString();
    }
    
    private static final Set<String> textTags;

    static {
        textTags = new HashSet<String>();
        textTags.add("title");
        textTags.add("ix");
        textTags.add("length");
        textTags.add("aspect");
        textTags.add("langcode");
        textTags.add("format");
        textTags.add("channels");
        textTags.add("streamid");
        textTags.add("content");
        textTags.add("angles");
        textTags.add("width");
        textTags.add("height");
        textTags.add("fps");
    }

    private class LsDVDParser extends SAXHandler {

        private StringBuffer buffer;
        private DVDTitle title;
        private DVDVideo video;
        private DVDAudio audio;
        private DVDSubtitle subtitle;
        private DVDChapter chapter;
        private float chapterTime;

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String value = (buffer == null ? null : buffer.toString());

            if (localName.equalsIgnoreCase("title")) {
                disk.setTitle(value);
            } else if (localName.equalsIgnoreCase("track")) {
                title = null;
                video = null;
            } else if (localName.equalsIgnoreCase("audio")) {
                audio = null;
            } else if (localName.equalsIgnoreCase("chapter")) {
                chapter = null;
            } else if (localName.equalsIgnoreCase("subp")) {
                subtitle = null;
            } else if (localName.equalsIgnoreCase("ix")) {
                int index = Integer.parseInt(value);
                if (chapter != null) {
                    chapter.setChapterNumber(index);
                } else if (audio == null && subtitle == null && title != null) {
                    title.setNumber(index);
                }
            } else if (localName.equalsIgnoreCase("length")) {
                float length = Float.parseFloat(value);
                if (chapter != null) {
                    chapter.setLength(length);
                    chapter.setStartTime(chapterTime);
                    chapterTime += length;
                } else if (title != null) {
                    title.setLength(length);
                }
            } else if (localName.equalsIgnoreCase("fps")) {
                if (title != null)
                    video.setFps(value);
            } else if (localName.equalsIgnoreCase("aspect")) {
                if (title != null) {
                    video.setAspect(value);
                }
            } else if (localName.equalsIgnoreCase("width")) {
                if (title != null) {
                    video.setPixelWidth(Integer.parseInt(value));
                }
            } else if (localName.equalsIgnoreCase("height")) {
                if (title != null) {
                    video.setPixelHeight(Integer.parseInt(value));
                }
            } else if (localName.equalsIgnoreCase("angles")) {
                if (title != null) {
                    video.setAngles(Integer.parseInt(value));
                }
            } else if (localName.equalsIgnoreCase("langcode")) {
                if (audio != null) {
                    audio.setLanguage(value);
                } else if (subtitle != null) {
                    subtitle.setLanguage(value);
                }
            } else if (localName.equalsIgnoreCase("streamid")) {
                int stream;
                if (value.startsWith("0x")) {
                    stream = Integer.parseInt(value.substring(2), 16);
                } else {
                    stream = Integer.parseInt(value);
                }

                if (audio != null) {
                    audio.setStreamId(stream);
                }

            } else if (localName.equalsIgnoreCase("content")) {
                if (audio != null) {
                    audio.setContent(value);
                } else if (subtitle != null) {
                    subtitle.setContent(value);
                }
            } else if (localName.equalsIgnoreCase("format") &&
                    audio != null) {
                audio.setFormat(AudioFormat.getFormat(value));
            } else if (localName.equalsIgnoreCase("channels") &&
                    audio != null) {
                audio.setChannels(Integer.parseInt(value));
            }

            buffer = null;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (localName.equalsIgnoreCase("lsdvd")) {
                disk = new DVDDisk();
                disk.setTitles(new ArrayList<DVDTitle>());
            } else if (localName.equalsIgnoreCase("track")) {
                title = new DVDTitle(disk);
                video = new DVDVideo();
                title.getVideoStreams().add(video);
                disk.getTitles().add(title);
                subtitleId = 0;
                chapterTime = 0f;
            } else if (localName.equalsIgnoreCase("audio")) {
                audio = new DVDAudio();
                title.getAudioStreams().add(audio);
            } else if (localName.equalsIgnoreCase("chapter")) {
                chapter = new DVDChapter();
                title.getChapters().add(chapter);
            } else if (localName.equalsIgnoreCase("subp")) {
                subtitle = new DVDSubtitle();
                subtitle.setStreamId(subtitleId++);
                title.getSubtitleStreams().add(subtitle);
            } else if (textTags.contains(localName)) {
                buffer = new StringBuffer();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (buffer != null) {
                buffer.append(ch, start, length);
            }
        }
    }

    @Override
    protected void loadModuleState(MutableObject<Boolean> usable, MutableObject<Boolean> configured, MutableObject<String> errorMessage) {
        if (!OSUtils.isLinux()) {
            errorMessage.setValue("lsdvd is only avaliable on Linux.");
            return;
        }
        
        usable.setValue(true);
        
        if (!ProgramsAddon.getInstance().isAvaliable(CommonPrograms.LSDVD)) {
            errorMessage.setValue("lsdvd not installed or configured.");
            return;
        }
        
        configured.setValue(true);
    }

    @Override
    public String getModuleDescription() {
        return "Reads information about DVDs through lsdvd (http://untrepid.com/lsdvd/).";
    }

    @Override
    public String getModuleName() {
        return "lsdvd DVD Reader";
    }

    public int getModulePriority() {
        return MODULE_PRIORITY_PREFERRED;
    }

    
    
}
