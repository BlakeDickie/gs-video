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
package net.landora.xbmc.database.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bdickie
 */
@Entity
@Table(name = "tvshow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TvShow.findAll", query = "SELECT t FROM TvShow t"),
    @NamedQuery(name = "TvShow.findByIdShow", query = "SELECT t FROM TvShow t WHERE t.idShow = :idShow")})
public class TvShow implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idShow")
    private Integer idShow;
    @Lob
    @Column(name = "c00")
    private String title;
    @Lob
    @Column(name = "c01")
    private String plot;
    @Lob
    @Column(name = "c02")
    private String status;
    @Lob
    @Column(name = "c03")
    private String votes;
    @Lob
    @Column(name = "c04")
    private String rating;
    @Lob
    @Column(name = "c05")
    private String premiered;
    @Lob
    @Column(name = "c06")
    private String thumbUrl;
    @Lob
    @Column(name = "c07")
    private String thumbUrlSpoof;
    @Lob
    @Column(name = "c08")
    private String genreStr;
    @Lob
    @Column(name = "c09")
    private String originalTitle;
    @Lob
    @Column(name = "c10")
    private String episodeGuide;
    @Lob
    @Column(name = "c11")
    private String fanart;
    @Lob
    @Column(name = "c12")
    private String ident;
    @Lob
    @Column(name = "c13")
    private String mpaa;
    @Lob
    @Column(name = "c14")
    private String studioStr;
    @Lob
    @Column(name = "c15")
    private String sortTitle;
    @Lob
    @Column(name = "c16")
    private String basePath;
    @Lob
    @Column(name = "c17")
    private String parentPathId;
    @Lob
    @Column(name = "c18")
    private String unused1;
    @Lob
    @Column(name = "c19")
    private String unused2;
    @Lob
    @Column(name = "c20")
    private String unused3;
    @Lob
    @Column(name = "c21")
    private String unused4;
    @Lob
    @Column(name = "c22")
    private String unused5;
    @Lob
    @Column(name = "c23")
    private String unused6;
    
    @ManyToMany()
    @JoinTable(name="studiolinktvshow",
            joinColumns=@JoinColumn(name="idShow"),
            inverseJoinColumns=@JoinColumn(name="idStudio"))
    private Set<Studio> studio;
    
    @ManyToMany()
    @JoinTable(name="genrelinktvshow",
            joinColumns=@JoinColumn(name="idShow"),
            inverseJoinColumns=@JoinColumn(name="idGenre"))
    private Set<Genre> genre;
    
    @ManyToMany()
    @JoinTable(name="directorlinktvshow",
            joinColumns=@JoinColumn(name="idShow"),
            inverseJoinColumns=@JoinColumn(name="idDirector"))
    private Set<Person> director;

    public TvShow() {
    }

    public TvShow(Integer idShow) {
        this.idShow = idShow;
    }

    public Integer getIdShow() {
        return idShow;
    }

    public void setIdShow(Integer idShow) {
        this.idShow = idShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrlSpoof() {
        return thumbUrlSpoof;
    }

    public void setThumbUrlSpoof(String thumbUrlSpoof) {
        this.thumbUrlSpoof = thumbUrlSpoof;
    }

    public String getGenreStr() {
        return genreStr;
    }

    public void setGenreStr(String genreStr) {
        this.genreStr = genreStr;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getEpisodeGuide() {
        return episodeGuide;
    }

    public void setEpisodeGuide(String episodeGuide) {
        this.episodeGuide = episodeGuide;
    }

    public String getFanart() {
        return fanart;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getMpaa() {
        return mpaa;
    }

    public void setMpaa(String mpaa) {
        this.mpaa = mpaa;
    }

    public String getStudioStr() {
        return studioStr;
    }

    public void setStudioStr(String studioStr) {
        this.studioStr = studioStr;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getParentPathId() {
        return parentPathId;
    }

    public void setParentPathId(String parentPathId) {
        this.parentPathId = parentPathId;
    }

    public String getUnused1() {
        return unused1;
    }

    public void setUnused1(String unused1) {
        this.unused1 = unused1;
    }

    public String getUnused2() {
        return unused2;
    }

    public void setUnused2(String unused2) {
        this.unused2 = unused2;
    }

    public String getUnused3() {
        return unused3;
    }

    public void setUnused3(String unused3) {
        this.unused3 = unused3;
    }

    public String getUnused4() {
        return unused4;
    }

    public void setUnused4(String unused4) {
        this.unused4 = unused4;
    }

    public String getUnused5() {
        return unused5;
    }

    public void setUnused5(String unused5) {
        this.unused5 = unused5;
    }

    public String getUnused6() {
        return unused6;
    }

    public void setUnused6(String unused6) {
        this.unused6 = unused6;
    }

    public Set<Studio> getStudio() {
        return studio;
    }

    public void setStudio(Set<Studio> studio) {
        this.studio = studio;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public void setGenre(Set<Genre> genre) {
        this.genre = genre;
    }

    public Set<Person> getDirector() {
        return director;
    }

    public void setDirector(Set<Person> director) {
        this.director = director;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idShow != null ? idShow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TvShow)) {
            return false;
        }
        TvShow other = (TvShow) object;
        if ((this.idShow == null && other.idShow != null) || (this.idShow != null && !this.idShow.equals(other.idShow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.landora.xbmc.database.TvShow[ idShow=" + idShow + " ]";
    }
    
}
