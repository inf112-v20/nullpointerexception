package inf112.app.Board;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.Board.Player.Directions;
import inf112.app.Board.Player.Position;
import inf112.app.Game;

import java.util.Map;

public class BoardObjects {

    private final Game game;
    Map<String, TiledMapTileLayer> board;

    public BoardObjects(Map<String, TiledMapTileLayer> board, Game game) {
        this.board = board;
        this.game = game;
    }

    public boolean tileHasWall(Position playerPos, Position newPos, Directions dir) {
        if (board.get("wall" + Directions.getName(dir))
                .getCell(playerPos.getX(), playerPos.getY()) != null)
            return true;

        return board.get("wall" + Directions.getName(Directions.reverse(dir)))
                .getCell(newPos.getX(), newPos.getY()) != null;
    }

    public boolean tileHasFlag(Position pos) {
        return board.get("flag").getCell(pos.getX(), pos.getY()) != null;
    }

    public boolean tileHasTurnWheel(Position pos) {
        return board.get("turnwheel").getCell(pos.getX(), pos.getY()) != null;
    }

    public boolean tileHasHole(Position pos) {
        return board.get("hole").getCell(pos.getX(), pos.getY()) != null;
    }

    public boolean tileHasTrack(Position pos, Directions dir) {
        if (board.get("conveyor" + Directions.getName(dir)).getCell(pos.getX(), pos.getY()) != null) {
            game.movePlayer(pos.getNextPos(dir));
            System.out.println("PLayer was moved by a conveyorbelt");
            return true;
        }

        return false;
    }

    public boolean tileHasLaser(Position pos) {
        return board.get("laser").getCell(pos.getX(), pos.getY()) != null;
    }

    public boolean tileHasRepair(Position pos) {
        return board.get("repair kit").getCell(pos.getX(), pos.getY()) != null;
    }
}
