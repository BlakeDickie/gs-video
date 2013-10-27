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
@Table(name = "movie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findByIdMovie", query = "SELECT m FROM Movie m WHERE m.idMovie = :idMovie")
})
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMovie")
    private Integer idMovie;
    
    @ManyToOne
    @JoinColumn(name="idFile")
    private VideoFile file;
    
    @Lob
    @Column(name = "c00")
    private String title;
    @Lob
    @Column(name = "c01")
    private String plotSummary;
    @Lob
    @Column(name = "c02")
    private String plotOutline;
    @Lob
    @Column(name = "c03")
    private String tagLine;
    @Lob
    @Column(name = "c04")
    private String votes;
    @Lob
    @Column(name = "c05")
    private String rating;
    @Lob
    @Column(name = "c06")
    private String credits;
    @Lob
    @Column(name = "c07")
    private String year;
    @Lob
    @Column(name = "c08")
    private String thumbUrl;
    @Lob
    @Column(name = "c09")
    private String ident;
    @Lob
    @Column(name = "c10")
    private String sortTitle;
    @Lob
    @Column(name = "c11")
    private String runtime;
    @Lob
    @Column(name = "c12")
    private String mpaa;
    @Lob
    @Column(name = "c13")
    private String top250;
    @Lob
    @Column(name = "c14")
    private String genreStr;
    @Lob
    @Column(name = "c15")
    private String directorStr;
    @Lob
    @Column(name = "c16")
    private String originalTitle;
    @Lob
    @Column(name = "c17")
    private String thumbUrlSpoof;
    @Lob
    @Column(name = "c18")
    private String studioStr;
    @Lob
    @Column(name = "c19")
    private String trailer;
    @Lob
    @Column(name = "c20")
    private String fanart;
    @Lob
    @Column(name = "c21")
    private String countryStr;
    @Lob
    @Column(name = "c22")
    private String basePath;
    @Lob
    @Column(name = "c23")
    private String parentPathId;
    
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="idSet")
    private MovieSet set;
    
    @ManyToMany(cascade= CascadeType.PERSIST)
    @JoinTable(name="studiolinkmovie",
            joinColumns=@JoinColumn(name="idMovie"),
            inverseJoinColumns=@JoinColumn(name="idStudio"))
    private Set<Studio> studio;
    
    @ManyToMany(cascade= CascadeType.PERSIST)
    @JoinTable(name="genrelinkmovie",
            joinColumns=@JoinColumn(name="idMovie"),
            inverseJoinColumns=@JoinColumn(name="idGenre"))
    private Set<Genre> genre;
    
    @ManyToMany(cascade= CascadeType.PERSIST)
    @JoinTable(name="countrylinkmovie",
            joinColumns=@JoinColumn(name="idMovie"),
            inverseJoinColumns=@JoinColumn(name="idCountry"))
    private Set<Country> country;
    
    @ManyToMany(cascade= CascadeType.PERSIST)
    @JoinTable(name="directorlinkmovie",
            joinColumns=@JoinColumn(name="idMovie"),
            inverseJoinColumns=@JoinColumn(name="idDirector"))
    private Set<Person> director;
    
    @ManyToMany(cascade= CascadeType.PERSIST)
    @JoinTable(name="writerlinkmovie",
            joinColumns=@JoinColumn(name="idMovie"),
            inverseJoinColumns=@JoinColumn(name="idWriter"))
    private Set<Person> writer;

    public Movie() {
    }

    public Movie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
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

    public String getPlotSummary() {
        return plotSummary;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    public String getPlotOutline() {
        return plotOutline;
    }

    public void setPlotOutline(String plotOutline) {
        this.plotOutline = plotOutline;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
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

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getMpaa() {
        return mpaa;
    }

    public void setMpaa(String mpaa) {
        this.mpaa = mpaa;
    }

    public String getTop250() {
        return top250;
    }

    public void setTop250(String top250) {
        this.top250 = top250;
    }

    public String getGenreStr() {
        return genreStr;
    }

    public void setGenreStr(String genreStr) {
        this.genreStr = genreStr;
    }

    public String getDirectorStr() {
        return directorStr;
    }

    public void setDirectorStr(String directorStr) {
        this.directorStr = directorStr;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getThumbUrlSpoof() {
        return thumbUrlSpoof;
    }

    public void setThumbUrlSpoof(String thumbUrlSpoof) {
        this.thumbUrlSpoof = thumbUrlSpoof;
    }

    public String getStudioStr() {
        return studioStr;
    }

    public void setStudioStr(String studioStr) {
        this.studioStr = studioStr;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getFanart() {
        return fanart;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public String getCountryStr() {
        return countryStr;
    }

    public void setCountryStr(String countryStr) {
        this.countryStr = countryStr;
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

    public MovieSet getSet() {
        return set;
    }

    public void setSet(MovieSet set) {
        this.set = set;
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

    public Set<Country> getCountry() {
        return country;
    }

    public void setCountry(Set<Country> country) {
        this.country = country;
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
        hash += (idMovie != null ? idMovie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.idMovie == null && other.idMovie != null) || (this.idMovie != null && !this.idMovie.equals(other.idMovie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.landora.xbmc.database.Movie[ idMovie=" + idMovie + " ]";
    }
    
}
