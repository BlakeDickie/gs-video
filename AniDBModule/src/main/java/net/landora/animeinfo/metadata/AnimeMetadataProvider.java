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
package net.landora.animeinfo.metadata;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.landora.animeinfo.data.Anime;
import net.landora.animeinfo.data.AnimeDBA;
import net.landora.animeinfo.data.AnimeEpisode;
import net.landora.animeinfo.data.AnimeFile;
import net.landora.animeinfo.data.AnimeManager;
import net.landora.animeinfo.data.AnimeNameLookup;
import net.landora.animeinfo.data.AnimeStub;
import net.landora.video.info.MetadataMatch;
import net.landora.video.info.MetadataProvider;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.file.FileInfo;
import net.landora.video.utils.Touple;

/**
 *
 * @author bdickie
 */
public class AnimeMetadataProvider implements MetadataProvider {

    private static final String PROVIDER_ID = "ANIDB";

    public AnimeMetadataProvider() {
    }

    @Override
    public String getProviderId() {
        return PROVIDER_ID;
    }

    @Override
    public int getProviderVersion() {
        return 3;
    }

    public static String getUniqueVideoId(AnimeEpisode episode) {
        return "AID:" + episode.getEpisodeId();
    }

    private List<MetadataMatch> episodeMatchresult(AnimeEpisode episode) {
        AnimeFile item = AnimeDBA.getGenericAnimeFile(episode);
        String metadataId;
        if (item == null) {
            metadataId = "EID:" + episode.getEpisodeId();
        } else {
            metadataId = "FID:" + item.getFileId();
        }

        return Collections.singletonList(new MetadataMatch(MetadataMatch.MatchType.FilenameMatch, metadataId, getUniqueVideoId(episode)));
    }

    @Override
    public List<MetadataMatch> checkForMatch(FileInfo info, boolean ignoreCache) {
        AnimeEpisode episode = AnimeDBA.findCachedED2K(info.getE2dkHash(), info.getFileSize());
        if (episode != null) {
            return episodeMatchresult(episode);
        }

        if (ignoreCache || !AnimeDBA.checkForCachedED2KFileFailure(info.getE2dkHash(), info.getFileSize())) {
            AnimeFile file = AnimeManager.getInstance().findFile(info.getE2dkHash(), info.getFileSize());
            if (file != null) {
                return Collections.singletonList(new MetadataMatch(MetadataMatch.MatchType.HashMatch, "FID:" + file.getFileId(),
                        getUniqueVideoId(file.getEpisode())));
            }
        }

        if (!info.getFilename().contains(".")) {
            return null;
        }

        String filenameWithoutExtension = info.getFilename().substring(0, info.getFilename().lastIndexOf('.')).replace('_', ' ');
        for (AnimeFileNamePattern pattern : AnimeFileNamePattern.values()) {
            Touple<String, String> parsing = pattern.attemptParsing(filenameWithoutExtension);
            if (parsing == null) {
                continue;
            }

            List<AnimeNameLookup> findAnimeNames = AnimeDBA.findAnimeNames(parsing.getFirst());
            int animeId = -1;
            for (AnimeNameLookup lookup : findAnimeNames) {
                animeId = lookup.getAnime().getAnimeId();
            }

            if (animeId == -1) {
                continue;
            }

            Anime anime = AnimeManager.getInstance().getAnime(animeId);
            String number = parsing.getSecond();
            if (number == null) {
                if (anime.getEpisodeCount() != null && anime.getEpisodeCount().intValue() == 1) {
                    // Must be single movie or OVA.
                    number = "1";
                }
            }

            if (number != null) {
                if (Character.isDigit(number.charAt(0))) {
                    episode = AnimeManager.getInstance().findEpisode(animeId, Integer.parseInt(number));
                    if (episode != null) {
                        AnimeDBA.addCachedED2K(info.getE2dkHash(), info.getFileSize(), episode.getEpisodeId());
                        return episodeMatchresult(episode);
                    }
                } else {
                    episode = AnimeManager.getInstance().findEpisode(animeId, number);
                    if (episode != null) {
                        AnimeDBA.addCachedED2K(info.getE2dkHash(), info.getFileSize(), episode.getEpisodeId());
                        return episodeMatchresult(episode);
                    }
                }

            }

        }

        return null;
    }

    private final Pattern FileInfoIdParsePattern = Pattern.compile("([EF])ID:(\\d+)", Pattern.CASE_INSENSITIVE);

    @Override
    public VideoMetadata getMetadata(String metadataId) {

        Matcher m = FileInfoIdParsePattern.matcher(metadataId);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid file info id: " + metadataId);
        }
        int id = Integer.parseInt(m.group(2));

        AnimeEpisode episode;
        AnimeFile file;
        if (m.group(1).equalsIgnoreCase("F")) {
            file = AnimeManager.getInstance().findFile(id);
            if (file == null) {
                throw new IllegalArgumentException("Unable to find anime file: " + id);
            }

            episode = file.getEpisode();
        } else {
            file = null;
            episode = AnimeManager.getInstance().findEpisode(id);

            if (episode == null) {
                throw new IllegalArgumentException("Unable to find anime episode: " + id);
            }
        }

        return getMetadata(episode, file);
    }

    public static VideoMetadata getMetadata(AnimeFile file) {
        return getMetadata(file.getEpisode(), file);
    }

    public static VideoMetadata getMetadata(AnimeEpisode episode, AnimeFile file) {

        AnimeStub anime = episode.getAnime();

        boolean series = true;

        if ((anime.getEpisodeCount() != null && anime.getEpisodeCount().intValue() == 1)
                && episode.getNormalEpisodeNumber() != null && episode.getNormalEpisodeNumber().intValue() == 1) {
            String type = anime.getType();
            if (type.equalsIgnoreCase("Movie") || type.equalsIgnoreCase("OVA")) {
                series = false;
            }

        }

        if (series) {
            return new AnimeSeriesMetadata(episode, file);
        } else {
            return new AnimeMovieMetadata(episode, file);
        }
    }

}
