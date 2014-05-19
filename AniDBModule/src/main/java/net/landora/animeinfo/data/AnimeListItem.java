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
