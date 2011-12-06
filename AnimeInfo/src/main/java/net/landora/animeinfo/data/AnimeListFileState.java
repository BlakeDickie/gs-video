/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.landora.animeinfo.data;

/**
 *
 * @author bdickie
 */
public enum AnimeListFileState {
    Normal(0),
    Corrupted(1),
    SelfEdited(2),
    SelfRipped(10),
    OnDVD(11, "On DVD"),
    OnVHS(12, "On VHS"),
    OnTV(13, "On TV"),
    InTheaters(14),
    Streamed(15),
    Other(100);
    
//    0 - unknown - state is unknown or the user doesn't want to provide this information
// 1 - on hdd - the file is stored on hdd (but is not shared)
// 2 - on cd - the file is stored on cd
// 3 - deleted - the file has been deleted or is not available for other reasons (i.e. reencoded)
    
    
    private int stateId;
    private String name;

    private AnimeListFileState(int stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    private AnimeListFileState(int stateId) {
        this.stateId = stateId;
        StringBuilder buffer = new StringBuilder();
        name = name();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && java.lang.Character.isUpperCase(c)) {
                buffer.append(" ");
            }
            buffer.append(c);
        }
        name = buffer.toString();
    }

    public String getName() {
        return name;
    }

    public int getStateId() {
        return stateId;
    }

    public static AnimeListFileState lookupType(int id) {
        
        for (AnimeListFileState type : values()) {
            if (type.getStateId() == id) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
