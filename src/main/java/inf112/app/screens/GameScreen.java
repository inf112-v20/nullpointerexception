package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import inf112.app.board.Board;

public class GameScreen extends ScreenAdapter {

    private Stage stage;
    private Table table;
    private TextButton button;
    private Skin skin;
    private static final int TILE_SIZE = 300;
    private OrthogonalTiledMapRenderer renderer;
    private inf112.app.Game game;
    private Board board;
    private Button powerdown;
    private Texture lifes;
    private OrthographicCamera camera;

    public GameScreen() {
        game = new inf112.app.Game();
        board = game.getBoard();
        powerdown = new Button("powerdown.png");
        powerdown.setButtonCoords(0, board.getBoardHeight() * TILE_SIZE + 500);
        lifes = new Texture("life.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE + 1000);
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
        /**stage.act(v);
         stage.draw();
         **/

        GameRunner.batch.setProjectionMatrix(camera.combined);
        BitmapFont font = new BitmapFont();
        int lifepoints = game.getPlayersLifeCount();
        GameRunner.batch.begin();
        GameRunner.batch.draw(powerdown.getButtonTexture(), powerdown.getButtonX(), powerdown.getButtonY(), 350, 350);
        GameRunner.batch.draw(lifes, 0, board.getBoardHeight() * TILE_SIZE + 100, 150, 150);
        font.getData().setScale(7, 7);
        font.setColor(Color.BLACK);
        font.draw(GameRunner.batch, "x " + lifepoints, 150, board.getBoardHeight() * TILE_SIZE + 100 + lifes.getHeight() / 2);
        GameRunner.batch.end();

    }

    /**@Override public void show(){
    stage = new Stage();
    skin = new Skin();
    table = new Table();
    table.setBounds(0,board.getBoardHeight() + 1000,board.getBoardWidth(),1000);
    button = new TextButton("HI",skin);
    table.add(button);
    stage.addActor(table);
    }
     **/
    /**
     * Called when the Application is destroyed. Preceded by a call to pause().
     */
    @Override
    public void dispose() {
        renderer.dispose();
        GameRunner.batch.dispose();
    }

}
