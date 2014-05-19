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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "bookmark")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bookmark.findAll", query = "SELECT b FROM Bookmark b"),
    @NamedQuery(name = "Bookmark.findByIdBookmark", query = "SELECT b FROM Bookmark b WHERE b.idBookmark = :idBookmark")})
public class Bookmark implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBookmark")
    private Integer idBookmark;
    @ManyToOne
    @JoinColumn(name = "idFile")
    private VideoFile file;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "timeInSeconds")
    private Double timeInSeconds;
    @Column(name = "totalTimeInSeconds")
    private Double totalTimeInSeconds;
    @Lob
    @Column(name = "thumbNailImage")
    private String thumbNailImage;
    @Lob
    @Column(name = "player")
    private String player;
    @Lob
    @Column(name = "playerState")
    private String playerState;
    @Column(name = "type")
    private Integer type;

    public Bookmark() {
    }

    public Bookmark(Integer idBookmark) {
        this.idBookmark = idBookmark;
    }

    public Integer getIdBookmark() {
        return idBookmark;
    }

    public void setIdBookmark(Integer idBookmark) {
        this.idBookmark = idBookmark;
    }

    public VideoFile getFile() {
        return file;
    }

    public void setFile(VideoFile file) {
        this.file = file;
    }

    public Double getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(Double timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public Double getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }

    public void setTotalTimeInSeconds(Double totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }

    public String getThumbNailImage() {
        return thumbNailImage;
    }

    public void setThumbNailImage(String thumbNailImage) {
        this.thumbNailImage = thumbNailImage;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayerState() {
        return playerState;
    }

    public void setPlayerState(String playerState) {
        this.playerState = playerState;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBookmark != null ? idBookmark.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookmark)) {
            return false;
        }
        Bookmark other = (Bookmark) object;
        if ((this.idBookmark == null && other.idBookmark != null) || (this.idBookmark != null && !this.idBookmark.equals(other.idBookmark))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.landora.xbmc.database.Bookmark[ idBookmark=" + idBookmark + " ]";
    }

}
