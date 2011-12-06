/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.videoinfo;

import junit.framework.TestCase;

/**
 *
 * @author bdickie
 */
public class VideoInfoFileUtilsTest extends TestCase {
    
    public VideoInfoFileUtilsTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFilterInvalidFilenameCharactersWithoutEscapes() {
        String filename = "Soul Eater";
        String expResult = "Soul Eater";
        String result = VideoInfoFileUtils.filterInvalidFilenameCharacters(filename);
        assertEquals(expResult, result);
    }

    public void testFilterInvalidFilenameCharactersColonNoSpace() {
        String filename = "Rio:Rainbow Gate!";
        String expResult = "Rio ~ Rainbow Gate!";
        String result = VideoInfoFileUtils.filterInvalidFilenameCharacters(filename);
        assertEquals(expResult, result);
    }

    public void testFilterInvalidFilenameCharactersColonTailingSpace() {
        String filename = "Rio: Rainbow Gate!";
        String expResult = "Rio ~ Rainbow Gate!";
        String result = VideoInfoFileUtils.filterInvalidFilenameCharacters(filename);
        assertEquals(expResult, result);
    }

    public void testFilterInvalidFilenameCharactersColonSpecialCharacter() {
        String filename = "Rio - Rainbow Gate!?";
        String expResult = "Rio - Rainbow Gate!";
        String result = VideoInfoFileUtils.filterInvalidFilenameCharacters(filename);
        assertEquals(expResult, result);
    }
}
