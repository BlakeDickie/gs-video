/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.utils;

/**
 *
 * @author bdickie
 */
public class OSUtils {

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("os x");
    }

    public static boolean isUnix() {
        return !isWindows();
    }
    
    public static boolean useSystemLAF() {
        return isWindows() || isMac();
    }
}
