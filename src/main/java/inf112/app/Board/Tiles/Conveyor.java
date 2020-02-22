package inf112.app.Board.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Conveyor implements IConveyor {

    private final TiledMapTileLayer.Cell conveyorCell;

    public Conveyor(TiledMapTileLayer.Cell conveyorCell) {
        this.conveyorCell = conveyorCell;
    }

    @Override
    public int getDirection() {
        return 0;
    }

    @Override
    public boolean hasTurn() {
        return false;
    }

    @Override
    public int getID() {
        return conveyorCell.getTile().getId();
    }
}
