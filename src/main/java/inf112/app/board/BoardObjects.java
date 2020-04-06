package inf112.app.board;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.Game;
import inf112.app.player.Direction;
import inf112.app.player.Position;

import java.util.Map;

public class BoardObjects {

    private final Game game;
    private Map<String, TiledMapTileLayer> board;

    public BoardObjects(Map<String, TiledMapTileLayer> board, Game game) {
        this.board = board;
        this.game = game;
    }

    public void setTile(Position pos, String layer, TiledMapTileLayer.Cell newValue) {
        board.get("layer").setCell(pos.getX(), pos.getY(), newValue);
    }

    /**
     * Returns true if the tile has a wall.
     *
     * @param pos    position of the player
     * @param newPos the position the player would have if he moves
     * @param dir    direction of the player
     * @return true or false depending on if there is a wall or not
     */
    public boolean tileHasWall(Position pos, Position newPos, Direction dir) {
        if (board.get("wall" + dir.getName())
                .getCell(pos.getX(), pos.getY()) != null)
            return true;

        return board.get("wall" + dir.reverseDirection().getName())
                .getCell(newPos.getX(), newPos.getY()) != null;
    }

    /**
     * Returns true if the tile has an actor
     *
     * @param pos position of the actor
     * @return true or false
     */
    public boolean tileHasActor(Position pos) {
        return board.get("actor").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Returns true if the tile has a flag.
     *
     * @param pos position of the player
     * @return true or false
     */
    public boolean tileHasFlag(Position pos) {
        return board.get("flag").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Returns true if the tile has a spawn.
     *
     * @param pos position of the player
     * @return true or false
     */
    public boolean tileHasSpawn(Position pos) {
        return board.get("spawn").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Returns true if the tile has a wheel.
     *
     * @param pos position of the player
     * @param dir direction of the player
     * @return true or false
     */
    public boolean tileHasTurnWheel(Position pos, Direction dir) {
        return board.get("turnwheel").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Returns true if the tile is a hole.
     *
     * @param pos position of the player
     * @return true or false
     */
    public boolean tileHasHole(Position pos) {
        return board.get("hole").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Checks if the current tile has a laser.
     *
     * @param pos Position of the player
     * @return true or false
     */
    public boolean tileHasLaser(Position pos) {
        return board.get("laser").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Checks if the current tile has a repair kit.
     *
     * @param pos position of the player
     * @return boolean true or false
     */
    public boolean tileHasRepair(Position pos) {
        return board.get("repair kit").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Checks if the current tile has a conveyor belt.
     *
     * @param pos position of the player
     * @return boolean true or false
     */
    public boolean hasConveyor(Position pos) {
        for (Direction dir : Direction.values()) { // Checks for a conveyor tile in all directions
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(pos.getX(), pos.getY());
            if (currentCell != null)
                return true;
        }
        return false;
    }


    /**
     * Checks the direction of the conveyor belt
     *
     * @param pos position of the player
     * @return direction of the conveyor
     */
    public Direction conveyorDirection(Position pos) {
        for (Direction dir : Direction.values()) { // Checks for a conveyor tile in all directions
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(pos.getX(), pos.getY());
            if (currentCell != null)
                return dir;
        }
        return null;
    }


    /**
     * Checks if the current tile has a express conveyor belt.
     *
     * @param pos position of the player
     * @return boolean true or false
     */
    public boolean hasExpressConveyor(Position pos) {
        return board.get("expressconveyor").getCell(pos.getX(), pos.getY()) != null;
    }
}
