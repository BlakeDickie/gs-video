/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.anidb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.landora.animeinfo.data.AnimeCategory;
import java.util.Calendar;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import net.landora.animeinfo.data.AnimeName;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import net.landora.animeinfo.data.Anime;
import net.landora.animeinfo.data.AnimeDBA;
import org.apache.commons.io.IOUtils;

import static net.landora.video.utils.XMLUtilities.*;

/**
 *
 * @author bdickie
 */
public class AniDBHTTPManager {

    private static final String HTTP_CLIENT_NAME = "gsvideohttp";
    private static final int HTTP_CLIENT_VER = 1;
    private static final int HTTP_API_PROTO_VERSION = 1;
    private static final String HTTP_URL = "http://api.anidb.net:9001/httpapi?client=" + HTTP_CLIENT_NAME + "&clientver=" + HTTP_CLIENT_VER + "&protover=" + HTTP_API_PROTO_VERSION;
    private static final String HTTP_NAMES_URL = "http://anidb.net/api/animetitles.xml.gz";

    private Logger log = LoggerFactory.getLogger(getClass());

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static AniDBHTTPManager instance = new AniDBHTTPManager();
    }

    public static AniDBHTTPManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>


    private AniDBHTTPManager() {
    }

    public Anime downloadAnime(int aid) {
        InputStream is = null;
        try {

//            URL url = new URL(String.format("%s&request=anime&aid=%d", HTTP_URL, aid));
//            is = new GZIPInputStream(url.openStream());

            is = new BufferedInputStream(new FileInputStream("/home/bdickie/anidb/http_test.xml"));


            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

            reader.nextTag();
            reader.require(XMLStreamReader.START_ELEMENT, null, "anime");

            Anime anime = new Anime();

            anime.setAnimeId(Integer.parseInt(reader.getAttributeValue(null, "id")));
            anime.setHentai(Boolean.parseBoolean(reader.getAttributeValue(null, "restricted")));
            anime.setLastLoaded(Calendar.getInstance());

            while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                String tagName = reader.getLocalName();
                if (tagName.equals("type")) {
                    anime.setType(nextString(reader));
                } else if (tagName.equals("episodecount")) {
                    anime.setEpisodeCount(Integer.parseInt(nextString(reader)));
                } else if (tagName.equals("startdate")) {
//                    anime.setStartDate(nextString(reader));
                } else if (tagName.equals("enddate")) {
//                    anime.setEndDate(nextString(reader));
                } else if (tagName.equals("description")) {
                    anime.setDescription(nextString(reader));
                } else if (tagName.equals("picture")) {
                    anime.setPictureFileName(nextString(reader));
                } else if (tagName.equals("titles")) {
                    List<AnimeName> names = new ArrayList<AnimeName>();
                    while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                        reader.require(XMLStreamReader.START_ELEMENT, null, "title");

                        AnimeName name = new AnimeName();
                        for(int i = 0; i < reader.getAttributeCount(); i++) {
                            String aname = reader.getAttributeLocalName(i);
                            if (aname.equals("type"))
                                name.setType(reader.getAttributeValue(i));
                            else if (aname.equals("lang"))
                                name.setLanguage(reader.getAttributeValue(i));

                        }

                        name.setName(nextString(reader));
                        name.setAnime(anime);
                        names.add(name);
                    }

                    for(AnimeName name: names) {
                        if (name.getType().equalsIgnoreCase("main")) {
                            anime.setNameMain(name.getName());
                        } else if (name.getType().equalsIgnoreCase("official") && name.getLanguage().equalsIgnoreCase("en")) {
                            anime.setNameEnglish(name.getName());
                        }
                    }
                    anime.setNames(names);
                } else if (tagName.equals("ratings")) {
                    
                    while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                        String tagName2 = reader.getLocalName();
                        int count = Integer.parseInt(reader.getAttributeValue(null, "count"));
                        float value = Float.parseFloat(nextString(reader));
                        if (tagName2.equals("permanent")) {
                            anime.setRatingPermanent(value);
                            anime.setRatingPermanentVotes(count);
                        } else if (tagName2.equals("temporary")) {
                            anime.setRatingTemporary(value);
                            anime.setRatingTemporaryVotes(count);
                        }
                    }

                } else if (tagName.equals("categories")) {

                    while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                        reader.require(XMLStreamReader.START_ELEMENT, null, "category");

                        int categoryid = Integer.parseInt(reader.getAttributeValue(null, "id"));
                        int weight = Integer.parseInt(reader.getAttributeValue(null, "weight"));

                        AnimeCategory category = AnimeDBA.getAnimeCategory(categoryid);
                        if (category == null)
                            return null;

                        ignoreTag(reader);
                    }
                } else {
                    ignoreTag(reader);
                }
                

            }
            reader.close();


            return anime;
        } catch (Exception e) {
            log.error("Error downloading anime: " + aid, e);
            return null;
        } finally {
            if (is != null)
                IOUtils.closeQuietly(is);
        }
    }

    public void updateCategoryNames() {
        try {
            InputStream is = new GZIPInputStream(new BufferedInputStream(new URL(String.format("%s&request=categorylist", HTTP_URL)).openStream()));
            new AnimeCategoryParser().parseAnimeCategory(is);
        } catch (Exception e) {
            log.error("Error reading categories.");
        }
    }

    public void updateAnimeNames() {
        try {
            InputStream is = new GZIPInputStream(new BufferedInputStream(new URL(HTTP_NAMES_URL).openStream()));
            new AnimeTitleParser().parseAnimeTitle(is);
        } catch (Exception e) {
            log.error("Error reading categories.");
        }
    }
}
