package net.landora.animeinfo.data;

public enum RelationType {

    Sequel(1),
    Prequel(2),
    SameSetting(11),
    AlternativeSetting(21),
    AlternativeVersion(32),
    MusicVideo(41),
    Character(42),
    SideStory(51),
    ParentStory(52),
    Summary(61),
    FullStory(62),
    Other(100);

    
    private int type;
    private String name;

    private RelationType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private RelationType(int type) {
        this.type = type;
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

    public int getType() {
        return type;
    }

    public static RelationType lookupType(int id) {
        return lookupType(id, 1);
    }
    
    public static RelationType lookupType(int id, int pass) {

        for (RelationType type : values()) {
            if (type.getType() == id) {
                return type;
            }
        }
        
        switch(pass) {
            case 1:
                return lookupType(id - 1, 2);
            case 2:
                return lookupType(id + 2, 3);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return getName();
    }


}
