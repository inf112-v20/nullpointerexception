package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import inf112.app.Game;
import inf112.app.board.Board;
import inf112.app.cards.Card;
import inf112.app.cards.Deck;
import inf112.app.player.Direction;
import inf112.app.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends InputAdapter implements Screen {

    private static final int TILE_SIZE = 300;
    private final OrthogonalTiledMapRenderer renderer;
    private final inf112.app.Game game;
    private final Board board;
    private final Button powerdown;
    private final Texture lifes;
    private final Texture health;
    private final Texture flag;
    // y value for priority labels of cards
    private final int yPriority;
    private final int yCard;
    //all of the cards
    private Button MOVE1;
    private Button MOVE2;
    private Button MOVE3;
    private Button TURNRIGHT;
    private Button TURNLEFT;
    private Button BACKUP;
    private Button TURN180;
    //button to start the rounds
    private final Button start_round;

    private final OrthographicCamera camera;
    private ArrayList<Card> dealtCards;
    private final ArrayList<Card> hand;
    private ArrayList<Card> tempHand;
    private final BitmapFont font = new BitmapFont();
    private final Deck deck;
    private final Player player;
    private boolean chooseCards;
    private HashMap<Button, Card> cards;
    private boolean startOfRound = true;
    private int counter;


    public GameScreen() {
        game = new Game();
        board = game.getBoard();
        dealtCards = game.getPlayersDealtCards();
        powerdown = new Button("icons/powerdown.png");
        powerdown.setButtonCoords(0, board.getBoardHeight() * TILE_SIZE + 500);
        start_round = new Button("icons/start_round.png");
        start_round.setButtonCoords(powerdown.getButtonTexture().getWidth() + 250, board.getBoardHeight() * TILE_SIZE + 550);
        lifes = new Texture("icons/life.png");
        health = new Texture("icons/healthpoints.png");
        flag = new Texture("icons/flag.png");
        yPriority = board.getBoardHeight() * TILE_SIZE + 800;
        yCard = board.getBoardHeight() * TILE_SIZE + 200;
        deck = game.getDeckObject();
        player = game.getPlayerObject();

        hand = player.getHand();
        tempHand = new ArrayList<>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE + 1000);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(board.getBoard());
        renderer.setView(camera);
        Gdx.input.setInputProcessor(this);

        //Setting the continous rendering to false so that screen renders changes only when input occurs (saves some memory)
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
        //chooseCards = true;
    }

    /**
     * A method which is called whenever a mouse is clicked,
     * If the hand is full and the "start" button is clicked, calls the round() method and starts the rounds until a hand is empty
     * If its the start of a new phase, checks if cards are being clicked, then picks a clicked card until a hand is full
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 input = new Vector3(screenX, screenY, 0);
        camera.unproject(input);
        if(start_round.buttonIsHovered(input,270,250) && !chooseCards) {
            System.out.println("Starting round!");
            round();

        } else {
            if(chooseCards) {
                for (Button b : cards.keySet()) {
                    if (b.buttonIsHovered(input,GameRunner.CARD_WIDTH,GameRunner.CARD_HEIGHT)) {
                        if (!hand.contains(cards.get(b))) {
                            tempHand.add(cards.get(b));
                            dealtCards.remove(cards.get(b));
                            String c = cards.get(b).toString();
                            System.out.println("Added to hand: " + c);
                            if ((hand.size() + tempHand.size()) == 5) {
                                hand.addAll(0, tempHand);
                                deck.setDiscardPile(dealtCards);
                                player.resetDealtCards();
                                tempHand = new ArrayList<>();
                            }
                        } else {
                            System.out.println("hand is full/has that card");
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    public void show() {
    }

    /**
     * A loop method which renders the changes on the screen
     * Shows the player default/winning/dying state on the board
     * Shows the hud changes, works as a game loop
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();

        GameRunner.batch.setProjectionMatrix(camera.combined);
        GameRunner.batch.begin();

        int lifepoints = game.getPlayersLifeCount();
        int healthpoints = game.getPlayersHitPoints();
        int flagCount = player.getFlagCount();

        GameRunner.batch.draw(powerdown.getButtonTexture(), powerdown.getButtonX(), powerdown.getButtonY(), 350, 350);
        GameRunner.batch.draw(start_round.getButtonTexture(), start_round.getButtonX(), start_round.getButtonY(), 270, 250);
        GameRunner.batch.draw(lifes, 20, board.getBoardHeight() * TILE_SIZE, 150, 150);
        GameRunner.batch.draw(health, 0, board.getBoardHeight() * TILE_SIZE + 100, 220, 220);
        GameRunner.batch.draw(flag, 5, board.getBoardHeight() * TILE_SIZE + 300, 300, 150);
        font.getData().setScale(7, 7);
        font.setColor(Color.BLACK);
        font.draw(GameRunner.batch, "x " + lifepoints, 210, board.getBoardHeight() * TILE_SIZE + 100);
        font.draw(GameRunner.batch, "x " + (9 - healthpoints), 210, board.getBoardHeight() * TILE_SIZE + 250);
        font.draw(GameRunner.batch, "x " + flagCount, 210, board.getBoardHeight() * TILE_SIZE + 400);

        setUp();

        // Must choose cards untilt here's 5 cards in the hand
        if (hand.size() != 5) {
            chooseCards = true;
        }

        // Stops choosing card when there's 5 cards in the hand
        if (hand.size() == 5) {
            chooseCards = false;
        }

        // Draws cards depending on you're choosing cards or have a full hand
        if(chooseCards){
            drawDealtCards();
        } else {
            drawHand();
        }
        displayCards();

        GameRunner.batch.end();
    }


    /**
     * disposing of textures when the screen is changed/terminated
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

    /**
     * Places dealt cards onto the screen
     */
    private void drawDealtCards(){
        drawCards(dealtCards);
    }

    /**
     * Displaying the drawn cards together with their priorities
     */
    private void displayCards() {
        for (Button button : cards.keySet()) {
            GameRunner.batch.draw(button.getButtonTexture(), button.getButtonX(), button.getButtonY(), 390, 490);
            font.draw(GameRunner.batch, "" + cards.get(button).getPriority(), button.getButtonX() + 100, yPriority);
        }
    }

    /**
     * Places the hands cards onto the screen, check the drawCards method for more info
     */
    private void drawHand() {
        drawCards(hand);
    }

    /**
     * Sets the new phase where new cards are being dealt to players.
     */
    private void setUp() {
        if (startOfRound == true) {
            game.dealCards();
            dealtCards = game.getPlayersDealtCards();
        }
        startOfRound = false;
    }

    /**
     * Method takes the first card out of hand and makes the move.
     */
    private void round() {

        int counter = 0;

        while (counter < 5) {
            game.moveActorsByCards(counter);
            counter++;
        }
        game.discard();

        //game.dealCards();
        //dealtCards = game.getPlayersDealtCards();
        //drawDealtCards();

        startOfRound = true;
    }

    /**
     * Sets the screen coordinates for all of the cards in the list, the cards are not displayed until the displayCards method is called
     * @param list a list of cards which will be placed on the screen
     */
    private void drawCards(ArrayList<Card> list){
        cards = new HashMap<>();
        int x = powerdown.getButtonX() + 300;
        for(Card card:list){
            x += 450;
            switch (card.getType()) {
                case MOVE1:
                    MOVE1 = new Button("cards/move1_card.png");
                    MOVE1.setButtonCoords(x, yCard);
                    cards.put(MOVE1,card);
                    break;
                case MOVE2:
                    MOVE2 = new Button("cards/move2_card.png");
                    MOVE2.setButtonCoords(x, yCard);
                    cards.put(MOVE2,card);
                    break;
                case MOVE3:
                    MOVE3 = new Button("cards/move3_card.png");
                    MOVE3.setButtonCoords(x, yCard);
                    cards.put(MOVE3,card);
                    break;
                case TURN180:
                    TURN180 = new Button("cards/180_turn_card.jpg");
                    TURN180.setButtonCoords(x, yCard);
                    cards.put(TURN180,card);
                    break;
                case TURNLEFT:
                    TURNLEFT = new Button("cards/left_turn_card.jpg");
                    TURNLEFT.setButtonCoords(x, yCard);
                    cards.put(TURNLEFT,card);
                    break;
                case TURNRIGHT:
                    TURNRIGHT = new Button("cards/right_turn_card.jpg");
                    TURNRIGHT.setButtonCoords(x, yCard);
                    cards.put(TURNRIGHT,card);
                    break;
                case BACKUP:
                    BACKUP = new Button("cards/backup_card.jpg");
                    BACKUP.setButtonCoords(x, yCard);
                    cards.put(BACKUP,card);
                    break;
            }
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

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
        Direction dir = player.getDirection();

        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.RIGHT:
                if (dir != Direction.EAST)
                    player.setDirection(Direction.EAST);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.LEFT:
                if (dir != Direction.WEST)
                    player.setDirection(Direction.WEST);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.UP:
                if (dir != Direction.NORTH)
                    player.setDirection(Direction.NORTH);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.DOWN:
                if (dir != Direction.SOUTH)
                    player.setDirection(Direction.SOUTH);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.Q:
                game.checkPosition(player);
                break;
            case com.badlogic.gdx.Input.Keys.X:
                game.moveActorsByCards(counter % 5);
                counter++;
                break;
            case com.badlogic.gdx.Input.Keys.C:
                game.resetActors();
                break;
            default:
        }
        return super.keyDown(keycode);
    }
}
