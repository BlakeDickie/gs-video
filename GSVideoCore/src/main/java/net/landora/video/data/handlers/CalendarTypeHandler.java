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

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author bdickie
 */
public class CalendarTypeHandler implements TypeHandler {

    @Override
    public void setParameter( PreparedStatement ps, int i, Object parameter, JdbcType type ) throws SQLException {
        if ( parameter == null ) {
            ps.setNull( i, type.TYPE_CODE );
        } else {
            Calendar cal = (Calendar) parameter;
            switch ( type ) {
                case TIMESTAMP:
                    Timestamp ts = new Timestamp( cal.getTimeInMillis() );
                    ps.setTimestamp( i, ts );
                    break;
                case DATE:
                    Date date = new Date( cal.getTimeInMillis() );
                    ps.setDate( i, date );
                    break;
                default:
                    throw new SQLException( "Unsupported JdbcType for Calenders: " + type.toString() );
            }

        }
    }

    @Override
    public Object getResult( ResultSet rs, String colName ) throws SQLException {
        return readTimestamp( rs.getTimestamp( colName ) );
    }

    @Override
    public Object getResult( CallableStatement cs, int columnIndex ) throws SQLException {
        return readTimestamp( cs.getTimestamp( columnIndex ) );
    }

    private Calendar readTimestamp( Timestamp ts ) {
        if ( ts == null ) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis( ts.getTime() );
        return cal;
    }
}
