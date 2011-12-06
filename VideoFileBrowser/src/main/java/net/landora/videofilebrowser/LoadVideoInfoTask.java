/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videofilebrowser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.landora.gsuiutils.NBTask;
import net.landora.gsuiutils.UIUtils;
import net.landora.videoinfo.MetadataProvidersManager;
import net.landora.videoinfo.VideoMetadata;
import net.landora.videoinfo.file.FileInfo;
import net.landora.videoinfo.file.FileInfoManager;

/**
 *
 * @author bdickie
 */
public class LoadVideoInfoTask extends NBTask<Object, Object> {
    
    private List<VideoFile> videos;

    public LoadVideoInfoTask(List<VideoFile> videos) {
        super("Loading Video Information");
        this.videos = new ArrayList<VideoFile>(videos);
        Collections.sort(videos, UIUtils.LEXICAL_SORTER);
    }

    @Override
    protected Object doInBackground() throws Exception {
        start(videos.size());
        for (int i = 0; i < videos.size(); i++) {
            
            VideoFile video = videos.get(i);
            
            progress(video.getFile().getName(), i);
            
            
            
            FileInfo info = FileInfoManager.getInstance().getFileInfo(video.getFile());
            video.setInfo(info);

            video.setStatus(VideoFile.Status.Identifying);
            VideoMetadata md = MetadataProvidersManager.getInstance().getMetadata(info);
            video.setVideo(md);

            video.setStatus(md == null ? VideoFile.Status.Unknown : VideoFile.Status.Identifed );
        }
        
        return null;
    }

    @Override
    protected void success(Object result) {
        
    }
    
    
}
