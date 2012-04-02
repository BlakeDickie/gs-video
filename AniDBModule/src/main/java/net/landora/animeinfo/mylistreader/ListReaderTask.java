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

package net.landora.animeinfo.mylistreader;

import java.awt.KeyboardFocusManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javax.swing.JOptionPane;
import net.landora.animeinfo.AnimeInfoPreference;
import net.landora.video.tasks.NBTask;


/**
 *
 * @author bdickie
 */
public class ListReaderTask extends NBTask<Boolean, Object> {

    private File listFile;

    public ListReaderTask(File listFile) {
        super("Importing MyList");
        this.listFile = listFile;
    }
    

    @Override
    protected void success(Boolean result) {
        JOptionPane.showMessageDialog(KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow(), 
                "MyList import completed successfully.");
    }

    @Override
    protected void failure(Throwable t) {
        JOptionPane.showMessageDialog(KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow(), 
                "MyList import failed.");
    }

    @Override
    protected void finished() {
        AnimeInfoPreference.LastMyListExport.setLong(Math.max(System.currentTimeMillis(), AnimeInfoPreference.LastMyListExport.getLong()));
    }
    
    
    
    
    
    private Map<String,String> values;
    
    @Override
    protected Boolean doInBackground() throws Throwable {
        return new ListReader().download(new NBTaskProgressInputStream(
                new BufferedInputStream(new FileInputStream(listFile)),
                listFile.length() ) );
    }
    
    
}
