/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.mylistreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import net.landora.animeinfo.data.Anime;
import net.landora.animeinfo.data.AnimeCategoryWeight;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.animeinfo.data.AnimeGroup;
import net.landora.animeinfo.data.AnimeListItem;
import net.landora.animeinfo.data.AnimeName;
import net.landora.animeinfo.data.AnimeRelation;
import org.apache.commons.io.IOUtils;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;

/**
 *
 * @author bdickie
 */
public class ListReader {

    public ListReader() {
    }
    

    private Map<String,String> values;
    
    public boolean download(URL input) throws Throwable {
        return download(input.openStream());
    }
    
    public boolean download(InputStream input) throws Throwable {
        TarInputStream is = null;
        try {
            is = new TarInputStream(new GZIPInputStream( input ) );

            TarEntry entry;
            while((entry = is.getNextEntry()) != null) {
                if (!entry.getName().equalsIgnoreCase("mylist.xml"))
                    continue;

                XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(is);
                reader.nextTag();
                reader.require(XMLStreamReader.START_ELEMENT, null, "my_anime_list");
                values = new HashMap<String, String>();
                StringBuilder value = new StringBuilder();

                while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                    reader.require(XMLStreamReader.START_ELEMENT, null, null);
                    String tableName = reader.getLocalName();

                    values.clear();

                    while(reader.nextTag() != XMLStreamReader.END_ELEMENT) {
                        String valueName = reader.getLocalName();

                        value.setLength(0);
                        while(reader.next() != XMLStreamReader.END_ELEMENT) {
                            switch(reader.getEventType()) {
                                case XMLStreamReader.CDATA:
                                case XMLStreamReader.CHARACTERS:
                                case XMLStreamReader.SPACE:
                                    value.append(reader.getText());
                            }
                        }
                        reader.require(XMLStreamReader.END_ELEMENT, null, valueName);
                        values.put(valueName, value.toString());

                    }
                    reader.require(XMLStreamReader.END_ELEMENT, null, tableName);

                    handleTable(tableName);

                }
                reader.require(XMLStreamReader.END_ELEMENT, null, "my_anime_list");

                saveLast();
            }
            return true;
        } finally {
            if (is != null)
                IOUtils.closeQuietly(is);
            else if (input != null)
                IOUtils.closeQuietly(input);
        }
    }
    
    
    private void saveLast() {
        saveLastAnime();
        saveLastEpisode();
        saveLastFile();
    }
    
    private Calendar exportDate;
    
    private void handleTable(String tableName) throws ParseException {
        if (tableName.equals("anime")) {
            handleAnime();
        } else if (tableName.equals("title")) {
            handleTitle();
        } else if (tableName.equals("anime_category")) {
            handleAnimeCategory();
        } else if (tableName.equals("episode")) {
            handleEpisode();
        } else if (tableName.equals("file")) {
            handleFile();
        } else if (tableName.equals("user_info")) {
            exportDate = getDateTime("ExportDate");
        }
    }
    
    private String getString(String name) {
        String value = values.get(name);
        if (value == null)
            return null;
        return value.replaceAll(Pattern.quote("<br />"), "\n");
    }
    
    private Integer getInt(String name) {
        String value = getString(name);
        if (value == null || value.equals("") || value.equals("-"))
            return null;
        return Integer.parseInt(value);
    }
    
    private Long getLong(String name) {
        String value = getString(name);
        if (value == null || value.equals("") || value.equals("-"))
            return null;
        return Long.parseLong(value);
    }
    
    private Float getFloat(String name) {
        String value = getString(name);
        if (value == null || value.equals("") || value.equals("-"))
            return null;
        return Float.parseFloat(value);
    }
    
    private Boolean getBoolean(String name) {
        String value = getString(name);
        if (value == null || value.equals("") || value.equals("-"))
            return null;
        return getString(name).equals("1");
    }
    
    private SimpleDateFormat dateTimeFormat;
    private SimpleDateFormat dateFormat;
    
    {
        dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    public Calendar getDateTime(String name) throws ParseException {
        String value = getString(name);
        if (value == null || value.equals("0") || value.equals("") || value.equals("-")  || value.equals("?"))
            return null;
        
        Date date;
        
        try {
            date = dateTimeFormat.parse(value);
        } catch (ParseException e) {
            date = dateFormat.parse(value);
        }
        
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    
    private Anime anime;
    
    private void handleAnime() throws ParseException {
        saveLast();
        
        int animeId = getInt("AnimeID");
        anime = AnimeDBA.getAnime(animeId);
        if (anime == null) {
            anime = new Anime();
            anime.setNames(new ArrayList<AnimeName>());
            anime.setCategories(new ArrayList<AnimeCategoryWeight>());
            anime.setRelations(new ArrayList<AnimeRelation>());
            anime.setAnimeId(animeId);
        }
        
        anime.getNames().clear();
        anime.getCategories().clear();
        
        anime.setLastLoaded(exportDate);
        
        anime.setNameEnglish(getString("NameEnglish"));
        if (anime.getNameEnglish().equals(""))
            anime.setNameEnglish(null);
        anime.setNameMain(getString("Name"));
        anime.setDescription(getString("AnimeDescription"));
        anime.setStartDate(getDateTime("StartDate"));
        anime.setEndDate(getDateTime("EndDate"));
        anime.setEpisodeCount(getInt("Eps"));
        anime.setType(getString("TypeName"));
        anime.setRatingPermanent(getFloat("Rating"));
        anime.setRatingPermanentVotes(anime.getRatingPermanent() == null ? null : getInt("Votes"));
        anime.setRatingTemporary(getFloat("TempRating"));
        anime.setRatingTemporaryVotes(anime.getRatingTemporary() == null ? null : getInt("TempVotes"));
        anime.setHentai(getBoolean("Hentai"));
        
    }
    
    private void handleTitle() throws ParseException {
        if (anime == null)
            return;
        
        AnimeName name = new AnimeName();
        name.setAnime(anime);
        String language = getString("LanguageName");
        String converted = languageConvert.get(language);
        
        name.setLanguage(converted == null ? language : converted);
        name.setType(getString("TitleTypeName"));
        name.setName(getString("Name"));
        
        anime.getNames().add(name);
        
    }
    
    private void handleAnimeCategory() throws ParseException {
        if (anime == null)
            return;
        
        AnimeCategoryWeight category = new AnimeCategoryWeight();
        category.setAnime(anime);
        category.setCategory(AnimeDBA.getAnimeCategory(getInt("CategoryID")));
        category.setWeight(getInt("CategoryWeight") / 100);
        
        anime.getCategories().add(category);
        
    }
    
    private void saveLastAnime() {
        if (anime == null)
            return;
        AnimeDBA.saveAnimeWithNames(anime);
        anime = null;
    }
    
    
    private final static Map<String,String> languageConvert;
    static {
        languageConvert = new HashMap<String, String>();
        
        languageConvert.put("english", "en");
        languageConvert.put("japanese", "ja");
        languageConvert.put("japanese (transcription)", "x-jat");
        languageConvert.put("spanish", "es");
        languageConvert.put("arabic", "ar");
        languageConvert.put("brazilian portuguese", "pt-BR");
        languageConvert.put("chinese (simplified)", "zh-Hans");
        
    }
    
    
    
    private AnimeEpisode episode;
    
    private void handleEpisode() throws ParseException {
        saveLast();
        
        int episodeId = getInt("EpID");
        episode = AnimeDBA.getAnimeEpisode(episodeId);
        if (episode == null) {
            episode = new AnimeEpisode();
            episode.setEpisodeId(episodeId);
            episode.setAnime(AnimeDBA.getAnime(getInt("AnimeID")));
        }
        
        episode.setLength(getInt("EpLength"));
        episode.setRating(getFloat("EpRating"));
        episode.setRatingVotes(episode.getRating() == null ? null : getInt("EpVotes"));
        episode.setNameEnglish(getString("EpName"));
        episode.setNameKanji(getString("EpNameKanji"));
        episode.setNameRomaji(getString("EpNameRomaji"));
        episode.setAirDate(getDateTime("EpAired"));
        episode.setEpisodeNumber(getString("EpNo"));
    }
    
    private void saveLastEpisode() {
        if (episode == null)
            return;
        
        AnimeDBA.saveEpisode(episode);
        episode = null;
    }
    
    
    
    private AnimeFile file;
    private AnimeListItem listItem;
    
    private void handleFile() throws ParseException {
        saveLast();
        
        int fileId = getInt("FID");
        
        file = AnimeDBA.getAnimeFile(fileId);
        if (file == null) {
            file = new AnimeFile();
            file.setFileId(fileId);
            file.setEpisode(AnimeDBA.getAnimeEpisode(getInt("EpID")));
        }
        
        boolean generic = getBoolean("Generic");
        file.setGeneric(generic);
        
        if (!generic) {
            
            int groupId = getInt("GID");
            if (groupId > 0) {
                AnimeGroup group = AnimeDBA.getAnimeGroup(groupId);
                if (group == null) {
                    group = new AnimeGroup();
                    group.setGroupId(groupId);
                    group.setLostName(getString("GName"));
                    group.setShortName(getString("GShortName"));
                    AnimeDBA.saveGroup(group);
                }
                file.setGroup(group);
            }
            
            file.setEd2k(getString("ed2kHash"));
            file.setSize(getLong("Size"));
            
            int state = getInt("State");
            
            if ((state & 1) != 0)
                file.setCrcValid(true);
            else if ((state & 2) != 0)
                file.setCrcValid(false);
            
            file.setVersion(1);
            if ((state & 4) != 0)
                file.setVersion(2);
            else if ((state & 8) != 0)
                file.setVersion(3);
            else if ((state & 16) != 0)
                file.setVersion(4);
            else if ((state & 32) != 0)
                file.setVersion(5);
            
            if ((state & 64) != 0)
                file.setCensored(false);
            else if ((state & 128) != 0)
                file.setCensored(false);
            
            
            file.setVideoResolution(getString("ResName"));
            file.setFileType(getString("FileType"));
            file.setVideoCodec(getString("VCodecName"));
            file.setSource(getString("TypeName"));
            
        } else {
            file.setGroup(null);
            file.setCrcValid(null);
            file.setVersion(null);
            file.setCensored(null);
            file.setEd2k(null);
            file.setSize(null);
            file.setFileType("");
            file.setVideoResolution("");
            file.setVideoCodec("");
            file.setSource("");
        }
        
        listItem = AnimeDBA.getAnimeListByFileId(fileId);
        if (listItem == null) {
            listItem = new AnimeListItem();
            listItem.setFile(file);
        }
        
        listItem.setStateId(getInt("MyState"));
        listItem.setFileStateId(getInt("MyFileState"));
        listItem.setViewDate(getDateTime("ViewDate"));
        listItem.setAddedDate(getDateTime("ListDate"));
        listItem.setStorage(getString("Storage"));
        listItem.setSource(getString("Source"));
        listItem.setOther(getString("Other"));
        
    }
    
    private void saveLastFile() {
        if (file == null)
            return;
        
        AnimeDBA.saveFile(file);
        AnimeDBA.saveListItem(listItem);
        
        file = null;
        listItem = null;
    }
    
}
