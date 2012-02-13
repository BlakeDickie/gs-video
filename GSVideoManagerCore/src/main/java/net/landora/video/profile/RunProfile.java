package net.landora.video.profile;

/**
 *
 * @author bdickie
 */
public interface RunProfile {
    /** A short description of the profile without spaces.
     * 
     * @return The name of the profile.
     */
    public String getProfileName();
    /** A one sentence description of the profile.
     * 
     * @return The description of the profile.
     */
    public String getProfileDescription();
    
    /** Returns true iff this is the manager profile.  No custom profiles should return true to this.
     * 
     * @return Is this the manager profile.
     */
    public boolean isManager();
    
    /** Returns true iff this is the viewer profile.  No custom profiles should return true to this.
     * 
     * @return Is this the viewer profile.
     */
    public boolean isVideo();
    
    /** Returns true iff this is at tool profile.  All custom profiles should return true to this.
     * 
     * @return Is this a tool profile.
     */
    public boolean isTool();
    
    /** Return true iff a Swing GUI will be used for this profile.
     * 
     * @return Will a Swing GUI be used. 
     */
    public boolean isGUIEnabled();
    
    /** Passes the command line argument to the profile for parsing.
     * 
     * @param args The command line arguments passed after the profile name.
     * @return True is the command line arguments where successfully read and the running should continue.
     */
    public boolean readCommandLine(String[] args);
    
    /** Passed control to the profile.
     * 
     * @return A non-null exit code if the execution should end after the profile can been ran.  A null value will cause the 
     *  system to assume that the profile will handle exiting itself.
     */
    public Integer runProfile();
}
