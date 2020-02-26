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

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

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
}
