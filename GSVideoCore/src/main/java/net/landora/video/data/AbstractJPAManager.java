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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.openjpa.persistence.PersistenceProviderImpl;


/**
 *
 * @author bdickie
 */
public abstract class AbstractJPAManager {

    protected AbstractJPAManager() {
    }
    
    protected abstract List<? extends Class> getEntityClasses();
    
    protected abstract boolean createScheme();
    
    private EntityManagerFactory factory;
    
    protected synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            
            Map<Object,Object> props = new HashMap<Object, Object>();
            
            StringBuilder types = new StringBuilder();
            types.append("jpa(Types=");
            List<? extends Class> entityClasses = getEntityClasses();
            for (int i = 0; i < entityClasses.size(); i++) {
                Class clazz = entityClasses.get(i);
                if (i > 0)
                    types.append(";");
                types.append(clazz.getName());
            }
            types.append(")");
            
            props.put("openjpa.MetaDataFactory", types.toString());
            props.put("openjpa.ConnectionFactory", DatabaseConnectionManager.getInstance().getDataSource());
            props.put("openjpa.RuntimeUnenhancedClasses", "unsupported");
            props.put("openjpa.Log", "slf4j");
            props.put("openjpa.Id", getClass().getName());
            
            if (createScheme()) {
                props.put("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
            }
            
            factory = Persistence.createEntityManagerFactory("openjpa", props);
            
            
        }
        return factory;
    }
    
}
