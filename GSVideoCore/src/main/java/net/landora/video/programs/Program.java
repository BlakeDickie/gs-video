/**
 *     Copyright (C) 2012 Blake Dickie
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
