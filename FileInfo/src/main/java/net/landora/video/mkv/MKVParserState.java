/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.mkv;

/**
 *
 * @author bdickie
 */
public enum MKVParserState {
    Other,
    Segment_Header,
    VideoTrack,
    AudioTrack,
    SubtitleTrack,
    UnknownTrack,
    Attachment,
    Chapter
}
