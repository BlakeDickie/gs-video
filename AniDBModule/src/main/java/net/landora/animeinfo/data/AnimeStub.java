/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import java.util.Calendar;

/**
 *
 * @author bdickie
 */
public class AnimeStub implements java.io.Serializable {
    private int animeId;
    private Calendar lastLoaded;
    private String nameMain;
    private String nameEnglish;
    private Integer episodeCount;

    private Calendar startDate;
    private Calendar endDate;
    
    private String type;
    private String description;

    private Float ratingPermanent;
    private Integer ratingPermanentVotes;
    private Float ratingTemporary;
    private Integer ratingTemporaryVotes;

    private String pictureFileName;

    private boolean hentai;
    

    public AnimeStub() {
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public Calendar getLastLoaded() {
        return lastLoaded;
    }

    public void setLastLoaded(Calendar lastLoaded) {
        this.lastLoaded = lastLoaded;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getDisplayName() {
        if (nameEnglish != null)
            return nameEnglish;
        else
            return nameMain;
    }

    public String getNameMain() {
        return nameMain;
    }

    public void setNameMain(String nameMain) {
        this.nameMain = nameMain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }

    public Float getRatingPermanent() {
        return ratingPermanent;
    }

    public void setRatingPermanent(Float ratingPermanent) {
        this.ratingPermanent = ratingPermanent;
    }

    public Float getRatingTemporary() {
        return ratingTemporary;
    }

    public void setRatingTemporary(Float ratingTemporary) {
        this.ratingTemporary = ratingTemporary;
    }

    public Integer getRatingPermanentVotes() {
        return ratingPermanentVotes;
    }

    public void setRatingPermanentVotes(Integer ratingPermanentVotes) {
        this.ratingPermanentVotes = ratingPermanentVotes;
    }

    public Integer getRatingTemporaryVotes() {
        return ratingTemporaryVotes;
    }

    public void setRatingTemporaryVotes(Integer ratingTemporaryVotes) {
        this.ratingTemporaryVotes = ratingTemporaryVotes;
    }

    

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public boolean isHentai() {
        return hentai;
    }

    public void setHentai(boolean hentai) {
        this.hentai = hentai;
    }

    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeStub other = (AnimeStub) obj;
        if (this.animeId != other.animeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }



    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.animeId;
        return hash;
    }

    
    
}
