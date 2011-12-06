/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.data;

import java.util.Calendar;

/**
 *
 * @author bdickie
 */
public class AnimeListItem implements java.io.Serializable {
    private AnimeFile file;
    private AnimeListState state;
    private AnimeListFileState fileState;
    private Calendar viewDate;
    private Calendar addedDate;
    private String storage;
    private String source;
    private String other;

    public AnimeListItem() {
    }

    public Calendar getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Calendar addedDate) {
        this.addedDate = addedDate;
    }

    public AnimeFile getFile() {
        return file;
    }

    public void setFile(AnimeFile file) {
        this.file = file;
    }

    public AnimeListState getState() {
        return state;
    }

    public void setState(AnimeListState state) {
        this.state = state;
    }

    public Calendar getViewDate() {
        return viewDate;
    }

    public void setViewDate(Calendar viewDate) {
        this.viewDate = viewDate;
    }

    public void setStateId(int stateId) {
        setState(AnimeListState.lookupType(stateId));
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", file, getState());
    }

    public AnimeListFileState getFileState() {
        return fileState;
    }

    public void setFileState(AnimeListFileState fileState) {
        this.fileState = fileState;
    }
    
    public void setFileStateId(int stateId) {
        setFileState(AnimeListFileState.lookupType(stateId));
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
    
    
    
}
