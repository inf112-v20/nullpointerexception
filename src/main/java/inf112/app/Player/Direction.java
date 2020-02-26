package inf112.app.Player;

public enum Direction {
    NORTH(2),
    EAST(1),
    SOUTH(0),
    WEST(3);

    private final int id;

    Direction(int id) {
        this.id = id;
    }

    public String getName() {
        if (this == NORTH)
            return "N";
        if (this == EAST)
            return "E";
        if (this == SOUTH)
            return "S";
        if (this == WEST)
            return "W";
        return "";
    }

    public Direction reverseDirection() {
        if (this == NORTH)
            return SOUTH;
        if (this == EAST)
            return WEST;
        if (this == SOUTH)
            return NORTH;
        if (this == WEST)
            return EAST;
        return null;

    }


    public Direction turnLeft() {
        if (this == NORTH)
            return WEST;
        if (this == EAST)
            return NORTH;
        if (this == SOUTH)
            return EAST;
        if (this == WEST)
            return SOUTH;
        return null;
    }

    public Direction turnRight() {
        if (this == NORTH)
            return EAST;
        if (this == EAST)
            return SOUTH;
        if (this == SOUTH)
            return WEST;
        if (this == WEST)
            return NORTH;
        return null;
    }

    /**
     * An int value of an direction.
     *
     * @return direction id
     */
    public int getID() {
        return this.id;
    }
}
