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
package net.landora.xbmcdatabase;

import java.io.File;
import net.landora.xbmc.database.XBMCDatabase;
import net.landora.xbmc.database.entities.Episode;
import net.landora.xbmc.database.entities.VideoFile;
import org.logicalcobwebs.proxool.ProxoolDataSource;

/**
 *
 * @author bdickie
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        
        ProxoolDataSource ds = new ProxoolDataSource();
        ds.setAlias("videos");
        ds.setDriver("com.mysql.jdbc.Driver");
        ds.setDriverUrl(String.format("jdbc:mysql://%s/%s", "faye", "xbmc_videos75"));
        ds.setUser("xbmc");
        ds.setPassword("hawkpath");
        ds.setMinimumConnectionCount(1);
        
        XBMCDatabase db = new XBMCDatabase(ds);
        File f = new File("/var/storage/Videos/TV/Blood Ties/Blood Ties S1E05.avi");
        VideoFile file = db.getFileAt(f);
        System.out.println(file);
        Episode ep = db.getEpisodeForFile(file);
        System.out.println(ep.getShow().getStudio());
        
    }

}
