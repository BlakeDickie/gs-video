/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.videoinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import net.landora.videoinfo.file.FileInfo;

/**
 *
 * @author bdickie
 */
public class MetadataProvidersManager {

    // <editor-fold defaultstate="collapsed" desc="Singleton">
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.instance , not before.
     */
    private static class SingletonHolder {

        private final static MetadataProvidersManager instance = new MetadataProvidersManager();
    }

    public static MetadataProvidersManager getInstance() {
        return SingletonHolder.instance;
    }
    // </editor-fold>



    public static final String FAILURE_ID = "NOMATCH";

    private final String providerIdString;
    private final List<MetadataProvider> providers;

    private MetadataProvidersManager() {
        ServiceLoader<MetadataProvider> loader = ServiceLoader.load(MetadataProvider.class);
        
        List<MetadataProvider> allProviders = new ArrayList<MetadataProvider>();
        for(MetadataProvider provider: loader)
            allProviders.add(provider);

        Collections.sort(allProviders, new ProviderNameSorter());

        providers = Collections.unmodifiableList(allProviders);

        StringBuilder providerIdBuilder = new StringBuilder();
        for (int i = 0; i < allProviders.size(); i++) {
            MetadataProvider metadataProvider = allProviders.get(i);
            if (i > 0)
                providerIdBuilder.append(",");
            providerIdBuilder.append(metadataProvider.getProviderId());
            providerIdBuilder.append(":");
            if (metadataProvider.getProviderVersion() < 0) {
                // Provider in development state, always assume things are out of date.
                providerIdBuilder.append("***");
                providerIdBuilder.append(System.currentTimeMillis());
                providerIdBuilder.append("***");
            }  else
                providerIdBuilder.append(metadataProvider.getProviderVersion());
        }
        providerIdString = providerIdBuilder.toString();
    }

    public void checkForMetadata(FileInfo info) {
        for(MetadataProvider provider: providers) {
            List<MetadataMatch> matches = provider.checkForMatch(info);
            if (matches == null || matches.isEmpty())
                continue;
            
            MetadataMatch match = matches.get(0);
            if (match.getType() == MetadataMatch.MatchType.HashMatch) {
                info.setMetadataId(match.getMetadataId());
                info.setMetadataSource(provider.getProviderId());
                return;
            }
        }

        info.setMetadataId(providerIdString);
        info.setMetadataSource(FAILURE_ID);
    }

    public boolean checkForMetadataUpdate(FileInfo info) {
        boolean redo = false;
        if (info.getMetadataSource() == null) {
            redo = true;
        } else if (info.getMetadataSource().equals(FAILURE_ID)) {
//            if (!info.getMetadataId().equals(providerIdString))
                redo = true;
        }

        if (redo) {
            info.setMetadataId(null);
            info.setMetadataSource(null);

            checkForMetadata(info);

            return true;
        } else
            return false;
    }

    public String getProviderIdString() {
        return providerIdString;
    }

    private class ProviderNameSorter implements Comparator<MetadataProvider> {

        @Override
        public int compare(MetadataProvider o1, MetadataProvider o2) {
            return o1.getProviderId().compareTo(o2.getProviderId());
        }


    }
    
    
    public VideoMetadata getMetadata(FileInfo info) {
        if (info == null)// || (info.getMetadataSource().equalsIgnoreCase(FAILURE_ID) && info.getMetadataId().equals(providerIdString)))
            return null;
        
        for(MetadataProvider provider: providers)
            if (provider.getProviderId().equalsIgnoreCase(info.getMetadataSource()))
                return provider.getMetadata(info.getMetadataId());
        
        return null;
    }
}
