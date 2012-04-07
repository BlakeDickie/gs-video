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

package net.landora.video.properties;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class Languages {

    private static final Map<String, String> langMap;

    static {
        langMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        try {
            String file = IOUtils.toString(Languages.class.getResourceAsStream("ISO-639-2_values_8bits.txt"), "UTF-8");
            Pattern p = Pattern.compile("^([^|]*)\\|([^|]*)\\|([^|]*)\\|([^|]*)\\|([^|]*)$", Pattern.MULTILINE);
            Matcher m = p.matcher(file);
            while (m.find()) {
                for (int i = 1; i <= 3; i++) {
                    String code = m.group(i).trim();
                    if (code != null && !code.isEmpty()) {
                        langMap.put(code, m.group(4));
                    }
                }

            }
        } catch (Exception e) {
            LoggerFactory.getLogger(Languages.class).error("Error creating language map.", e);
        }
    }

    public static String lookup(String langCode) {
        if (langCode.equalsIgnoreCase("und"))
            return "";
        
        return langMap.get(langCode);
    }
    
    public static List<String> getAllLanguages() {
        
        ArrayList<String> result = new ArrayList<String>(new HashSet<String>(langMap.values()));
        Collections.sort(result, new LangSorter());
        return result;
    }
    
    private static String[] TOP_LANGS = {"English", "Japanese", "French", "Spanish; Castilian"};
    
    private static class LangSorter implements Comparator<String> {

        public int compare(String o1, String o2) {
            int i1 = Integer.MAX_VALUE;
            int i2 = Integer.MAX_VALUE;
            for(int i = 0; i < TOP_LANGS.length; i++) {
                if (TOP_LANGS[i].equalsIgnoreCase(o1))
                    i1 = i;
                if (TOP_LANGS[i].equalsIgnoreCase(o2))
                    i2 = i;
            }
            
            if (i1 < i2)
                return -1;
            else if (i1 > i2)
                return 1;
            else
                return o1.compareToIgnoreCase(o2);
        }
        
    }
}
