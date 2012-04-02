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

package net.landora.video.info;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import net.landora.video.ui.ContextProducer;
import net.landora.video.utils.UIUtils;

/**
 *
 * @author bdickie
 */
public abstract class AbstractVideoMetadata implements VideoMetadata, ContextProducer {

    @Override
    public boolean isMovie() {
        return this instanceof MovieMetadata;
    }

    @Override
    public boolean isMultiSeasonSeries() {
        return this instanceof MultiSeasonSeriesMetadata;
    }

    @Override
    public boolean isSeries() {
        return this instanceof SeriesMetadata;
    }

    @Override
    public String getTypeDescription() {
        if (isMovie())
            return "Movie";
        else if (isSeries())
            return "Series";
        else
            return "UNKNOWN";
    }
    
    
    
    protected static String getEpisodeNumberForMultiSeasonSeries(MultiSeasonSeriesMetadata md) {
        return String.format("S%dE%02d", md.getSeasonNumber(), md.getEpisodeInSeason());
    }
    
    public ViewListState getViewListState() {
        ViewListManager listManager = getListManager();
        if (listManager != null) {
            ViewListState viewListState = listManager.getViewListState(this);
            if (viewListState != null)
                return viewListState;
        }
        return null;
    }
    
    @Override
    public final void addExtraInformation(Map<String, String> addTo) {
        addTo.put("Type", getTypeDescription());
        
        ViewListState state = getViewListState();
        if (state != null) {
            Calendar viewDate = state.getLastViewed();
            addTo.put("Watched", (viewDate == null ? "Not Watched" : viewDate.getTime().toString()));

        }
        addExtraInformationImpl(addTo);
    }
    
    protected abstract void addExtraInformationImpl(Map<String, String> addTo);
    
    
    
    protected abstract void addContentObjectsImpl(Collection<Object> addTo);

    public void addContentObjects(Collection<Object> addTo) {
        addTo.add(this);
        UIUtils.addContentObject(getViewListState(), addTo);
        addContentObjectsImpl(addTo);
    }

    
    
    @Override
    public String toString() {
        if (isSeries()) {
            SeriesMetadata md = (SeriesMetadata)this;
            return String.format("%s - %s - %s", md.getSeriesName(), md.getEpisodeNumber(), md.getEpisodeName());
        } else if (isMovie()) {
            MovieMetadata md = (MovieMetadata)this;
            return md.getMovieName();
        } else
            return super.toString();
    }

    @Override
    public boolean isAnime() {
        return false;
    }
    
}
