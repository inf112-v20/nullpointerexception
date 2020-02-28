package inf112.app.player;

import static inf112.app.player.Direction.*;

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

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public Position getNextPos(Direction dir) {
        if (dir == NORTH)
            return new Position(xCoordinate, yCoordinate + 1);
        if (dir == EAST)
            return new Position(xCoordinate + 1, yCoordinate);
        if (dir == SOUTH)
            return new Position(xCoordinate, yCoordinate - 1);
        return new Position(xCoordinate, yCoordinate + 1);
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
