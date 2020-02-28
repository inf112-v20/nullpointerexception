package inf112.app.player;

public class Position {

    private int xCoordinate;
    private int yCoordinate;

    public Position(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    public Position(Position pos) {
        xCoordinate = pos.getX();
        yCoordinate = pos.getY();
    }

    /**
     * Returns the x coordinate
     *
     * @return int value
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * Returns the y coordinate
     *
     * @return int value
     */
    public int getY() {
        return yCoordinate;
    }


    /**
     * Returns the next position on a grid in a certain direction
     *
     * @param dir direction of the next pos
     * @return new position
     */
    public Position getNextPos(Direction dir) {
        switch (dir) {
            case NORTH:
                return new Position(xCoordinate, yCoordinate + 1);
            case EAST:
                return new Position(xCoordinate + 1, yCoordinate);
            case SOUTH:
                return new Position(xCoordinate, yCoordinate - 1);
            case WEST:
                return new Position(xCoordinate - 1, yCoordinate);
            default:
                throw new IllegalStateException("Unexpected value: " + dir);
        }
    }


    /**
     * Compares two objects
     *
     * @param object the object that is being compared
     * @return true if its the same object, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return xCoordinate == position.xCoordinate &&
                yCoordinate == position.yCoordinate;
    }
}
