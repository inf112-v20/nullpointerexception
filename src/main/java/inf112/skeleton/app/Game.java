package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class Game implements ApplicationListener {
    private static final float MAP_SIZE_X = 300 * 5;
    private static final float MAP_SIZE_Y = 300 * 5;
    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMap map;
    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer flagLayer;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private Player player;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        map = new TmxMapLoader().load("assets/untitled.tmx");
        createLayers(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_SIZE_X, MAP_SIZE_Y);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(camera);
        player = new Player(map);
    }

    private void createLayers(TiledMap board) {
        //boardLayer = (TiledMapTileLayer) board.getLayers().get("Board");
        holeLayer = (TiledMapTileLayer) board.getLayers().get("Hole");
        playerLayer = (TiledMapTileLayer) board.getLayers().get("Player");
        flagLayer = (TiledMapTileLayer) board.getLayers().get("Flag");
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);


        if (flagLayer.getCell((int) player.getX(), (int) player.getY()) != null)
            player.getLayer().setCell((int) player.getX(), (int) player.getY(), player.setPlayerToWon());
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
