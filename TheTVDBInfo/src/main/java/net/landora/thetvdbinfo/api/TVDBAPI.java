/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.thetvdbinfo.api;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import net.landora.videoinfo.util.XMLUtilities;
import org.openide.util.Exceptions;

/**
 *
 * @author bdickie
 */
public class TVDBAPI {
    private static final String API_KEY = "E87424F7E6E4D3B0";
    private static final String LANG = "en";
    private static final String CORE_HOST = "http://www.thetvdb.com";
    
    
    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static TVDBAPI instance = new TVDBAPI();
    }

    public static TVDBAPI getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>
    
    private List<String> xmlMirrors;
    private List<String> bannerMirrors;
    private List<String> zipMirrors;
    
    private TVDBAPI() {
        loadMirrors();
    }
    
    private static final String UPDATE_TIME_URL = "/api/Updates.php?type=none";
    public long getCurrentUpdateTime() {
        XMLStreamReader xml = openXMLStream(buildUrl(CORE_HOST, UPDATE_TIME_URL));
        if (xml == null) {
            return 0;
        }
        try {
            long lastUpdate = 0;
            xml.nextTag();
            xml.require(XMLStreamReader.START_ELEMENT, null, "Items");

            while (xml.nextTag() != XMLStreamReader.END_ELEMENT) {
                String tag = xml.getLocalName();

                if (tag.equals("Items")) {
                    lastUpdate = Long.parseLong(XMLUtilities.nextString(xml));
                } else {
                    XMLUtilities.ignoreTag(xml);
                }

                xml.require(XMLStreamReader.END_ELEMENT, null, tag);

            }
            xml.require(XMLStreamReader.END_ELEMENT, null, "Items");
            xml.close();

            return lastUpdate;
        } catch (Exception e) {
            xmlMirrors = Collections.singletonList(CORE_HOST);
            bannerMirrors = Collections.singletonList(CORE_HOST);
            zipMirrors = Collections.singletonList(CORE_HOST);

            return 0;
        }
    }
    
    
    private static final String MIRROR_URL = "/api/APIKEY/mirrors.xml";
    private void loadMirrors() {
        XMLStreamReader mirrors = openXMLStream(buildUrl(CORE_HOST, MIRROR_URL));
        if (mirrors == null) {
            xmlMirrors = Collections.singletonList(CORE_HOST);
            bannerMirrors = Collections.singletonList(CORE_HOST);
            zipMirrors = Collections.singletonList(CORE_HOST);
        } else {
            xmlMirrors = new ArrayList<String>();
            bannerMirrors = new ArrayList<String>();
            zipMirrors = new ArrayList<String>();
            
            try {
                mirrors.nextTag();
                mirrors.require(XMLStreamReader.START_ELEMENT, null, "Mirrors");
                
                while(mirrors.nextTag() != XMLStreamReader.END_ELEMENT) {
                    mirrors.require(XMLStreamReader.START_ELEMENT, null, "Mirror");
                    String mirrorPath = null;
                    int typemask = 0;
                    
                    while(mirrors.nextTag() != XMLStreamReader.END_ELEMENT) {
                        String tag = mirrors.getLocalName();
                        
                        if (tag.equals("mirrorpath")) {
                            mirrorPath = XMLUtilities.nextString(mirrors);
                        } else if (tag.equals("typemask")) {
                            typemask = Integer.parseInt(XMLUtilities.nextString(mirrors));
                        } else
                            XMLUtilities.ignoreTag(mirrors);
                        
                        mirrors.require(XMLStreamReader.END_ELEMENT, null, tag);
                    }
                    
                    if (mirrorPath != null) {
                        if ((typemask & 1) != 0)
                            xmlMirrors.add(mirrorPath);
                        if ((typemask & 2) != 0)
                            bannerMirrors.add(mirrorPath);
                        if ((typemask & 4) != 0)
                            zipMirrors.add(mirrorPath);
                    }
                    
                    mirrors.require(XMLStreamReader.END_ELEMENT, null, "Mirror");
                }
                mirrors.require(XMLStreamReader.END_ELEMENT, null, "Mirrors");
                mirrors.close();
            
            
            } catch (Exception e) {
                xmlMirrors = Collections.singletonList(CORE_HOST);
                bannerMirrors = Collections.singletonList(CORE_HOST);
                zipMirrors = Collections.singletonList(CORE_HOST);
                
                Exceptions.printStackTrace(e);
            }
        }
    }
    
    private Random random = new Random();
    
    private String buildUrl(List<String> mirrorHosts, String url, Object... args) {
        int i = random.nextInt(mirrorHosts.size());
        return buildUrl(mirrorHosts.get(i), url, args);
    }
    
    private String buildUrl(String host, String url, Object... args) {
        StringBuilder res = new StringBuilder();
        res.append(host);
        res.append(url.replaceAll("APIKEY", API_KEY).replaceAll("LANG", LANG));
        return res.toString();
    }
    
    private XMLStreamReader openXMLStream(String url) {
        try {
            URL urlObj = new URL(url);
            InputStream is = urlObj.openStream();
            
            XMLInputFactory factory = XMLInputFactory.newFactory();
            return factory.createXMLStreamReader(is);
        
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
            return null;
        }
    }
}
