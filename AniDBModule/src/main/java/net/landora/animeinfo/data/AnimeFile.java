/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import java.util.Collection;
import net.landora.video.ui.ContextProducer;
import net.landora.video.utils.UIUtils;
/**
 *
 * @author bdickie
 */
public class AnimeFile implements java.io.Serializable, ContextProducer {
    
    public static final int SAVE_REVISION = 2;

    private int fileId;
    private AnimeEpisode episode;
    private AnimeGroup group;

    private Boolean crcValid;
    private Integer version;
    private Boolean censored;

    private Long size;
    private String ed2k;
    
    private String source;
    private String videoCodec;
    private String videoResolution;
    private String fileType;
    private int currentSaveRevision;

    private boolean generic;

    public AnimeFile() {
    }

    public Boolean getCensored() {
        return censored;
    }

    public void setCensored(Boolean censored) {
        this.censored = censored;
    }

    public Boolean getCrcValid() {
        return crcValid;
    }

    public void setCrcValid(Boolean crcValid) {
        this.crcValid = crcValid;
    }

    public AnimeEpisode getEpisode() {
        return episode;
    }

    public void setEpisode(AnimeEpisode episode) {
        this.episode = episode;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public AnimeGroup getGroup() {
        return group;
    }

    public void setGroup(AnimeGroup group) {
        this.group = group;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getEd2k() {
        return ed2k;
    }

    public void setEd2k(String ed2k) {
        this.ed2k = ed2k;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    

    public void setState(int state) {
        crcValid = null;
        version = null;
        censored = null;

        if ((state & FILE_CRCOK) != 0)
            crcValid = true;
        else if ((state & FILE_CRCERR) != 0)
            crcValid = false;

        if ((state & FILE_ISV2) != 0)
            version = 2;
        else if ((state & FILE_ISV3) != 0)
            version = 3;
        else if ((state & FILE_ISV4) != 0)
            version = 4;
        else if ((state & FILE_ISV5) != 0)
            version = 5;

        if ((state & FILE_CEN) != 0)
            censored = true;
        else if ((state & FILE_UNC) != 0)
            censored = false;
    }

    private static final int FILE_CRCOK = 1;
    private static final int FILE_CRCERR = 2;
    private static final int FILE_ISV2 = 4;
    private static final int FILE_ISV3 = 8;
    private static final int FILE_ISV4 = 16;
    private static final int FILE_ISV5 = 32;
    private static final int FILE_UNC = 64;
    private static final int FILE_CEN = 128;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimeFile other = (AnimeFile) obj;
        if (this.fileId != other.fileId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.fileId;
        return hash;
    }

    @Override
    public String toString() {
        if (isGeneric())
            return String.format("Generic: %s", episode.toString());
        else if (group == null)
            return String.format("No Group: %s", episode.toString());
        else
            return String.format("%s: %s", group.getShortName(), episode.toString());
    }

    @Override
    public void addContentObjects(Collection<Object> addTo) {
        addTo.add(this);
        UIUtils.addContentObject(episode, addTo);
        UIUtils.addContentObject(group, addTo);
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public int getCurrentSaveRevision() {
        return currentSaveRevision;
    }

    public void setCurrentSaveRevision(int currentSaveRevision) {
        this.currentSaveRevision = currentSaveRevision;
    }

    
}
