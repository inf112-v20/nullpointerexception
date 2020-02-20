package inf112.app;

public enum Directions {
    NORTH(2),
    EAST(1),
    SOUTH(0),
    WEST(3);

    private final int id;

    Directions(int id) {
        this.id = id;
    }

    public static String getName(Directions dir) {
        if (dir == NORTH)
            return "N";
        if (dir == EAST)
            return "E";
        if (dir == SOUTH)
            return "S";
        if (dir == WEST)
            return "W";
        return "";
    }

    public static Directions reverse(Directions dir) {
        if (dir == NORTH)
            return SOUTH;
        if (dir == EAST)
            return WEST;
        if (dir == SOUTH)
            return NORTH;
        if (dir == WEST)
            return EAST;
        return null;
    }

    public int getID() {
        return this.id;
    }
}
