package inf112.app.Board;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.Game;
import inf112.app.player.Direction;
import inf112.app.player.Position;

import java.util.Map;

public class BoardObjects {

    private final Game game;
    Map<String, TiledMapTileLayer> board;

    public BoardObjects(Map<String, TiledMapTileLayer> board, Game game) {
        this.board = board;
        this.game = game;
    }

    public boolean tileHasWall(Position playerPos, Position newPos, Direction dir) {
        if (board.get("wall" + dir.getName())
                .getCell(playerPos.getX(), playerPos.getY()) != null)
            return true;

        return board.get("wall" + dir.reverseDirection().getName())
                .getCell(newPos.getX(), newPos.getY()) != null;
    }

    public boolean tileHasFlag(Position pos) {
        return board.get("flag").getCell(pos.getX(), pos.getY()) != null;
    }

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

    public boolean tileHasRepair(Position pos) {
        return board.get("repair kit").getCell(pos.getX(), pos.getY()) != null;
    }


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
     * @param pos       Position of the player
     * @param playerDir direction of the player
     * @return true or false
     */
    public boolean tileHasConveyor(Position pos, Direction playerDir) {

        for (Direction dir : Direction.values()) {
            TiledMapTileLayer.Cell currentCell = board
                    .get("conveyor" + dir.getName())
                    .getCell(pos.getX(), pos.getY());

            if (currentCell != null) {
                if (board.get("expressconveyor").getCell(pos.getX(), pos.getY()) != null) {
                    game.movePlayer(game.getPlayerPos(), dir);
                    System.out.println("express");
                    if (game.outOfBoard(pos.getNextPos(dir)))
                        return false;
                    hasTurn(pos, playerDir);
                }
                game.movePlayer(game.getPlayerPos(), dir);
                hasTurn(pos, playerDir);
                return true;
            }
        }
        return false;
    }
}
