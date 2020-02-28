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
     * Checks if a certain tile has a wall or are blocking the player from moving
     *
     * @param playerPos the current position of the player
     * @param newPos    the position the player has if he moves
     * @param dir       direction of the player
     * @return true if there is a wall, false if not
     */
    public boolean tileHasWall(Position playerPos, Position newPos, Direction dir) {
        if (board.get("wall" + dir.getName())
                .getCell(playerPos.getX(), playerPos.getY()) != null)
            return true;

        return board.get("wall" + dir.reverseDirection().getName())
                .getCell(newPos.getX(), newPos.getY()) != null;
    }

    /**
     * Checks if the current tile has a flag
     *
     * @param pos Position of the player
     * @return true or false
     */
    public boolean tileHasFlag(Position pos) {
        return board.get("flag").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Checks if the current tile has a turn wheel
     *
     * @param pos Position of the player
     * @param dir Direction of the player
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
     * Checks if the current tile has a hole
     *
     * @param pos Position of the player
     * @return true or false
     */
    public boolean tileHasHole(Position pos) {
        if (board.get("hole").getCell(pos.getX(), pos.getY()) != null) {
            game.resetPlayer(pos);
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

    /**
     * Checks if the current tile has a repair kit
     *
     * @param pos position of the player
     * @return true or false
     */
    public boolean tileHasRepair(Position pos) {
        return board.get("repair kit").getCell(pos.getX(), pos.getY()) != null;
    }

    /**
     * Turns the player if the board has a object that can turn him
     *
     * @param pos       player position
     * @param playerDir player direction
     * @return true if the player was turned, false if not
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
     * Checks if the current tile has a track.
     *
     * @return true or false
     */
    public boolean tileHasConveyor(Player player) {
        for (Direction dir : Direction.values()) { // Checks which direction the conveyor is facing
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(player.getPos().getX(), player.getPos().getY());

            if (currentCell != null) {  // If the conveyor moves the player out of the board, method returns
                if (game.outOfBoard(player.getPos().getNextPos(dir)))
                    return false;

                // Moves the player along the conveyor
                Position pos = game.getPlayerPos();
                game.movePlayer(game.getPlayerPos(), dir);
                hasTurn(player.getPos(), player.getDirection());

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


    /**
     * Checks if the player is standing on an express type conveyor and moves the player accordingly
     *
     * @param player                    the current player which is being moved
     * @param previousConveyorDirection the direction of the player
     * @return true or false
     */
    public boolean tileHasExpressConveyor(Player player, Direction previousConveyorDirection) {
        for (Direction dir : Direction.values()) { // Checks for a conveyor tile in all directions
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(player.getPos().getX(), player.getPos().getY());

            if (currentCell != null) { // If the conveyor moves the player out of the board, method returns
                if (game.outOfBoard(player.getPos().getNextPos(dir)))
                    return false;
                game.movePlayer(player.getPos(), dir);

                if (dir != previousConveyorDirection)
                    hasTurn(player.getPos(), player.getDirection());
                break;
            }
        }
        return true;
    }
}
