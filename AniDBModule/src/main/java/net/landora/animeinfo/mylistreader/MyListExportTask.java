/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.mylistreader;

import net.landora.animeinfo.anidb.AniDB;
import net.landora.video.tasks.NBTask;

/**
 *
 * @author bdickie
 */
public class MyListExportTask extends NBTask<Object, Object> {

    public MyListExportTask() {
        super("Queuing MyList Export");
    }

    
    
    @Override
    protected Object doInBackground() throws Throwable {
        start();
        
        AniDB.queueMyListExport("xml-plain-new");
        
        return null;
    }

    @Override
    protected void success(Object result) {
        
    }
    
}
