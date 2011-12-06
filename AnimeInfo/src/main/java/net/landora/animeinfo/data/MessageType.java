package net.landora.animeinfo.data;

public enum MessageType {

    Normal(0),
    Annonymous(1),
    System(2),
    Mod(3);

    
    private int type;
    private String name;

    private MessageType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private MessageType(int type) {
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

    public static MessageType lookupType(int id) {
        return lookupType(id, 1);
    }
    
    public static MessageType lookupType(int id, int pass) {

        for (MessageType type : values()) {
            if (type.getType() == id) {
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
