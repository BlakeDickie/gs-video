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

import net.landora.animeinfo.data.AnimeNameLookup;
import net.landora.animeinfo.data.AnimeNameLookupMapper;
import net.landora.animeinfo.data.AnimeDataManager;
import net.landora.animeinfo.data.AnimeNameLookupSummary;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
public class AnimeTitleParser {

    private Logger log = LoggerFactory.getLogger(getClass());

    public void parseAnimeTitle(InputStream is) {
        SqlSession session = null;

        try {
            session = AnimeDataManager.getInstance().openSession();
            AnimeNameLookupMapper mapper = session.getMapper(AnimeNameLookupMapper.class);

            mapper.deleteAnimeNames();

            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

            reader.nextTag();
            reader.require(XMLStreamReader.START_ELEMENT, null, "animetitles");

            int t1;
            while((t1 = reader.nextTag()) != XMLStreamReader.END_ELEMENT) {
                reader.require(XMLStreamReader.START_ELEMENT, null, "anime");

                int animeId = Integer.parseInt(reader.getAttributeValue(null, "aid"));

                AnimeNameLookupSummary anime = new AnimeNameLookupSummary();
                anime.setAnimeId(animeId);

                
                List<AnimeNameLookup> newNames = new ArrayList<AnimeNameLookup>();

                int t2;
                while((t2 = reader.nextTag()) != XMLStreamReader.END_ELEMENT) {
                    reader.require(XMLStreamReader.START_ELEMENT, null, "title");

                    AnimeNameLookup name = new AnimeNameLookup();
                    for(int i = 0; i < reader.getAttributeCount(); i++) {
                        String aname = reader.getAttributeLocalName(i);
                        if (aname.equals("type"))
                            name.setType(reader.getAttributeValue(i));
                        else if (aname.equals("lang"))
                            name.setLanguage(reader.getAttributeValue(i));

                    }

                    name.setName(XMLUtilities.nextString(reader));
                    name.setAnime(anime);
                    newNames.add(name);
                }

                for(AnimeNameLookup name: newNames) {
                    if (name.getType().equalsIgnoreCase("main")) {
                        anime.setNameMain(name.getName());
                    } else if (name.getType().equalsIgnoreCase("official") && name.getLanguage().equalsIgnoreCase("en")) {
                        anime.setNameEnglish(name.getName());
                    }
                }

                mapper.insertAnime(anime);
                for(AnimeNameLookup name: newNames)
                    mapper.insertAnimeName(name);
                
            }
            reader.close();
            
            mapper.updateExistingAnimeMainName();
            mapper.deleteCoreAnimeNames();
            mapper.insertExistingAnimeMainName();
            
            session.commit();
        } catch (Exception e) {
            session.rollback();
            log.error("Error saving anime titles.", e);
        } finally {
            if (session != null)
                session.close();
        }
    }

}
