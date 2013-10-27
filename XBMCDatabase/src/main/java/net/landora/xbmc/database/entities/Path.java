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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bdickie
 */
@Entity
@Table(name = "path")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Path.findAll", query = "SELECT p FROM Path p"),
    @NamedQuery(name = "Path.findByIdPath", query = "SELECT p FROM Path p WHERE p.idPath = :idPath")})
public class Path implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPath")
    private Integer idPath;
    @Lob
    @Column(name = "strPath")
    private String path;
    @Lob
    @Column(name = "strContent")
    private String content;
    @Lob
    @Column(name = "strScraper")
    private String scraper;
    @Lob
    @Column(name = "strHash")
    private String hash;
    @Column(name = "scanRecursive")
    private Integer scanRecursive;
    @Column(name = "useFolderNames")
    private Boolean useFolderNames;
    @Lob
    @Column(name = "strSettings")
    private String strSettings;
    @Column(name = "noUpdate")
    private Boolean noUpdate;
    @Column(name = "exclude")
    private Boolean exclude;
    @Lob
    @Column(name = "dateAdded")
    private String dateAdded;

    public Path() {
    }

    public Path(Integer idPath) {
        this.idPath = idPath;
    }

    public Integer getIdPath() {
        return idPath;
    }

    public void setIdPath(Integer idPath) {
        this.idPath = idPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScraper() {
        return scraper;
    }

    public void setScraper(String scraper) {
        this.scraper = scraper;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public Integer getScanRecursive() {
        return scanRecursive;
    }

    public void setScanRecursive(Integer scanRecursive) {
        this.scanRecursive = scanRecursive;
    }

    public Boolean getUseFolderNames() {
        return useFolderNames;
    }

    public void setUseFolderNames(Boolean useFolderNames) {
        this.useFolderNames = useFolderNames;
    }

    public String getStrSettings() {
        return strSettings;
    }

    public void setStrSettings(String strSettings) {
        this.strSettings = strSettings;
    }

    public Boolean getNoUpdate() {
        return noUpdate;
    }

    public void setNoUpdate(Boolean noUpdate) {
        this.noUpdate = noUpdate;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPath != null ? idPath.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Path)) {
            return false;
        }
        Path other = (Path) object;
        if ((this.idPath == null && other.idPath != null) || (this.idPath != null && !this.idPath.equals(other.idPath))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.landora.xbmc.database.Path[ idPath=" + idPath + " ]";
    }
    
}
