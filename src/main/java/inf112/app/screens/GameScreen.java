package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import inf112.app.Card;
import inf112.app.Game;
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

    private Button MOVE1;
    private Button MOVE2;
    private Button MOVE3;
    private Button TURNRIGHT;
    private Button TURNLEFT;
    private Button BACKUP;
    private Button TURN180;

    private OrthographicCamera camera;
    private ArrayList<Card> dealtCards;
    private ArrayList<Button> cards = new ArrayList<>();
    private ArrayList<Button> hand = new ArrayList<>();
    private BitmapFont font = new BitmapFont();

    public GameScreen() {
        game = new Game();
        board = game.getBoard();
        dealtCards = game.getPlayersDealtCards();
        powerdown = new Button("powerdown.png");
        powerdown.setButtonCoords(0, board.getBoardHeight() * TILE_SIZE + 500);
        lifes = new Texture("life.png");
        health = new Texture("healthpoints.png");

        MOVE1 = new Button("cards/move1_card.png");
        cards.add(MOVE1);

        MOVE2= new Button("cards/move2_card.png");
        cards.add(MOVE2);

        MOVE3= new Button("cards/move3_card.png");
        cards.add(MOVE3);

        TURNRIGHT= new Button("cards/right_turn_card.jpg");
        cards.add(TURNRIGHT);

        TURNLEFT= new Button("cards/left_turn_card.jpg");
        cards.add(TURNLEFT);

        BACKUP= new Button("cards/backup_card.jpg");
        cards.add(BACKUP);

        TURN180= new Button("cards/180_turn_card.jpg");
        cards.add(TURN180);

        /**powerup= new Button("cards/powerup_card.jpg");
        powerup.setButtonCoords(turn_180.getButtonX() + turn_180.getButtonTexture().getWidth()+ 340, board.getBoardHeight() * TILE_SIZE + 200);
        cards.add(powerup);**/

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
        int lifepoints = game.getPlayersLifeCount();
        int healthpoints = game.getPlayersHitPoints();
        GameRunner.batch.begin();

        //Current mouse position on Y-axis
        int mousePosition_Y = Gdx.input.getY();
        //Current mouse position on X-axis
        int mousePosition_X = Gdx.input.getX();

        /**
         * Vector point of a current mouse position
         * unprojecting the mouse position so that it matches the worlds coordinates.
         * (Default mouse position at Y-axis in Gdx.input is the opposite of our worlds Y-axis coords, thats why
         * we need to unproject it)
         */
        Vector3 input = new Vector3(mousePosition_X, mousePosition_Y, 0);
        camera.unproject(input);


        GameRunner.batch.draw(powerdown.getButtonTexture(), powerdown.getButtonX(), powerdown.getButtonY(), 350, 350);
        GameRunner.batch.draw(lifes, 20, board.getBoardHeight() * TILE_SIZE + 50, 150, 150);
        GameRunner.batch.draw(health, 0, board.getBoardHeight() * TILE_SIZE + 50 + 200, 220, 220);
        font.getData().setScale(7, 7);
        font.setColor(Color.BLACK);
        font.draw(GameRunner.batch, "x " + lifepoints, 170, board.getBoardHeight() * TILE_SIZE + 50 + lifes.getHeight() / 2);
        font.draw(GameRunner.batch, "x " + healthpoints, 210, board.getBoardHeight() * TILE_SIZE + 400 + health.getHeight()/2);

        setHand();

        /**GameRunner.batch.draw(move1.getButtonTexture(), move1.getButtonX(), move1.getButtonY(), 500, 600);
        GameRunner.batch.draw(move2.getButtonTexture(), move2.getButtonX(), move2.getButtonY(), 500, 600);
        GameRunner.batch.draw(move3.getButtonTexture(), move3.getButtonX(), move3.getButtonY(), 500, 600);
        GameRunner.batch.draw(right_turn.getButtonTexture(), right_turn.getButtonX(), right_turn.getButtonY(), 500, 600);
        GameRunner.batch.draw(left_turn.getButtonTexture(), left_turn.getButtonX(), left_turn.getButtonY(), 500, 600);
        GameRunner.batch.draw(turn_180.getButtonTexture(), turn_180.getButtonX(), turn_180.getButtonY(), 500, 600);
        GameRunner.batch.draw(backup.getButtonTexture(), backup.getButtonX(), backup.getButtonY(), 500, 600);
        GameRunner.batch.draw(powerup.getButtonTexture(), powerup.getButtonX(), powerup.getButtonY(), 500, 600);**/



        /**while(hand.size() < 5) {
            /**for(Button button : dealtCards) {
                if (button.buttonIsHovered(input)) {
                    if (Gdx.input.isTouched()) {
                        hand.add(button);
                        System.out.println("A card has been added to the hand");
                        dealtCards.remove(button);
                    }
                }
            if (move1.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(move1);
                }

            } else if (move2.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(move2);
                }
            } else if (move3.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(move3);
                }
            } else if (right_turn.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(right_turn);
                }
            } else if (left_turn.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(left_turn);
                }
            } else if (backup.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(backup);
                }
            } else if (turn_180.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(turn_180);
                }
            } else if (powerup.buttonIsHovered(input)) {
                if (Gdx.input.isTouched()) {
                    hand.add(powerup);
                }
            }
        }**/
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
        powerdown.getButtonTexture().dispose();
        lifes.dispose();
        health.dispose();
        MOVE1.getButtonTexture().dispose();
        MOVE2.getButtonTexture().dispose();
        MOVE3.getButtonTexture().dispose();
        BACKUP.getButtonTexture().dispose();
        TURN180.getButtonTexture().dispose();
        TURNLEFT.getButtonTexture().dispose();
        TURNRIGHT.getButtonTexture().dispose();
        GameRunner.batch.dispose();
    }
    private void setHand(){
        int x = powerdown.getButtonX() + 300;
        int y = board.getBoardHeight() * TILE_SIZE + 200;
        System.out.println(dealtCards.size());
        for(Card card:dealtCards) {
            x+= MOVE1.getButtonTexture().getWidth() + 250;
            switch (card.getType()) {
                case MOVE1:
                    GameRunner.batch.draw(MOVE1.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                case MOVE2:
                    GameRunner.batch.draw(MOVE2.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                case MOVE3:
                    GameRunner.batch.draw(MOVE3.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                case TURN180:
                    GameRunner.batch.draw(TURN180.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                case TURNLEFT:
                    GameRunner.batch.draw(TURNLEFT.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                case TURNRIGHT:
                    GameRunner.batch.draw(TURNRIGHT.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                case BACKUP:
                    GameRunner.batch.draw(BACKUP.getButtonTexture(), x, y, 390, 490);
                    font.draw(GameRunner.batch, ""+ card.getPriority(), x + 100, y + 600);
                    break;
                }
        }
        /**while(dealtCards.size() > 4){

        }**/
    }




}
