package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.app.board.Board;

public class GameScreen extends ScreenAdapter {

    private static final int TILE_SIZE = 300;
    private OrthogonalTiledMapRenderer renderer;
    private inf112.app.Game game;

    public GameScreen() {
        this.game = new inf112.app.Game();
        Board board = game.getBoard();


        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(board.getBoard());
        renderer.setView(camera);


    }

    /**
     * A loop method which renders the changes on the screen
     * Shows the player default/winning/dying state on the board
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();
    }


    /**
     * Called when the Application is destroyed. Preceded by a call to pause().
     */
    @Override
    public void dispose() {
        renderer.dispose();
    }

}
