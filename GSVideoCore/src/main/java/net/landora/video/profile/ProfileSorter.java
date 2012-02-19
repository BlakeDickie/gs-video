/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.profile;

import java.util.Comparator;

/**
 *
 * @author bdickie
 */
public class ProfileSorter implements Comparator<RunProfile> {

    private int getPriority(RunProfile p) {
        if (p.isManager())
            return 0;
        else if (p.isVideo())
            return 1;
        else
            return 2;
    }
    
    public int compare(RunProfile o1, RunProfile o2) {
        int cmp = getPriority(o1) - getPriority(o2);
        if (cmp != 0)
            return cmp;
        
        return o1.getProfileName().compareToIgnoreCase(o2.getProfileName());
    }
    
}
