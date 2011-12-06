/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.thetvdbinfo.data;

/**
 *
 * @author bdickie
 */
public class TVEpisode implements java.io.Serializable {
    private int episodeId;
    private TVShow show;
    
    private int seasonNumber;
    private int episodeNumber;
    private String name;
    private String summary;

    public TVEpisode() {
    }

    
    
    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public TVShow getShow() {
        return show;
    }

    public void setShow(TVShow show) {
        this.show = show;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TVEpisode other = (TVEpisode) obj;
        if (this.episodeId != other.episodeId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.episodeId;
        return hash;
    }

    
    
    
    
    
}
