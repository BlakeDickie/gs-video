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

package net.landora.animeinfo.metadata;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.animeinfo.data.AnimeGroup;
import net.landora.animeinfo.data.AnimeListFileState;
import net.landora.animeinfo.data.AnimeListItem;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.animeinfo.data.AnimeStub;
import net.landora.video.info.AbstractVideoMetadata;
import net.landora.video.utils.UIUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author bdickie
 */
public abstract class AnimeMetadata extends AbstractVideoMetadata {

    protected AnimeEpisode episode;
    protected AnimeFile file;
    protected AnimeStub anime;

    protected AnimeMetadata(AnimeEpisode episode, AnimeFile file) {
        this.episode = episode;
        this.file = file;
        this.anime = episode.getAnime();
    }

    @Override
    protected void addContentObjectsImpl(Collection<Object> addTo) {
        UIUtils.addContentObject(episode, addTo);
        if (file != null)
            UIUtils.addContentObject(file, addTo);
    }

    @Override
    public String getTypeDescription() {
        return anime.getType();
    }
    @Override
    protected void addExtraInformationImpl(Map<String, String> addTo) {
        
        
        if (anime.getRatingPermanent() != null)
            addTo.put("Rating", String.format("%.1f (%d)", anime.getRatingPermanent(), anime.getRatingPermanentVotes()));
        else if (anime.getRatingTemporary() != null)
            addTo.put("Temp. Rating", String.format("%.1f (%d)", anime.getRatingTemporary(), anime.getRatingTemporaryVotes()));
        
        if (file != null) {
            AnimeGroup group = file.getGroup();
            if (group != null)
                addTo.put("Group", group.getLostName());
            
        }
        
        AnimeViewListState listState = getViewListState();
        AnimeListItem listItem = (listState == null ? null : listState.getItem());
        if (listItem != null) {
            if (listItem.getFileState() != null && listItem.getFileState() != AnimeListFileState.Normal)
                addTo.put("State", listItem.getFileState().name());
        }
    }

    @Override
    public AnimeViewListState getViewListState() {
        return (AnimeViewListState) super.getViewListState();
    }
    
    
    
    @Override
    public boolean isAdult() {
        return anime.isHentai();
    }

    @Override
    public boolean isAnime() {
        return true;
    }

    @Override
    public byte[] getPosterImage() {
        String filename = episode.getAnime().getPictureFileName();
        if (filename == null) {
            filename = AnimeManager.getInstance().refreshAnime(episode.getAnime().getAnimeId()).getPictureFileName();
            if (filename == null)
                return null;
        }
        
        byte[] data = AnimeDBA.getAnimePicture(filename);
        if (data == null) {
            InputStream is = null;
            try {
                URL url = new URL(String.format("http://img7.anidb.net/pics/anime/%s", filename));
                is = url.openStream();
                data = IOUtils.toByteArray(is);

                AnimeDBA.saveAnimePicture(filename, data);
            } catch (Exception e) {
                if (is != null)
                    IOUtils.closeQuietly(is);
            }

        }
        return data;
        
    }

    @Override
    public AnimeListManager getListManager() {
        return AnimeListManager.getInstance();
    }

    public AnimeFile getFile() {
        return file;
    }

    public AnimeEpisode getEpisode() {
        return episode;
    }

    public String getNfoUrl() {
        return String.format("http://anidb.net/perl-bin/animedb.pl?show=anime&aid=%d", anime.getAnimeId());
    }

    public String getUniqueVideoId() {
        return AnimeMetadataProvider.getUniqueVideoId(episode);
    }
    
}
