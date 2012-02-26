/*
 * Chapter.java
 *
 * Created on June 1, 2007, 11:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.landora.video.properties;

/**
 *
 * @author bdickie
 */
public class Chapter {
    
    private int chapterNumber;
    private float startTime;
    private float length;
    
    /** Creates a new instance of Chapter */
    public Chapter() {
    }
    
    
    @Override
    public String toString() {
        return "Chapter " + chapterNumber;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
}
