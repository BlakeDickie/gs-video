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
@Table(name = "sets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovieSet.findAll", query = "SELECT s FROM MovieSet s"),
    @NamedQuery(name = "MovieSet.findByName", query = "SELECT s FROM MovieSet s WHERE s.setName = :name")})
public class MovieSet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSet")
    private Integer idSet;
    @Lob
    @Column(name = "strSet")
    private String setName;

    public MovieSet() {
    }

    public MovieSet(Integer idSet) {
        this.idSet = idSet;
    }

    public Integer getIdSet() {
        return idSet;
    }

    public void setIdSet(Integer idSet) {
        this.idSet = idSet;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSet != null ? idSet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieSet)) {
            return false;
        }
        MovieSet other = (MovieSet) object;
        if ((this.idSet == null && other.idSet != null) || (this.idSet != null && !this.idSet.equals(other.idSet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return setName;
    }
    
}
