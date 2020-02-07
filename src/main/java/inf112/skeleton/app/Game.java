package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class Game implements ApplicationListener {
    private Board board;
    private Player player;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private TiledMapTileLayer holeLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer flagLayer;

    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    @Override
    public void create() {
        board = new Board();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * Board.TILE_SIZE,
                board.getBoardHeight() * Board.TILE_SIZE);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(board.getBoard());
        renderer.setView(camera);

        createLayers();

        player = new Player(board.getBoard());
    }

    /**
     * Initializing the layers responsible for holes, flags and players.
     */
    private void createLayers() {
        playerLayer = (TiledMapTileLayer) board.getBoard().getLayers().get("Player");
        holeLayer = (TiledMapTileLayer) board.getBoard().getLayers().get("Hole");
        flagLayer = (TiledMapTileLayer) board.getBoard().getLayers().get("Flag");
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

        checkPlayerState();

        renderer.render();
    }

    /**
     * Checks which imagine to show to the screen depending on player state
     */
    private void checkPlayerState() {
        //If a player is on the position of the flag, change the image of a player to winning-state
        if (flagLayer.getCell((int) player.getX(), (int) player.getY()) != null)
            player.getLayer().setCell((int) player.getX(), (int) player.getY(), player.setPlayerToWon());
            //If a player stands on the hole-cell, change the image to dying-state
        else if (holeLayer.getCell((int) player.getX(), (int) player.getY()) != null)
            playerLayer.setCell((int) player.getX(), (int) player.getY(), player.setPlayerToDead());
        else
            playerLayer.setCell((int) player.getX(), (int) player.getY(), player.setPlayerToDefault());
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
