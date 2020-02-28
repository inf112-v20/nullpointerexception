package inf112.app.board;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.Game;
import inf112.app.player.Direction;
import inf112.app.player.Player;
import inf112.app.player.Position;

import java.util.Map;

public class BoardObjects {

    private final Game game;
    private Map<String, TiledMapTileLayer> board;

    public BoardObjects(Map<String, TiledMapTileLayer> board, Game game) {
        this.board = board;
        this.game = game;
    }

    /**
     * Returns true if the tile has a wall.
     *
     * @param playerPos position of the player
     * @param newPos the position the player would have if he moves
     * @param dir direction of the player
     * @return true or false depending on if there is a wall or not
     */
    public boolean tileHasWall(Position playerPos, Position newPos, Direction dir) {
        if (board.get("wall" + dir.getName())
                .getCell(playerPos.getX(), playerPos.getY()) != null)
            return true;

        return board.get("wall" + dir.reverseDirection().getName())
                .getCell(newPos.getX(), newPos.getY()) != null;
    }

    /**
     * Returns true if the tile has a flag.
     * @param pos position of the player
     * @return true or false
     */
    public boolean tileHasFlag(Position pos) {
        return board.get("flag").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Returns true if the tile has a wheel.
     * @param pos position of the player
     * @param dir direction of the player
     * @return true or false
     */
    public boolean tileHasTurnWheel(Position pos, Direction dir) {
        if (board.get("turnwheel").getCell(pos.getX(), pos.getY()) != null) {
            TiledMapTileLayer.Cell currentCell = board.get("turnwheel").getCell(pos.getX(), pos.getY());
            if (currentCell.getTile().getId() == 53)
                game.turnPlayer(dir.turnLeft());
            else
                game.turnPlayer(dir.turnRight());
            return true;
        }
        return false;
    }

    /**
     * Returns true if the tile is a hole.
     * @param pos position of the player
     * @return true or false
     */
    public boolean tileHasHole(Position pos) {
        if (board.get("hole").getCell(pos.getX(), pos.getY()) != null) {
            game.resetPlayer();
            return true;
        }
        return false;
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

    public boolean tileHasRepair(Position pos) {
        return board.get("repair kit").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Returns True if the player is supposed to turn.
     * @param pos position of the player
     * @param playerDir direction of the player
     * @return true or false depending on if there is a turn tile or not
     */
    private boolean hasTurn(Position pos, Direction playerDir) {
        if (board.get("leftTurn").getCell(pos.getX(), pos.getY()) != null) {
            game.turnPlayer(playerDir.turnLeft());
            return true;
        }
        if (board.get("rightTurn").getCell(pos.getX(), pos.getY()) != null) {
            game.turnPlayer(playerDir.turnRight());
            return true;
        }
        return false;
    }

    /**
     * Returns true if the tile has a Conveyor belt
     * @param player the player object that is being moved
     * @return true or false depending on tile
     */
    public boolean tileHasConveyor(Player player) {
        for (Direction dir : Direction.values()) { // Checks which direction the conveyor is facing
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(player.getPos().getX(), player.getPos().getY());

            if (currentCell != null) {  // If the conveyor moves the player out of the board, method returns
                if (game.outOfBoard(player.getPos().getNextPos(dir))) {
                    game.resetPlayer();
                    return false;
            }
                // Moves the player along the conveyor
                Position pos = game.getPlayerPos();
                game.movePlayer(game.getPlayerPos(), dir);
                if (conveyorDirectionIsDifferent(player.getPos(), dir)) {
                    hasTurn(player.getPos(), player.getDirection());
                }
                // Checks if the player is on an express conveyor
                if (board.get("expressconveyor").getCell(player.getPos().getX(), player.getPos().getY()) != null) {
                    tileHasExpressConveyor(player, dir);
                    return !game.outOfBoard(player.getPos().getNextPos(dir));
                }
                return true;
            }
        }
        return false;
    }

    private boolean conveyorDirectionIsDifferent(Position pos, Direction currentDir) {
        for (Direction dir : Direction.values()) { // Checks which direction the conveyor is facing
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(pos.getX(), pos.getY());
            if (currentCell != null) {
                if (currentDir == dir)
                    return false;
            }
        }
        return true;
    }


    /**
     * Returns true if the tile has an express conveyor.
     *
     * @param player player object that is being moved
     * @param previousConveyorDirection the direction of the conveyor that just moved the player
     * @return true or false depending on tile
     */
    public boolean tileHasExpressConveyor(Player player, Direction previousConveyorDirection) {
        for (Direction dir : Direction.values()) { // Checks for a conveyor tile in all directions
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(player.getPos().getX(), player.getPos().getY());

            if (currentCell != null) { // If the conveyor moves the player out of the board, method returns
                if (game.outOfBoard(player.getPos().getNextPos(dir))) {
                    game.resetPlayer();
                    return false;
                }
                game.movePlayer(player.getPos(), dir);

                if (conveyorDirectionIsDifferent(player.getPos(), dir)) {
                    hasTurn(player.getPos(), player.getDirection());
                }

                break;
            }
        }
        return true;
    }
}
