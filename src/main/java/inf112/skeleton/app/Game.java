package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class Game implements ApplicationListener {
    private static final float MAP_SIZE_X = 300 * 5;
    private static final float MAP_SIZE_Y = 300 * 5;

    private TiledMap map;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer flagLayer;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;

    /**
     * Initializing a map, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    @Override
    public void create() {
        map = new TmxMapLoader().load("assets/untitled.tmx");
        player = new Player(map);
        createLayers(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(camera);

    }

    /**
     * Initializing the layers responsible for holes, flags and players.
     * @param board
     */
    private void createLayers(TiledMap board) {
        playerLayer = player.getLayer();
        holeLayer = (TiledMapTileLayer) board.getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) board.getLayers().get("Flag");
    }

    @Override
    public void dispose() {
       renderer.dispose();
    }

    /**
     * A loop method which renders the changes on the screen
     * Shows the player default/winning/dying state on the board
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        //If a player is on the position of the flag, change the image of a player to winning-state
        if (flagLayer.getCell((int) player.getX(), (int) player.getY()) != null)
            player.getLayer().setCell((int) player.getX(), (int) player.getY(), player.setPlayerToWon());
        //If a player stands on the hole-cell, change the image to dying-state
        else if (holeLayer.getCell((int) player.getX(), (int) player.getY()) != null)
            playerLayer.setCell((int) player.getX(), (int) player.getY(), player.setPlayerToDead());
        else
            playerLayer.setCell((int) player.getX(), (int) player.getY(), player.setPlayerToDefault());

        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
