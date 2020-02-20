package inf112.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Map;

public class BoardObjects {

    Map<String, TiledMapTileLayer> board;

    public BoardObjects(Map<String, TiledMapTileLayer> board) {
        this.board = board;
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

    public boolean tileHasTrack(Position pos) {
        return board.get("conveyorbelt").getCell(pos.getX(), pos.getY()) != null;
    }

    public boolean tileHasLaser(Position pos) {
        return board.get("laser").getCell(pos.getX(), pos.getY()) != null;
    }
}
