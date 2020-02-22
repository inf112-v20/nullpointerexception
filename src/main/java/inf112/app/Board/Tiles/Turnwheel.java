package inf112.app.Board.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Turnwheel implements ITile {

    TiledMapTileLayer.Cell tileCell;

    public Turnwheel(TiledMapTileLayer.Cell tileCell) {
        this.tileCell = tileCell;
    }

    @Override
    public int getID() {
        return tileCell.getTile().getId();
    }


}
