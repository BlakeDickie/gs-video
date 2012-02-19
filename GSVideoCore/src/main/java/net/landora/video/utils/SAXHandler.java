/*
 * SAXHandler.java
 *
 * Created on February 27, 2004, 7:30 PM
 */

package net.landora.video.utils;


import org.xml.sax.helpers.*;
import org.xml.sax.*;

/** A SAX Handler that keeps track of if an error occured.
 * @author Blake Dickie
 */
public class SAXHandler extends DefaultHandler {
    private boolean m_error;
    
    //*********************** CONSTRUCTORS ************************
    
    /** Creates a new instance of SAXHandler */
    public SAXHandler() {
        m_error = false;
    }
    
    //*********************** PUBLIC METHODS **********************
    
    /** Checks if an error occured.
     */
    public boolean isError() {
        return m_error;
    }
    
    /** Handler for an error.
     */
    public void error(SAXParseException e) {
        e.printStackTrace();
        m_error = true;
    }
    
    /** Handler for a fatal error.
     */
    public void fatalError(SAXParseException e) {
        e.printStackTrace();
        m_error = true;
    }
    
    /** Handler for a warning.
     */
    public void warning(SAXParseException e) {
        e.printStackTrace();
        m_error = true;
    }
}