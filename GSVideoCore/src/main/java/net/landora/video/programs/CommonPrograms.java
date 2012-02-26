/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.programs;

import java.util.Collections;

/**
 *
 * @author bdickie
 */
public class CommonPrograms {
    public static final Program MPLAYER = new Program("core.mplayer", "MPlayer", "mplayer", Collections.EMPTY_LIST);
    public static final Program MEDIAINFO = new Program("core.mediainfo", "MediaInfo", "mediainfo", Collections.EMPTY_LIST);
    public static final Program LSDVD = new Program("core.lsdvd", "lsdvd", "lsdvd", null, Collections.singletonList("-V"));
    public static final Program MKVINFO = new Program("core.mkvinfo", "mkvinfo", "mkvinfo", Collections.singletonList("-V"));
}
