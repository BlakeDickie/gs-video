/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.video.programs;

import java.util.List;

/**
 *
 * @author bdickie
 */
public final class Program {
    private String id;
    private String name;
    private String defaultUnixPath;
    private String defaultWindowsPath;
    private List<String> testArguments;

    public Program(String id, String name, String defaultUnixPath, String defaultWindowsPath, List<String> testArguments) {
        this.id = id;
        this.name = name;
        this.defaultUnixPath = defaultUnixPath;
        this.defaultWindowsPath = defaultWindowsPath;
        this.testArguments = testArguments;
    }

    public Program(String id, String name, String defaultPath, List<String> testArguments) {
        this(id, name, defaultPath, defaultPath, testArguments);
    }

    public String getDefaultUnixPath() {
        return defaultUnixPath;
    }

    public String getDefaultWindowsPath() {
        return defaultWindowsPath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTestArguments() {
        return testArguments;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
}
