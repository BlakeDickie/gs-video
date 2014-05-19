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
package net.landora.tmdb;

import java.util.List;
import net.landora.video.info.MetadataMatch;
import net.landora.video.info.MetadataProvider;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.file.FileInfo;

/**
 *
 * @author bdickie
 */
public class TMDbMetadataProvider implements MetadataProvider {

    public static final String PROVIDER_ID = "TMDb";

    @Override
    public int getProviderVersion() {
        return 1;
    }

    @Override
    public String getProviderId() {
        return PROVIDER_ID;
    }

    @Override
    public List<MetadataMatch> checkForMatch(FileInfo info, boolean ignoreCache) {
        return null;
    }

    @Override
    public VideoMetadata getMetadata(String metadataId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
