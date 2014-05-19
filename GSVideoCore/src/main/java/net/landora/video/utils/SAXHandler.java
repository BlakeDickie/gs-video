/**
 * Copyright (C) 2012-2014 Blake Dickie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * SAXHandler.java
 *
 * Created on February 27, 2004, 7:30 PM
 */
package net.landora.video.utils;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX Handler that keeps track of if an error occured.
 *
 * @author Blake Dickie
 */
public class SAXHandler extends DefaultHandler {

    private boolean m_error;

    //*********************** CONSTRUCTORS ************************
    /**
     * Creates a new instance of SAXHandler
     */
    public SAXHandler() {
        m_error = false;
    }

    //*********************** PUBLIC METHODS **********************
    /**
     * Checks if an error occured.
     */
    public boolean isError() {
        return m_error;
    }

    /**
     * Handler for an error.
     */
    public void error( SAXParseException e ) {
        m_error = true;
    }

    /**
     * Handler for a fatal error.
     */
    public void fatalError( SAXParseException e ) {
        m_error = true;
    }

    /**
     * Handler for a warning.
     */
    public void warning( SAXParseException e ) {
        m_error = true;
    }
}
