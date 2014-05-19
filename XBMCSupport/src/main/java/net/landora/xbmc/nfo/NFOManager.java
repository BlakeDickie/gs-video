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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.xbmc.nfo;

import java.io.File;
import net.landora.video.info.ExtensionUtils;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.info.MovieMetadata;
import net.landora.video.info.SeriesMetadata;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.file.CheckFileExtension;
import net.landora.video.info.file.FileInfo;
import net.landora.video.info.file.FileInfoChangedEvent;
import net.landora.video.info.file.FileInfoChangedListener;
import net.landora.video.info.file.FileInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class NFOManager implements CheckFileExtension, FileInfoChangedListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String TV_SHOW_FILENAME = "tvshow.nfo";

    public NFOManager() {
    }

    private void checkFile(File file, FileInfo info) {
        VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);
        checkFile(file, md, info);
    }

    public void fileChanged(FileInfoChangedEvent e) {
        checkFile(e.getFile(), e.getFileInfo());
    }

    public void fileRemoved(FileInfoChangedEvent e) {
        checkFile(e.getFile(), e.getFileInfo());
    }

    public void checkFile(File file, VideoMetadata md, FileInfo fileInfo) {
        boolean fileExists = file.isFile();
        log.info("Check file: " + file.getPath());

        File nfoFile = getNFOFile(file);

        if (!fileExists || md == null) {
            if (nfoFile.exists()) {
                log.info("Deleting Video NFO File: " + nfoFile.getPath());
                nfoFile.delete();
            }
        } else {

            if (md.isMovie()) {
                if (!nfoFile.exists()) {

                    log.info("Missing Video NFO File: " + nfoFile.getPath());

                    MovieMetadata series = (MovieMetadata) md;

                    MovieNFO nfo = new MovieNFO();
                    nfo.setUrl(series.getNfoUrl());
                    nfo.setTitle(series.getMovieName());
                    new NFOWriter().writeNFO(nfo, nfoFile);
                }
            }

        }

        checkFolderForTVShow(file.getParentFile(), md);
    }

    private File getNFOFile(File videoFile) {
        String name = ExtensionUtils.getFilenameWithoutExtension(videoFile);
        return new File(videoFile.getParentFile(), name + ".nfo");
    }

    private void checkFolderForTVShow(File folder, VideoMetadata md) {
        boolean generateTVShow;
        if (md != null && md.isSeries()) {
            generateTVShow = true;

            for (File file : folder.listFiles()) {
                if (file.isHidden()) {
                    continue;
                }

                if (file.isFile()) {
                    String extension = ExtensionUtils.getExtension(file);
                    if (ExtensionUtils.isVideoExtension(extension)) {

                        FileInfo info = FileInfoManager.getInstance().getFileInfo(file, true);
                        if (info == null) {
                            generateTVShow = false;
                            break;
                        }

                        VideoMetadata md2 = MetadataProvidersManager.getInstance().getMetadata(info);
                        if (md2 == null || !md2.isSeries() && !((SeriesMetadata) md2).sameSeries((SeriesMetadata) md)) {
                            generateTVShow = false;
                            break;
                        }
                    }
                }
            }

        } else {
            generateTVShow = false;
        }

        File tvShowFile = new File(folder, TV_SHOW_FILENAME);
        if (generateTVShow) {
            if (!tvShowFile.exists()) {
                log.info("Missing TV Show NFO File: " + tvShowFile.getPath());

                SeriesMetadata series = (SeriesMetadata) md;

                TVShowNFO nfo = new TVShowNFO();
                nfo.setUrl(series.getNfoUrl());
                nfo.setTitle(series.getSeriesName());
                new NFOWriter().writeNFO(nfo, tvShowFile);

            }
        } else {
            if (tvShowFile.exists()) {
                log.info("Deleting TV Show NFO File: " + tvShowFile.getPath());
                tvShowFile.delete();
            }
        }
    }
}
