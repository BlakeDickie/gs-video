package net.landora.animeinfo.data;

public enum NotificationType {

    All(0),
    New(1),
    Group(2),
    Complete(3);

    
    private int type;
    private String name;

    private NotificationType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private NotificationType(int type) {
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

    public static NotificationType lookupType(int id) {
        return lookupType(id, 1);
    }
    
    public static NotificationType lookupType(int id, int pass) {

        for (NotificationType type : values()) {
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
