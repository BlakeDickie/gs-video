/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.data.handlers;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author bdickie
 */
public class DateCalendarTypeHandler extends CalendarTypeHandler {

    @Override
    public Object getResult( ResultSet rs, String colName ) throws SQLException {
        return readTimestamp( rs.getDate( colName ) );
    }

    @Override
    public Object getResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return readTimestamp( cs.getDate( columnIndex ) );
    }

    private Calendar readTimestamp( Date ts ) {
        if ( ts == null ) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis( ts.getTime() );
        return cal;
    }
}
