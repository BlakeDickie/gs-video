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

import java.util.*;
import net.landora.video.info.file.FileInfo;
import net.landora.video.utils.OrderedRepresentation;
import net.landora.video.utils.RegularExpressions;
import net.landora.video.utils.Touple;

/**
 *
 * @author bdickie
 */
public class MetadataUtils {
    public static <T extends FileInfo> Map<OrderedRepresentation<String>,GroupingValue<T>> groupFileItems(Collection<T> infos) {
        Map<OrderedRepresentation<String>,GroupingValue<T>> result = new TreeMap<OrderedRepresentation<String>, GroupingValue<T>>();
        
        for(T info: infos) {
            VideoMetadata metadata = MetadataProvidersManager.getInstance().getMetadata(info);
            if (metadata == null)
                continue;
            List<OrderedRepresentation<String>> groupings = getGroupings(metadata);
            addItem(result, groupings, 0, info, metadata);
        }
        
        return result;
    }
    
    private static <T extends FileInfo> void addItem(Map<OrderedRepresentation<String>,GroupingValue<T>> saveTo, List<OrderedRepresentation<String>> groupings, int index, T info,
            VideoMetadata metadata) {
        OrderedRepresentation<String> group = groupings.get(index);
        
        GroupingValue<T> value = saveTo.get(group);
        if (value == null) {
            value = new GroupingValue<T>();
            saveTo.put(group, value);
        }
        index++;
        if (groupings.size() == index) {
            value.getLeafsOrCreate().add(new Touple<T, VideoMetadata>(info, metadata));
        } else {
            addItem(value.getChildrenGroupsOrCreate(), groupings, index, info, metadata);
        }
        
    }
    
    public static List<OrderedRepresentation<String>> getGroupings(VideoMetadata md) {
        switch(md.getType()) {
            case Movie:
                return getGroupingsForMovie((MovieMetadata)md);
                
            case SingleSeasonSeries:
                return getGroupingsForSingleSeasonSeries((SeriesMetadata)md);
                
            case MultiSeasonSeries:
                return getGroupingsForMultiSeasonSeries((MultiSeasonSeriesMetadata)md);
                
            default:
                throw new IllegalArgumentException("Unknown metadata type: " + md.getType());
        }
    }
    
    private static List<OrderedRepresentation<String>> getGroupingsForMovie(MovieMetadata md) {
        return Collections.singletonList(new OrderedRepresentation<String>(md.getMovieName()));
    }
    
    private static List<OrderedRepresentation<String>> getGroupingsForSingleSeasonSeries(SeriesMetadata md) {
        List<OrderedRepresentation<String>> result = new ArrayList<OrderedRepresentation<String>>(2);
        
        result.add(new OrderedRepresentation<String>(md.getSeriesName()));
        
        int seriesNum = Integer.MAX_VALUE;
        if (RegularExpressions.isWholeNumber(md.getEpisodeNumber()))
            seriesNum = Integer.parseInt(md.getEpisodeNumber());
        
        result.add(new OrderedRepresentation<String>(seriesNum, String.format("Episode %s - %s", md.getEpisodeNumber(), md.getEpisodeName())));
        
        return result;
    }
    
    private static List<OrderedRepresentation<String>> getGroupingsForMultiSeasonSeries(MultiSeasonSeriesMetadata md) {
        List<OrderedRepresentation<String>> result = new ArrayList<OrderedRepresentation<String>>(3);
        
        result.add(new OrderedRepresentation<String>(md.getSeriesName()));
        
        result.add(new OrderedRepresentation<String>(md.getSeasonNumber(), String.format("Season %d", md.getSeasonNumber())));
        
        int seriesNum = Integer.MAX_VALUE;
        if (RegularExpressions.isWholeNumber(md.getEpisodeNumber()))
            seriesNum = Integer.parseInt(md.getEpisodeNumber());
        result.add(new OrderedRepresentation<String>(seriesNum, String.format("Episode %s - %s", md.getEpisodeNumber(), md.getEpisodeName())));
        
        return result;
    }
    
    public static class GroupingValue<T extends FileInfo> {
        private Map<OrderedRepresentation<String>,GroupingValue<T>> childrenGroups;
        private List<Touple<T,VideoMetadata>> leafs;

        public GroupingValue() {
        }

        public Map<OrderedRepresentation<String>, GroupingValue<T>> getChildrenGroups() {
            return (childrenGroups == null ? Collections.EMPTY_MAP : childrenGroups);
        }
        
        private Map<OrderedRepresentation<String>, GroupingValue<T>> getChildrenGroupsOrCreate() {
            if (childrenGroups == null)
                childrenGroups = new TreeMap<OrderedRepresentation<String>, GroupingValue<T>>();
            return childrenGroups;
        }

        public List<Touple<T,VideoMetadata>> getLeafs() {
            return (leafs == null ? Collections.EMPTY_LIST : leafs);
        }
        
        private List<Touple<T,VideoMetadata>> getLeafsOrCreate() {
            if (leafs == null)
                leafs = new ArrayList<Touple<T,VideoMetadata>>();
            return leafs;
        }
        
        
    }
}
