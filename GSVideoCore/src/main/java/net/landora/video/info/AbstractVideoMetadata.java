/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.landora.video.info;

import net.landora.video.ui.ContextProducer;
import net.landora.video.utils.UIUtils;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public Type getType() {
        if ( isMovie() ) {
            return Type.Movie;
        } else if ( isSeries() ) {
            if ( isMultiSeasonSeries() ) {
                return Type.MultiSeasonSeries;
            } else {
                return Type.SingleSeasonSeries;
            }
        } else {
            throw new IllegalStateException( "Unknown metadata type: " + getClass() );
        }
    }

    @Override
    public String getTypeDescription() {
        if ( isMovie() ) {
            return "Movie";
        } else if ( isSeries() ) {
            return "Series";
        } else {
            return "UNKNOWN";
        }
    }

    protected static String getEpisodeNumberForMultiSeasonSeries( MultiSeasonSeriesMetadata md ) {
        return String.format( "S%dE%02d", md.getSeasonNumber(), md.getEpisodeInSeason() );
    }

    public ViewListState getViewListState() {
        ViewListManager listManager = getListManager();
        if ( listManager != null ) {
            ViewListState viewListState = listManager.getViewListState( this );
            if ( viewListState != null ) {
                return viewListState;
            }
        }
        return null;
    }

    @Override
    public final void addExtraInformation( Map<String, String> addTo, boolean detailed ) {
        addTo.put( "Type", getTypeDescription() );

        ViewListState state = getViewListState();
        if ( state != null ) {
            Calendar viewDate = state.getLastViewed();
            addTo.put( "Watched", ( viewDate == null ? "Not Watched" : viewDate.getTime().toString() ) );

        }
        addExtraInformationImpl( addTo, detailed );
    }

    protected abstract void addExtraInformationImpl( Map<String, String> addTo, boolean detailed );

    protected abstract void addContentObjectsImpl( Collection<Object> addTo );

    public void addContentObjects( Collection<Object> addTo ) {
        addTo.add( this );
        UIUtils.addContentObject( getViewListState(), addTo );
        addContentObjectsImpl( addTo );
    }

    @Override
    public String toString() {
        if ( isSeries() ) {
            SeriesMetadata md = (SeriesMetadata) this;
            return String.format( "%s - %s - %s", md.getSeriesName(), md.getEpisodeNumber(), md.getEpisodeName() );
        } else if ( isMovie() ) {
            MovieMetadata md = (MovieMetadata) this;
            return md.getMovieName();
        } else {
            return super.toString();
        }
    }

    @Override
    public boolean isAnime() {
        return false;
    }

    @Override
    public Map<String, String> getAllInformation( boolean detailed ) {

        Map<String, String> values = new LinkedHashMap<String, String>();
        if ( isMovie() ) {
            MovieMetadata movie = (MovieMetadata) this;
            values.put( "Movie Name", movie.getMovieName() );
        } else if ( isSeries() ) {
            SeriesMetadata series = (SeriesMetadata) this;
            values.put( "Series name", series.getSeriesName() );
            if ( isMultiSeasonSeries() ) {
                MultiSeasonSeriesMetadata multiSeason = (MultiSeasonSeriesMetadata) this;
                values.put( "Season", String.valueOf( multiSeason.getSeasonNumber() ) );
            }
            values.put( "Episode Number", series.getEpisodeNumber() );
            if ( series.getEpisodeName() != null ) {
                values.put( "Episode Name", series.getEpisodeName() );
            }
        }

        addExtraInformation( values, detailed );

        if ( isAdult() ) {
            values.put( "Adult Content", "Yes" );
        }

        return values;
    }

}
