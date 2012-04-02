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

package net.landora.video.data;

import java.util.Calendar;
import java.util.List;
import net.landora.video.data.handlers.CalendarTypeHandler;
import net.landora.video.data.handlers.DateCalendarTypeHandler;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;

/**
 *
 * @author bdickie
 */
public abstract class AbstractDataManager {


    protected AbstractDataManager() {
        
    }
    
    protected abstract List<? extends Class> getMapperClasses();
    protected abstract List<? extends Class> getAliasTypeClasses();
    
    private SqlSessionFactory factory;
    
    private synchronized SqlSessionFactory getFactory() {
        if (factory == null) {
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment =
            new Environment("development", transactionFactory, DatabaseConnectionManager.getInstance().getDataSource());
            Configuration configuration = new Configuration(environment);
            
            
            configuration.setCacheEnabled(true);
            configuration.setLazyLoadingEnabled(false);
            configuration.getTypeHandlerRegistry().register(Calendar.class, JdbcType.TIMESTAMP, new CalendarTypeHandler());
            configuration.getTypeHandlerRegistry().register(Calendar.class, JdbcType.DATE, new DateCalendarTypeHandler());
            
            
            for(Class<?> clazz: getAliasTypeClasses()) {
                String className = clazz.getSimpleName();
                configuration.getTypeAliasRegistry().registerAlias(className, clazz);
            }
            
            for(Class<?> clazz: getMapperClasses())
                configuration.addMapper(clazz);
            
            factory =
                new SqlSessionFactoryBuilder().build(configuration);
        }
        return factory;
    }

    
    public SqlSession openSession() {
//        return sessionFactory.openSession(ExecutorType.BATCH);
        return getFactory().openSession();
    }
}
