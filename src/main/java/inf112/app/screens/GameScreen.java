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

import java.util.ArrayList;

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
    private Texture health;

    private Button move1;
    private Button move2;
    private Button move3;
    private Button right_turn;
    private Button left_turn;
    private Button backup;
    private Button turn_180;
    private Button powerup;

    private OrthographicCamera camera;
    private ArrayList<Button> hand;
    public GameScreen() {
        game = new inf112.app.Game();
        board = game.getBoard();
        powerdown = new Button("powerdown.png");
        powerdown.setButtonCoords(0, board.getBoardHeight() * TILE_SIZE + 500);
        lifes = new Texture("life.png");
        health = new Texture("healthpoints.png");

        move1 = new Button("cards/move1_card.png");
        move1.setButtonCoords(powerdown.getButtonX() + powerdown.getButtonTexture().getWidth() + 500, board.getBoardHeight() * TILE_SIZE + 200);

        move2= new Button("cards/move2_card.png");
        move2.setButtonCoords(move1.getButtonX() + move1.getButtonTexture().getWidth() + 340, board.getBoardHeight() * TILE_SIZE + 200);

        move3= new Button("cards/move3_card.png");
        move3.setButtonCoords(move2.getButtonX() + move2.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);

        right_turn= new Button("cards/right_turn_card.jpg");
        right_turn.setButtonCoords(move3.getButtonX() + move3.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);

        left_turn= new Button("cards/left_turn_card.jpg");
        left_turn.setButtonCoords(right_turn.getButtonX() + right_turn.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);

        backup= new Button("cards/backup_card.jpg");
        backup.setButtonCoords(left_turn.getButtonX() + left_turn.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);

        turn_180= new Button("cards/180_turn_card.jpg");
        turn_180.setButtonCoords(backup.getButtonX() + backup.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);

        powerup= new Button("cards/powerup_card.jpg");
        powerup.setButtonCoords(turn_180.getButtonX() + turn_180.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);

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
        int healthpoints = game.getPlayersHitPoints();
        GameRunner.batch.begin();

        GameRunner.batch.draw(powerdown.getButtonTexture(), powerdown.getButtonX(), powerdown.getButtonY(), 350, 350);
        GameRunner.batch.draw(lifes, 20, board.getBoardHeight() * TILE_SIZE + 50, 150, 150);
        GameRunner.batch.draw(health, 0, board.getBoardHeight() * TILE_SIZE + 50 + 200, 220, 220);
        GameRunner.batch.draw(move1.getButtonTexture(), move1.getButtonX(), move1.getButtonY(), 500, 600);
        GameRunner.batch.draw(move2.getButtonTexture(), move2.getButtonX(), move2.getButtonY(), 500, 600);
        GameRunner.batch.draw(move3.getButtonTexture(), move3.getButtonX(), move3.getButtonY(), 500, 600);
        GameRunner.batch.draw(right_turn.getButtonTexture(), right_turn.getButtonX(), right_turn.getButtonY(), 500, 600);
        GameRunner.batch.draw(left_turn.getButtonTexture(), left_turn.getButtonX(), left_turn.getButtonY(), 500, 600);
        GameRunner.batch.draw(turn_180.getButtonTexture(), turn_180.getButtonX(), turn_180.getButtonY(), 500, 600);
        GameRunner.batch.draw(backup.getButtonTexture(), backup.getButtonX(), backup.getButtonY(), 500, 600);
        GameRunner.batch.draw(powerup.getButtonTexture(), powerup.getButtonX(), powerup.getButtonY(), 500, 600);

        font.getData().setScale(7, 7);
        font.setColor(Color.BLACK);
        font.draw(GameRunner.batch, "x " + lifepoints, 170, board.getBoardHeight() * TILE_SIZE + 50 + lifes.getHeight() / 2);
        font.draw(GameRunner.batch, "x " + healthpoints, 210, board.getBoardHeight() * TILE_SIZE + 400 + health.getHeight()/2);
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
