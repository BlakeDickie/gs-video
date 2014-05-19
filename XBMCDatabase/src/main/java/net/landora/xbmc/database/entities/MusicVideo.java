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
@Table(name = "musicvideo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MusicVideo.findAll", query = "SELECT m FROM MusicVideo m"),
    @NamedQuery(name = "MusicVideo.findByIdMVideo", query = "SELECT m FROM MusicVideo m WHERE m.idMVideo = :idMVideo")})
public class MusicVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMVideo")
    private Integer idMVideo;
    @ManyToOne
    @JoinColumn(name = "idFile")
    private VideoFile file;
    @Lob
    @Column(name = "c00")
    private String title;
    @Lob
    @Column(name = "c01")
    private String thumbUrl;
    @Lob
    @Column(name = "c02")
    private String thumbUrlSpoof;
    @Lob
    @Column(name = "c03")
    private String unused1;
    @Lob
    @Column(name = "c04")
    private String runtime;
    @Lob
    @Column(name = "c05")
    private String directorStr;
    @Lob
    @Column(name = "c06")
    private String studioStr;
    @Lob
    @Column(name = "c07")
    private String year;
    @Lob
    @Column(name = "c08")
    private String plot;
    @Lob
    @Column(name = "c09")
    private String album;
    @Lob
    @Column(name = "c10")
    private String artistStr;
    @Lob
    @Column(name = "c11")
    private String genreStr;
    @Lob
    @Column(name = "c12")
    private String track;
    @Lob
    @Column(name = "c13")
    private String basePath;
    @Lob
    @Column(name = "c14")
    private String parentPathId;
    @Lob
    @Column(name = "c15")
    private String c15;
    @Lob
    @Column(name = "c16")
    private String c16;
    @Lob
    @Column(name = "c17")
    private String c17;
    @Lob
    @Column(name = "c18")
    private String c18;
    @Lob
    @Column(name = "c19")
    private String c19;
    @Lob
    @Column(name = "c20")
    private String c20;
    @Lob
    @Column(name = "c21")
    private String c21;
    @Lob
    @Column(name = "c22")
    private String c22;
    @Lob
    @Column(name = "c23")
    private String c23;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "studiolinkmusicvideo",
            joinColumns = @JoinColumn(name = "idMVideo"),
            inverseJoinColumns = @JoinColumn(name = "idStudio"))
    private Set<Studio> studio;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "genrelinkmusicvideo",
            joinColumns = @JoinColumn(name = "idMVideo"),
            inverseJoinColumns = @JoinColumn(name = "idGenre"))
    private Set<Genre> genre;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "directorlinkmusicvideo",
            joinColumns = @JoinColumn(name = "idMVideo"),
            inverseJoinColumns = @JoinColumn(name = "idDirector"))
    private Set<Person> director;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "artistlinkmusicvideo",
            joinColumns = @JoinColumn(name = "idMVideo"),
            inverseJoinColumns = @JoinColumn(name = "idArtist"))
    private Set<Person> artist;

    public MusicVideo() {
    }

    public MusicVideo(Integer idMVideo) {
        this.idMVideo = idMVideo;
    }

    public Integer getIdMVideo() {
        return idMVideo;
    }

    public void setIdMVideo(Integer idMVideo) {
        this.idMVideo = idMVideo;
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

    public String getStudioStr() {
        return studioStr;
    }

    public void setStudioStr(String studioStr) {
        this.studioStr = studioStr;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtistStr() {
        return artistStr;
    }

    public void setArtistStr(String artistStr) {
        this.artistStr = artistStr;
    }

    public String getGenreStr() {
        return genreStr;
    }

    public void setGenreStr(String genreStr) {
        this.genreStr = genreStr;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
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

    public String getC15() {
        return c15;
    }

    public void setC15(String c15) {
        this.c15 = c15;
    }

    public String getC16() {
        return c16;
    }

    public void setC16(String c16) {
        this.c16 = c16;
    }

    public String getC17() {
        return c17;
    }

    public void setC17(String c17) {
        this.c17 = c17;
    }

    public String getC18() {
        return c18;
    }

    public void setC18(String c18) {
        this.c18 = c18;
    }

    public String getC19() {
        return c19;
    }

    public void setC19(String c19) {
        this.c19 = c19;
    }

    public String getC20() {
        return c20;
    }

    public void setC20(String c20) {
        this.c20 = c20;
    }

    public String getC21() {
        return c21;
    }

    public void setC21(String c21) {
        this.c21 = c21;
    }

    public String getC22() {
        return c22;
    }

    public void setC22(String c22) {
        this.c22 = c22;
    }

    public String getC23() {
        return c23;
    }

    public void setC23(String c23) {
        this.c23 = c23;
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

    public Set<Person> getArtist() {
        return artist;
    }

    public void setArtist(Set<Person> artist) {
        this.artist = artist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMVideo != null ? idMVideo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusicVideo)) {
            return false;
        }
        MusicVideo other = (MusicVideo) object;
        if ((this.idMVideo == null && other.idMVideo != null) || (this.idMVideo != null && !this.idMVideo.equals(other.idMVideo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.landora.xbmc.database.MusicVideo[ idMVideo=" + idMVideo + " ]";
    }

}
