/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.anidb;

import net.landora.animeinfo.data.AnimeCategory;
import net.landora.animeinfo.data.AnimeDataManager;
import net.landora.animeinfo.data.AnimeMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import net.landora.video.utils.XMLUtilities;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class AnimeCategoryParser {

    private Logger log = LoggerFactory.getLogger(getClass());

    public void parseAnimeCategory(InputStream is) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeMapper mapper = session.getMapper(AnimeMapper.class);


            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

            reader.nextTag();
            reader.require(XMLStreamReader.START_ELEMENT, null, "categorylist");

            Map<Integer,AnimeCategory> categories = new HashMap<Integer, AnimeCategory>();
            Map<Integer,Integer> parentCategories = new HashMap<Integer, Integer>();

            int t1;
            while((t1 = reader.nextTag()) != XMLStreamReader.END_ELEMENT) {
                reader.require(XMLStreamReader.START_ELEMENT, null, "category");

                int categoryId = Integer.parseInt(reader.getAttributeValue(null, "id"));
                int parentCategoryId = Integer.parseInt(reader.getAttributeValue(null, "parentid"));

                AnimeCategory category = new AnimeCategory();
                category.setId(categoryId);
                category.setHentai(Boolean.parseBoolean(reader.getAttributeValue(null, "ishentai")));



                int t2;
                while((t2 = reader.nextTag()) != XMLStreamReader.END_ELEMENT) {
                    if (reader.getLocalName().equals("name")) {
                        category.setName(XMLUtilities.nextString(reader).trim());
                    } else if (reader.getLocalName().equals("description")) {
                        category.setDescription(XMLUtilities.nextString(reader).trim());
                    }
                }

                if (categoryId > 0) {
                    if (mapper.updateCategory(category) == 0)
                        mapper.insertCategory(category);

                    categories.put(categoryId, category);
                    
                    if (parentCategoryId > 0)
                        parentCategories.put(categoryId, parentCategoryId);
                }
            }
            reader.close();

            for(Map.Entry<Integer,Integer> entry: parentCategories.entrySet()) {
                AnimeCategory c1 = categories.get(entry.getKey());
                c1.setParentCategory(categories.get(entry.getValue()));
                mapper.updateCategory(c1);
            }

            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving anime categories.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }

}
