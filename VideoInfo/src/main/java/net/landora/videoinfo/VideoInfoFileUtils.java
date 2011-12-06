/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bdickie
 */
public class VideoInfoFileUtils {
 
    private static final Map<String,String> filenameReplaceRules = new HashMap<String,String>();
    private static final Pattern filenameReplaceRulesPattern;
    
    static {
        filenameReplaceRules.put("`", "'");
        filenameReplaceRules.put("\"", "");
        filenameReplaceRules.put("<", "");
        filenameReplaceRules.put(">", "");
        filenameReplaceRules.put("|", "");
        filenameReplaceRules.put("/", "");
        filenameReplaceRules.put("\\", "");
        filenameReplaceRules.put("?", "");
        filenameReplaceRules.put("*", "");
//        filenameReplaceRules.put(":", " - ");
        StringBuilder b = new StringBuilder();
        b.append("(");
        for(String search: filenameReplaceRules.keySet()) {
            if (b.length() > 1) {
                b.append("|");
            }
            b.append("(?:");
            b.append(Pattern.quote(search));
            b.append(")");
        }
        b.append(")");
        b.append("|( ?: ?)");
        filenameReplaceRulesPattern = Pattern.compile(b.toString());
    }
    
    public static String filterInvalidFilenameCharacters(String filename) {
        StringBuffer buffer = new StringBuffer(filename.length() + 5);
        
        Matcher m = filenameReplaceRulesPattern.matcher(filename);
        while(m.find()) {
            String basicReplace = m.group(1);
            String replace = m.group();
            if (basicReplace != null) {
                replace = filenameReplaceRules.get(basicReplace);
            } else if (m.group(2) != null) {
                replace = " ~ ";
            }
            
            m.appendReplacement(buffer, Matcher.quoteReplacement(replace));
        }
        m.appendTail(buffer);
        
        return buffer.toString();
    }
    
}
