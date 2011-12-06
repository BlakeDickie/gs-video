/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.filestate.data;

/**
 *
 * @author bdickie
 */
public class SharedDirectory implements java.io.Serializable {
    private int directoryId;
    private String uuid;
    private String name;
    private String defaultPath;
    private boolean renameNewFiles;

    public SharedDirectory() {
    }

    public int getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(int directoryId) {
        this.directoryId = directoryId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRenameNewFiles() {
        return renameNewFiles;
    }

    public void setRenameNewFiles(boolean renameNewFiles) {
        this.renameNewFiles = renameNewFiles;
    }

    

    
    
    @Override
    public String toString() {
        return (getName() == null || getName().trim().isEmpty() ? "--Unnamed--" : getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SharedDirectory other = (SharedDirectory) obj;
        if ((this.uuid == null) ? (other.uuid != null) : !this.uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.uuid != null ? this.uuid.hashCode() : 0);
        return hash;
    }
    
    
    
    
}
