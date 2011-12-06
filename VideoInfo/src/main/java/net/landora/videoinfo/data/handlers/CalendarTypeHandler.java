/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.videoinfo.data.handlers;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 *
 * @author bdickie
 */
public class CalendarTypeHandler implements TypeHandler {


    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType type) throws SQLException {
        if (parameter == null)
            ps.setNull(i, type.TYPE_CODE);
        else {
            Calendar cal = (Calendar)parameter;
            switch(type) {
                case TIMESTAMP:
                    Timestamp ts = new Timestamp(cal.getTimeInMillis());
                    ps.setTimestamp(i, ts);
                    break;
                case DATE:
                    Date date = new Date(cal.getTimeInMillis());
                    ps.setDate(i, date);
                    break;
                default:
                    throw new SQLException("Unsupported JdbcType for Calenders: " + type.toString());
            }
            
        }
    }

    @Override
    public Object getResult(ResultSet rs, String colName) throws SQLException {
        return readTimestamp(rs.getTimestamp(colName));
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return readTimestamp(cs.getTimestamp(columnIndex));
    }

    private Calendar readTimestamp(Timestamp ts) {
        if (ts == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }
}
