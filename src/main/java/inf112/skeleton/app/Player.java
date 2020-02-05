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
    private Vector2 player;


    public Player(TiledMap map) {
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        playerTexture = new Texture("assets/player.png");
        TextureRegion[][] playerPictures = TextureRegion.split(playerTexture, 300, 300);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][0]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][1]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][2]));

        player = new Vector2();


        Gdx.input.setInputProcessor(this);
    }

    public TiledMapTileLayer getLayer() {
        return playerLayer;
    }

//heihei test
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

        System.out.println(player.x);
        System.out.println(player.y);
        return super.keyDown(keycode);
    }


    public TiledMapTileLayer.Cell setPlayerToDead() {
        return playerDiedCell;
    }

    public TiledMapTileLayer.Cell setPlayerToWon() {
        return playerWonCell;
    }

    public TiledMapTileLayer.Cell setPlayerToDefault() {
        return playerCell;
    }

    public float getX() {
        return player.x;
    }

    public float getY() {
        return player.y;
    }
}
