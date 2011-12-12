/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.info.data.handlers;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author bdickie
 */
public class DateCalendarTypeHandler extends CalendarTypeHandler {

    @Override
    public Object getResult(ResultSet rs, String colName) throws SQLException {
        return readTimestamp(rs.getDate(colName));
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return readTimestamp(cs.getDate(columnIndex));
    }

    private Calendar readTimestamp(Date ts) {
        if (ts == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
}
