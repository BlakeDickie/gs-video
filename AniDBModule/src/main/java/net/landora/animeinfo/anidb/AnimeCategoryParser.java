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
