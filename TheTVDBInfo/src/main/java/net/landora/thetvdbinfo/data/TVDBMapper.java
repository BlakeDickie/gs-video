/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.thetvdbinfo.data;

/**
 *
 * @author bdickie
 */
public interface TVDBMapper {
    public void deleteShowById(int id);
    public void deleteShow(TVShow show);
    public void insertShow(TVShow show);
    public void updateShow(TVShow show);
    
    public TVShow selectShow(int showId);
    
}
