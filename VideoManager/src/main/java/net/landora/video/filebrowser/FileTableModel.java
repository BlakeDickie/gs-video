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
package net.landora.video.filebrowser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.landora.video.info.ExtensionUtils;
import net.landora.video.info.MetadataProvidersManager;
import net.landora.video.info.VideoMetadata;
import net.landora.video.info.file.FileInfo;
import net.landora.video.info.file.FileInfoManager;
import net.landora.video.tasks.NBTask;
import net.landora.video.utils.ComparisionUtils;
import net.landora.video.utils.Representation;
import net.landora.video.utils.UIUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author bdickie
 */
public class FileTableModel extends AbstractTableModel {

    private static final int COL_FILENAME = 0;
    private static final int COL_STATUS = COL_FILENAME + 1;
    private static final int COL_SIZE = COL_STATUS + 1;
    private static final int COL_VIDEO_INFO = COL_SIZE + 1;

    private static final int NUM_COLS = COL_VIDEO_INFO + 1;

    private VideoFileWatcher watcher;

    public FileTableModel() {
        watcher = new VideoFileWatcher();
        files = new ArrayList<VideoFile>();
    }

    private File folder;

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        if (ComparisionUtils.equals(this.folder, folder)) {
            return;
        }

        this.folder = folder;

        updateFolder();
    }

    @Override
    public int getColumnCount() {
        return NUM_COLS;
    }

    @Override
    public int getRowCount() {
        return files.size();
    }

    public VideoFile getFile(int rowIndex) {
        return files.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VideoFile file = files.get(rowIndex);

        switch (columnIndex) {
            case COL_FILENAME:
                return file.getFile().getName();

            case COL_STATUS:
                return file.getStatus();

            case COL_SIZE:
                long length = file.getLength();

                String display = FileUtils.byteCountToDisplaySize(length);
                return new Representation<Long>(display, length);

            case COL_VIDEO_INFO:
                VideoMetadata video = file.getVideo();
                if (video == null) {
                    return "";
                } else {
                    return video.toString();
                }

            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case COL_FILENAME:
                return "Filename";

            case COL_SIZE:
                return "File Size";

            case COL_STATUS:
                return "Status";

            case COL_VIDEO_INFO:
                return "Video";

            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COL_FILENAME:
                return String.class;
            case COL_SIZE:
                return Representation.class;
            case COL_VIDEO_INFO:
                return String.class;

            default:
                return Object.class;
        }
    }

    private class VideoFileWatcher implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            updateFile((VideoFile) evt.getSource());
        }

    }

    private void updateFile(VideoFile file) {
        int index = files.indexOf(file);
        if (index >= 0) {
            fireTableRowsUpdated(index, index);
        }
    }

    public void refresh() {
        updateFolder();
    }

    private List<VideoFile> files;

    private void updateFolder() {
        if (task != null) {
            task.cancel();
        }

        for (VideoFile file : files) {
            file.removePropertyChangeListener(watcher);
        }
        files.clear();

        if (folder != null) {

            File[] allFiles = folder.listFiles();
            for (File file : allFiles) {
                if (file.isHidden() || !file.isFile() || !ExtensionUtils.isVideoExtension(file)) {
                    continue;
                }

                VideoFile videoFile = new VideoFile(file);
                videoFile.addPropertyChangeListener(watcher);
                files.add(videoFile);
            }
            if (!files.isEmpty()) {
                task = new LoadVideoInfoTask(new ArrayList<VideoFile>(files));
                task.startTask();
            }
        }

        Collections.sort(files, UIUtils.LEXICAL_SORTER);

        fireTableDataChanged();
    }

    private LoadVideoInfoTask task;

    private class LoadVideoInfoTask extends NBTask<Object, Object> {

        private List<VideoFile> videos;

        public LoadVideoInfoTask(List<VideoFile> videos) {
            super("Loading Video Information");
            this.videos = videos;
        }

        @Override
        protected Object doInBackground() throws Exception {
            start(videos.size());
            for (int i = 0; i < videos.size(); i++) {
                progress(i);

                VideoFile video = videos.get(i);

                FileInfo info = FileInfoManager.getInstance().getFileInfo(video.getFile());
                video.setInfo(info);

                video.setStatus(VideoFile.Status.Identifying);
                VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);
                video.setVideo(md);

                video.setStatus(md == null ? VideoFile.Status.Unknown : VideoFile.Status.Identifed);
            }

            return null;
        }

        @Override
        protected void success(Object result) {

        }

        @Override
        protected void finished() {
            if (task == this) {
                task = null;
            }
        }

    }

}
