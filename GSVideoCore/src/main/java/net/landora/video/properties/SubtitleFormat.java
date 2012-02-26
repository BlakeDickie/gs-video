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
public enum SubtitleFormat {
    ASS(3, ".*ass.*"),
    SRT(2, ".*srt.*", "S_TEXT/UTF8"),
    VobSub(1, ".*vobsub.*"),
    Unknown(0);
    
    private Pattern[] aliases;
    private int priority;

    private SubtitleFormat(int priority, String... aliases) {
        this.priority = priority;
        this.aliases = new Pattern[aliases.length + 1];
        for (int i = 0; i < aliases.length; i++) {
            this.aliases[i] = Pattern.compile(aliases[i], Pattern.CASE_INSENSITIVE);
        }
        this.aliases[aliases.length] = Pattern.compile(Pattern.quote(name()), Pattern.CASE_INSENSITIVE);
    }

    public synchronized static SubtitleFormat getFormat(String formatDesc) {
        for (SubtitleFormat format : values()) {
            for (Pattern alias : format.aliases) {
                if (alias.matcher(formatDesc).matches()) {
                    return format;
                }
            }
        }

        LoggerFactory.getLogger(SubtitleFormat.class).warn("Unable to find subtitle format:" + formatDesc);
        return Unknown;

    }

    public int getPriority() {
        return priority;
    }
    
    
}
