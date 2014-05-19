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
