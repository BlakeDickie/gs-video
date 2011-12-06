/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo.data.preferences;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class PreferencesDBA {
    private static Logger log = LoggerFactory.getLogger(PreferencesDBA.class);
    
    public static Set<String> getExtensions() {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper(PreferencesMapper.class);

            TreeSet<String> result = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
            result.addAll(mapper.getVideoExtensions());
            return result;
            
        } catch (Exception e) {
            log.error("Error getting extensions.", e);
            return Collections.EMPTY_SET;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static void setExtensions(Collection<String> extensions) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper(PreferencesMapper.class);
            
            
            mapper.deleteVideoExtensions();
            for(String extension: extensions) {
                mapper.insertVideoExtension(extension);
            }
            
            session.commit();
        } catch (Exception e) {
            log.error("Error saving extensions.", e);
            session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static Map<String,String> getPreferenceValues(String context) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper(PreferencesMapper.class);

            Map<String,String> result = new HashMap<String, String>();
            List<HashMap<String, String>> preferences = mapper.getPreferences(context);
            for(HashMap<String,String> row: preferences) {
                result.put(row.get("prefname"), row.get("prefvalue"));
            }
            return result;
            
        } catch (Exception e) {
            log.error("Error getting preferences.", e);
            return Collections.EMPTY_MAP;
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static void setPreferenceValue(String content, String prefName, String prefValue) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper(PreferencesMapper.class);
            
            if (mapper.updatePreference(content, prefName, prefValue) == 0)
                mapper.insertPreference(content, prefName, prefValue);
            
            session.commit();
        } catch (Exception e) {
            log.error("Error saving preferences.", e);
            session.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }
    
    public static void main(String[] args) {
        SqlSession session = null;

        try {
            session = PreferencesDataManager.getInstance().openSession();
            PreferencesMapper mapper = session.getMapper(PreferencesMapper.class);

            System.out.println(mapper.updatePreference("test", "test123", "afasdfa"));
            
        } catch (Exception e) {
            log.error("Error getting extensions.", e);
            
        } finally {
            if (session != null)
                session.close();
        }
    }
    
}
