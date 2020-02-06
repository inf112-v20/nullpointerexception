package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Player extends InputAdapter {

    private TiledMapTileLayer playerLayer;
    private Texture playerTexture;
    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;
    //Vector holds players position
    private Vector2 player;

    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param board board object
     */
    public Player(TiledMap board) {
        playerLayer = (TiledMapTileLayer) board.getLayers().get("Player");
        playerTexture = new Texture("assets/player.png");
        TextureRegion[][] playerPictures = TextureRegion.split(playerTexture, 300, 300);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][0]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][1]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][2]));

        player = new Vector2();
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Returns a layer consisting of players
     *
     * @return the layer with player(s)
     */
    public TiledMapTileLayer getLayer() {
        return playerLayer;
    }

    /**
     * Refreshing the former players position to null
     * Implements the board-movement of a player
     * Prints out the current position
     *
     * @param keycode - an integer representation of different possible inputs
     * @return true/false
     */
    @Override
    public boolean keyUp(int keycode) {
        playerLayer.setCell((int) player.x, (int) player.y, null);

        if (keycode == Input.Keys.RIGHT)
            player.x += 1;
        if (keycode == Input.Keys.LEFT)
            player.x -= 1;
        if (keycode == Input.Keys.UP)
            player.y += 1;
        if (keycode == Input.Keys.DOWN)
            player.y -= 1;
        return super.keyDown(keycode);
    }

    /**
     * Sets the current player state to dead
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setPlayerToDead() {
        return playerDiedCell;
    }

    /**
     * Sets the current player state to won
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setPlayerToWon() {
        return playerWonCell;
    }

    /**
     * Sets the current player state to default
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setPlayerToDefault() {
        return playerCell;
    }

    /**
     * Returns the x position on the grid for the player
     *
     * @return x value
     */
    public float getX() {
        return player.x;
    }

    /**
     * Returns the y position on the grid for the player
     *
     * @return y value
     */
    public float getY() {
        return player.y;
    }
}
