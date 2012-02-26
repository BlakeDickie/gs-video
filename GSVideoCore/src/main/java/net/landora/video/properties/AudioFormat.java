/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.properties;

import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public enum AudioFormat {

    AC3(1, ".*ac3.*"),
    AAC("faad", ".*aac.*"),
    MP3(".*mp3.*"),
    MP2("ffmp2"),
    DTS(2, ".*dca.*", ".*dts.*"),
    Vorbis(".*VORBIS.*"),
    WMA("ffwmav\\d*", "wma.*", "divx"),
    RealAudio("(?:ff)?ra.*"),
    QuickTime(".*qdm.*", "qclp", "qt.*"),
    PCM(".*pcm.*"),
    FLAC(".*flac.*"),
    Unknown;
    
    private Pattern[] aliases;
    private int priority;

    private AudioFormat(String... aliases) {
        this(0, aliases);
    }
    
    private AudioFormat(int priority, String... aliases) {
        this.priority = priority;
        this.aliases = new Pattern[aliases.length + 1];
        for (int i = 0; i < aliases.length; i++) {
            this.aliases[i] = Pattern.compile(aliases[i], Pattern.CASE_INSENSITIVE);
        }
        this.aliases[aliases.length] = Pattern.compile(Pattern.quote(name()), Pattern.CASE_INSENSITIVE);
    }

    public synchronized static AudioFormat getFormat(String formatDesc) {
        for (AudioFormat format : values()) {
            for (Pattern alias : format.aliases) {
                if (alias.matcher(formatDesc).matches()) {
                    return format;
                }
            }
        }

        LoggerFactory.getLogger(AudioFormat.class).warn("Unable to find audio format:" + formatDesc);
        return Unknown;

    }

    public int getPriority() {
        return priority;
    }
}
