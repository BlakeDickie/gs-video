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
public class NFOParser {
    private Logger log = LoggerFactory.getLogger(getClass());
    
    public GeneralVideoNFO parseNFOFile(File file) {
        try {
            XMLConfiguration config = new XMLConfiguration(file);
            
            String rootElement = config.getRootElementName();
            
            
            
        } catch (ConfigurationException ex) {
            log.error("Error loading NFO file.", ex);
            return null;
        }
        return null;
    }
    
}
