/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.anidb;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author bdickie
 */
public class AniDBCommand {

    
    private String command;
    private LinkedHashMap<String,String> arguments;
    private boolean usesSessions;

    public AniDBCommand(String command) {
        this(command, true);
    }

    public AniDBCommand(String command, boolean usesSessions) {
        this.command = command;
        this.usesSessions = usesSessions;
        
        arguments = new LinkedHashMap<String, String>();
    }



    private static void appendEscape(StringBuffer buffer, String str) {
        str = StringEscapeUtils.escapeHtml(str);
        str = str.replaceAll("\\n", "<br />");
        buffer.append(str);
    }

    public String getResult() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(command);

        boolean hasArg = false;
        if (usesSessions || !arguments.isEmpty()) {
            buffer.append(" ");

            for(Map.Entry<String,String> entry: arguments.entrySet()) {
                if (hasArg)
                    buffer.append("&");
                else
                    hasArg = true;

                addParameter(buffer, entry.getKey(), entry.getValue());
            }

            if (usesSessions) {
                if (hasArg)
                    buffer.append("&");
                else
                    hasArg = true;

                addParameter(buffer, "s", AniDBUDPManager.getInstance().getSessionId());
            }
        }
        

        return buffer.toString();
    }

    private static void addParameter(StringBuffer buffer, String name, String value) {
        appendEscape(buffer, name);
        buffer.append("=");
        appendEscape(buffer, value);
    }

    @Override
    public String toString() {
        return getResult();
    }

    public void addArgument(String name, String value) {
        arguments.put(name, value);
    }

    public void addArgument(String name, int value) {
        addArgument(name, String.valueOf(value));
    }

    public void addArgument(String name, long value) {
        addArgument(name, String.valueOf(value));
    }

    public void addArgument(String name, Calendar value) {
        if (value == null)
            addArgument(name, 0);
        else
            addArgument(name, value.getTimeInMillis() / 1000l);
    }



}
