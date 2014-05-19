/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.xbmcdatabase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import net.landora.xbmc.database.XBMCDatabase;
import net.landora.xbmc.database.entities.Episode;
import net.landora.xbmc.database.entities.VideoFile;

/**
 *
 * @author bdickie
 */
public class Testing {

    public static void main(String[] args) throws Exception {

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");

        config.addDataSourceProperty("serverName", "faye");
        config.addDataSourceProperty("databaseName", "xbmc_videos" + XBMCDatabase.DB_VERSION);
        config.addDataSourceProperty("user", "xbmc");
        config.addDataSourceProperty("password", "hawkpath");
        config.setMinimumIdle(1);

        config.setPoolName("xbmc_videos");

        HikariDataSource ds = new HikariDataSource(config);

        XBMCDatabase db = new XBMCDatabase(ds);
        File f = new File("/var/storage/Videos/TV/Blood Ties/Blood Ties S1E05.avi");
        VideoFile file = db.getFileAt(f);
        System.out.println(file.getFilename());
        Episode ep = db.getEpisodeForFile(file);
        System.out.println(ep.getShow().getStudio().size());

    }

}
