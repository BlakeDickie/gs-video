/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.gsuiutils.ContextProducer;
import net.landora.gsuiutils.UIUtils;

/**
 *
 * @author bdickie
 */
public class AnimeEpisode implements java.io.Serializable, ContextProducer {
    private AnimeStub anime;
    private int episodeId;
    private String episodeNumber;
    private Integer length;
    private Float rating;
    private Integer ratingVotes;
    private String nameEnglish;
    private String nameRomaji;
    private String nameKanji;
    private Calendar airDate;

    public AnimeEpisode() {
    }

    public Calendar getAirDate() {
        return airDate;
    }

    public void setAirDate(Calendar airDate) {
        this.airDate = airDate;
    }

    public AnimeStub getAnime() {
        return anime;
    }

    public void setAnime(AnimeStub anime) {
        this.anime = anime;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    private static final Pattern EPNAME_PATTERN = Pattern.compile("([SCTPO]?)(\\d+)", Pattern.CASE_INSENSITIVE);

    public String getEpisodeNumber() {
        Matcher m = EPNAME_PATTERN.matcher(episodeNumber);
        if (m.matches()) {
            Integer numEps = null;
            if (m.group(1).equals("")) {
                numEps = anime.getEpisodeCount();
            }
            
            if (numEps == null || numEps.intValue() < 1)
                numEps = 99;
            
            NumberFormat format = NumberFormat.getInstance();
            format.setGroupingUsed(false);
            format.setMinimumIntegerDigits((int)Math.log10(numEps) + 1);;
            return m.group(1) + format.format(Integer.parseInt(m.group(2)));
        }
        
        return episodeNumber;
    }
    
    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameKanji() {
        return nameKanji;
    }

    public void setNameKanji(String nameKanji) {
        this.nameKanji = nameKanji;
    }

    public String getNameRomaji() {
        return nameRomaji;
    }

    public void setNameRomaji(String nameRomaji) {
        this.nameRomaji = nameRomaji;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getRatingVotes() {
        return ratingVotes;
    }

    public void setRatingVotes(Integer ratingVotes) {
        this.ratingVotes = ratingVotes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeEpisode other = (AnimeEpisode) obj;
        if (this.episodeId != other.episodeId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.episodeId;
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", episodeNumber, (nameEnglish != null ? nameEnglish : nameRomaji));
    }

    public Integer getNormalEpisodeNumber() {
        if (Character.isDigit(episodeNumber.charAt(0)))
            return Integer.parseInt(episodeNumber);
        else
            return null;
    }

    @Override
    public void addContentObjects(Collection<Object> addTo) {
        addTo.add(this);
        UIUtils.addContentObject(anime, addTo);
    }

}
