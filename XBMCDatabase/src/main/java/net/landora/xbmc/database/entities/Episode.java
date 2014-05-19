/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.xbmc.database.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bdickie
 */
@Entity
@Table(name = "episode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Episode.findAll", query = "SELECT e FROM Episode e"),
    @NamedQuery(name = "Episode.findByIdEpisode", query = "SELECT e FROM Episode e WHERE e.idEpisode = :idEpisode"),
    @NamedQuery(name = "Episode.findByFile", query = "SELECT e FROM Episode e WHERE e.file = :file")})
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEpisode")
    private Integer idEpisode;
    @ManyToOne
    @JoinColumn(name = "idFile")
    private VideoFile file;
    @Lob
    @Column(name = "c00")
    private String title;
    @Lob
    @Column(name = "c01")
    private String plot;
    @Lob
    @Column(name = "c02")
    private String votes;
    @Lob
    @Column(name = "c03")
    private String rating;
    @Lob
    @Column(name = "c04")
    private String writerStr;
    @Lob
    @Column(name = "c05")
    private String firstAired;
    @Lob
    @Column(name = "c06")
    private String thumbnailUrl;
    @Lob
    @Column(name = "c07")
    private String thumbnailUrlSpoof;
    @Lob
    @Column(name = "c08")
    private String unused1;
    @Lob
    @Column(name = "c09")
    private String runtime;
    @Lob
    @Column(name = "c10")
    private String directorStr;
    @Lob
    @Column(name = "c11")
    private String productionCode;
    @Column(name = "c12")
    private String seasonNumber;
    @Column(name = "c13")
    private String episodeNumber;
    @Lob
    @Column(name = "c14")
    private String originalTitle;
    @Lob
    @Column(name = "c15")
    private String seasonNumberForSorting;
    @Lob
    @Column(name = "c16")
    private String episodeNumberForSorting;
    @Column(name = "c17")
    private String bookmarkId;
    @Lob
    @Column(name = "c18")
    private String basePath;
    @Lob
    @Column(name = "c19")
    private String parentPathId;
    @Lob
    @Column(name = "c20")
    private String uniqueId;
    @Lob
    @Column(name = "c21")
    private String unused2;
    @Lob
    @Column(name = "c22")
    private String unused3;
    @Lob
    @Column(name = "c23")
    private String unused4;

    @ManyToOne
    @JoinColumn(name = "idShow")
    private TvShow show;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "directorlinkepisode",
            joinColumns = @JoinColumn(name = "idEpisode"),
            inverseJoinColumns = @JoinColumn(name = "idDirector"))
    private Set<Person> director;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "writerlinkepisode",
            joinColumns = @JoinColumn(name = "idEpisode"),
            inverseJoinColumns = @JoinColumn(name = "idWriter"))
    private Set<Person> writer;

    public Episode() {
    }

    public Episode(Integer idEpisode) {
        this.idEpisode = idEpisode;
    }

    public Integer getIdEpisode() {
        return idEpisode;
    }

    public void setIdEpisode(Integer idEpisode) {
        this.idEpisode = idEpisode;
    }

    public VideoFile getFile() {
        return file;
    }

    public void setFile(VideoFile file) {
        this.file = file;
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

    public String getWriterStr() {
        return writerStr;
    }

    public void setWriterStr(String writerStr) {
        this.writerStr = writerStr;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrlSpoof() {
        return thumbnailUrlSpoof;
    }

    public void setThumbnailUrlSpoof(String thumbnailUrlSpoof) {
        this.thumbnailUrlSpoof = thumbnailUrlSpoof;
    }

    public String getUnused1() {
        return unused1;
    }

    public void setUnused1(String unused1) {
        this.unused1 = unused1;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirectorStr() {
        return directorStr;
    }

    public void setDirectorStr(String directorStr) {
        this.directorStr = directorStr;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getSeasonNumberForSorting() {
        return seasonNumberForSorting;
    }

    public void setSeasonNumberForSorting(String seasonNumberForSorting) {
        this.seasonNumberForSorting = seasonNumberForSorting;
    }

    public String getEpisodeNumberForSorting() {
        return episodeNumberForSorting;
    }

    public void setEpisodeNumberForSorting(String episodeNumberForSorting) {
        this.episodeNumberForSorting = episodeNumberForSorting;
    }

    public String getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(String bookmarkId) {
        this.bookmarkId = bookmarkId;
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public TvShow getShow() {
        return show;
    }

    public void setShow(TvShow show) {
        this.show = show;
    }

    public Set<Person> getDirector() {
        return director;
    }

    public void setDirector(Set<Person> director) {
        this.director = director;
    }

    public Set<Person> getWriter() {
        return writer;
    }

    public void setWriter(Set<Person> writer) {
        this.writer = writer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEpisode != null ? idEpisode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Episode)) {
            return false;
        }
        Episode other = (Episode) object;
        if ((this.idEpisode == null && other.idEpisode != null) || (this.idEpisode != null && !this.idEpisode.equals(other.idEpisode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.landora.xbmc.database.Episode[ idEpisode=" + idEpisode + " ]";
    }

}
