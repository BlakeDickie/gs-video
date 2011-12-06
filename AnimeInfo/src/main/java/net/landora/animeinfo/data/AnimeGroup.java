/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import net.landora.gsuiutils.ContextActionObject;

/**
 *
 * @author bdickie
 */
@ContextActionObject("Actions/AnimeGroup")
public class AnimeGroup implements java.io.Serializable {

    private int groupId;
    private String shortName;
    private String lostName;
    private String url;
    private boolean fullyLoaded;

    public AnimeGroup() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getLostName() {
        return lostName;
    }

    public void setLostName(String lostName) {
        this.lostName = lostName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isFullyLoaded() {
        return fullyLoaded;
    }

    public void setFullyLoaded(boolean fullyLoaded) {
        this.fullyLoaded = fullyLoaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeGroup other = (AnimeGroup) obj;
        if (this.groupId != other.groupId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.groupId;
        return hash;
    }

    @Override
    public String toString() {
        return getShortName();
    }


}
