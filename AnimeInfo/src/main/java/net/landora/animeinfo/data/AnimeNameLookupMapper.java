/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.landora.animeinfo.data;

import java.util.List;

/**
 *
 * @author bdickie
 */
public interface AnimeNameLookupMapper {

    public void insertAnime(AnimeNameLookupSummary anime);
    public void insertAnimeName(AnimeNameLookup name);
    public void deleteAnimeNames();

    public AnimeNameLookupSummary selectAnimeNameLookupSummary(int animeId);

    public List<AnimeNameLookup> selectAnimeNames(int animeId);
    public List<AnimeNameLookup> selectNames(String animeName);
    public List<AnimeNameLookup> searchNames(String escapedAnimeName);
    public List<AnimeNameLookup> selectNamesFull(String animeName);
    public List<AnimeNameLookup> searchNamesFull(String escapedAnimeName);
    
    public void updateExistingAnimeMainName();
    public void deleteCoreAnimeNames();
    public void insertExistingAnimeMainName();
}
