/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.mylistreader;

import java.awt.KeyboardFocusManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javax.swing.JOptionPane;
import net.landora.animeinfo.AnimeInfoPreference;
import net.landora.gsuiutils.NBTask;

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
