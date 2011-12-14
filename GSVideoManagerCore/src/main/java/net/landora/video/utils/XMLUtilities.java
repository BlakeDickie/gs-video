/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.video.utils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author bdickie
 */
public class XMLUtilities {

    public static String nextString(XMLStreamReader reader) throws XMLStreamException {
        int type;
        StringBuilder result = new StringBuilder();
        while((type = reader.next()) != XMLStreamReader.END_ELEMENT) {
            switch(type) {
                case XMLStreamReader.START_ELEMENT:
                    throw new IllegalStateException("Found start element parsing text.");

                case XMLStreamReader.CDATA:
                case XMLStreamReader.SPACE:
                case XMLStreamReader.CHARACTERS:
                    result.append(reader.getText());
            }
        }
        return result.toString();
    }


    public static void ignoreTag(XMLStreamReader reader) throws XMLStreamException {
        int type;
        while((type = reader.next()) != XMLStreamReader.END_ELEMENT) {
            switch(type) {
                case XMLStreamReader.START_ELEMENT:
                    ignoreTag(reader);

                case XMLStreamReader.CDATA:
                case XMLStreamReader.SPACE:
                case XMLStreamReader.CHARACTERS:
                default:
            }
        }
    }
}
