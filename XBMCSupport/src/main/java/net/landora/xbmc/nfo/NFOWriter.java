/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.xbmc.nfo;

import java.io.File;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bdickie
 */
public class NFOWriter {
    private Logger log = LoggerFactory.getLogger(getClass());
    
    public void writeNFO(GeneralVideoNFO nfo, File output) {
        
        XMLConfiguration xml = new XMLConfiguration();
        
        if (nfo instanceof TVShowNFO)
            xml.setRootElementName("tvshow");
        else if (nfo instanceof MovieNFO)
            xml.setRootElementName("movie");
        else if (nfo instanceof TVEpisodeNFO)
            xml.setRootElementName("episodedetails");
        else
            throw new IllegalArgumentException("Unknown NFO type: " + nfo.getClass());
        
        if (nfo.getUrl() != null)
            xml.setProperty("episodeguideurl", nfo.getUrl());
        
        if (nfo.getTitle() != null)
            xml.setProperty("title", nfo.getTitle());
        
        try {
            xml.save(output);
        } catch (ConfigurationException ex) {
            log.error("Error saving NFO file: " + output.getPath(), ex);
        }
        
    }
}
