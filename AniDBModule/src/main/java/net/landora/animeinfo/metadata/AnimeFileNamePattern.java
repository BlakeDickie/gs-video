/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.metadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.video.utils.Touple;

/**
 *
 * @author bdickie
 */
public enum AnimeFileNamePattern {
    //Amagami SS - 01 - Morishima Haruka - Chapter 1 Yearning.mkv

    NumberedBasic("(.+?) - ([STCPO]?\\d+)(?: - .*)?"),  //Amagami SS - 01 - Morishima Haruka - Chapter 1 Yearning.mkv
    NumberedBasic2("(.+) - ([STCPO]?\\d+)(?: - .*?)"),

    VolPattern("(.+)\\s+Vol\\.?(\\d+)(?:[ -].*)?"),
    Fullname("(.*+)")
            ;

    private Pattern pattern;
    private Integer nameGroup;
    private Integer numberGroup;

    private AnimeFileNamePattern(String pattern, int nameGroup, int numberGroup) {
        this(pattern);
        this.nameGroup = nameGroup;
        this.numberGroup = numberGroup;
    }

    private AnimeFileNamePattern(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    public Touple<String, String> attemptParsing(String filename) {
        Matcher m = pattern.matcher(filename);
        if (m.matches()) {
            

            Touple<String, String> result = new Touple<String, String>();
            if (nameGroup == null) {
                result.setFirst(m.group(1));
            } else
                result.setFirst(m.group(nameGroup));

            if (nameGroup != null && numberGroup > 0)
                result.setSecond(m.group(numberGroup));
            else if (m.groupCount() > 1)
                result.setSecond(m.group(2));
            
            return result;
        } else
            return null;
    }

}
