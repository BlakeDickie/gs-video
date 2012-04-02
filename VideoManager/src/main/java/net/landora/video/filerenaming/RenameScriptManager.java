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

package net.landora.video.filerenaming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class RenameScriptManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static RenameScriptManager instance = new RenameScriptManager();
    }

    public static RenameScriptManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>

    private RenameScriptManager() {
        loadCurrentScript();
    }
    private RenamingScript renamingScript;

    public RenamingScript getRenamingScript() {
        return renamingScript;
    }
    
    final void loadCurrentScript() {
        renamingScript = createRenamingScript(getFolderRenameScript(), getFileRenameScript(), true);
    }

    RenamingScript createRenamingScript(String folderScript, String fileScript, boolean reportExceptions) {
        try {
            StringBuilder str = new StringBuilder();
            
            
            str.append(getClassScript("RenameScript.py"));
            str.append("\n");
            str.append(createScript("findFolderName", folderScript));
            str.append("\n");
            str.append(createScript("findFilename", fileScript));

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("jython");

            engine.eval(str.toString());

            Invocable inv = (Invocable) engine;
            return inv.getInterface(RenamingScript.class);
        } catch (Exception ex) {
            if (reportExceptions)
                LoggerFactory.getLogger(getClass()).error("Error getting rename script.", ex);
            return null;
        }
    }

    private String createScript(String functionName, String function) {
        try {
            StringBuilder builder = new StringBuilder();

            builder.append("def ");
            builder.append(functionName);
            builder.append("(metadata):\n");

            BufferedReader reader = new BufferedReader(new StringReader(function));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append("  ");
                builder.append(line.replaceAll("\t", "  "));
                builder.append("\n");
            }

            return builder.toString();
        } catch (IOException ex) {
            LoggerFactory.getLogger(getClass()).error("Error getting rename script.", ex);
            return "";
        }
    }

    public String getFileRenameScript() {
        return getRenameScript("RenameScript_File.py", RenamePreferences.RenameScript_File);
    }

    public String getFolderRenameScript() {
        return getRenameScript("RenameScript_Folder.py", RenamePreferences.RenameScript_Folder);
    }

    public void setFileRenameScript(String script) {
        setRenameScript("RenameScript_File.py", RenamePreferences.RenameScript_File, script);
    }

    public void setFolderRenameScript(String script) {
        setRenameScript("RenameScript_Folder.py", RenamePreferences.RenameScript_Folder, script);
    }
    private static final String CHARSET = "UTF-8";

    private String getClassScript(String filename) {
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(filename);
            return IOUtils.toString(is, CHARSET);
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Error getting class script.", e);
            return "return None";
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
        }
    }
    
    private String getRenameScript(String filename, RenamePreferences preference) {
        String pref = preference.getString();
        if (pref == null || pref.trim().isEmpty())
            return getClassScript(filename);
        return pref;
    }

    private void setRenameScript(String filename, RenamePreferences preference, String script) {
        if (getClassScript(filename).equals(script))
            preference.setString("");
        else
            preference.setString(script);
    }
}
