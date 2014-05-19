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
package net.landora.video.data;

import org.batoo.jpa.JPASettings;
import org.batoo.jpa.core.BatooPersistenceProvider;
import org.batoo.jpa.jdbc.DDLMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 *
 * @author bdickie
 */
public abstract class AbstractJPAManager {

    private static final BatooPersistenceProvider provider = new BatooPersistenceProvider();

    protected AbstractJPAManager() {
    }

    protected abstract List<? extends Class> getEntityClasses();

    protected abstract boolean createScheme();

    private EntityManagerFactory factory;

    protected DataSource getDataSource() {
        return DatabaseConnectionManager.getInstance().getDataSource();
    }

    protected synchronized EntityManagerFactory getEntityManagerFactory() {
        if ( factory == null ) {

            Map<String, String> props = new HashMap();

            List<? extends Class> entityClasses = getEntityClasses();
            String[] classNames = new String[entityClasses.size()];
            for ( int i = 0; i < entityClasses.size(); i++ ) {
                Class clazz = entityClasses.get( i );
                classNames[i] = clazz.getName();
            }

            String hintName = UUID.randomUUID().toString();
            BatooDSManager.registryDataSource( hintName, getDataSource() );

            props.put( JPASettings.DATASOURCE_POOL, BatooDSManager.class.getName() );
            props.put( JPASettings.DATASOURCE_NAME, hintName );
//            props.put("openjpa.MetaDataFactory", types.toString());
//            props.put("openjpa.ConnectionFactory", getDataSource());
//            props.put("openjpa.RuntimeUnenhancedClasses", "unsupported");
//            props.put("openjpa.Log", "slf4j");
//            props.put("openjpa.Id", getClass().getName());
            if ( createScheme() ) {
                props.put( JPASettings.DDL, DDLMode.CREATE.name() );
            } else {
                props.put( JPASettings.DDL, DDLMode.NONE.name() );
            }

            factory = provider.createEntityManagerFactory( getClass().getName(), props, classNames );

        }
        return factory;
    }

}
