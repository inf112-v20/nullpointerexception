package inf112.app.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import inf112.app.Card;
import inf112.app.Deck;
import inf112.app.Game;
import inf112.app.board.Board;
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
    // y value for priority labels of cards
    private final int yPriority;
    private final int yCard;
    private Button MOVE1;
    private Button MOVE2;
    private Button MOVE3;
    private Button TURNRIGHT;
    private Button TURNLEFT;
    private Button BACKUP;
    private Button TURN180;
    private Button start_round;

    private final OrthographicCamera camera;
    private ArrayList<Card> dealtCards;
    private ArrayList<Card> hand;
    private final BitmapFont font = new BitmapFont();
    private final Deck deck;
    private final Player player;
    private boolean chooseCards;
    private HashMap<Button, Card> cards;
    private boolean roundstart;


    public GameScreen() {
        game = new Game();
        board = game.getBoard();
        dealtCards = game.getPlayersDealtCards();
        powerdown = new Button("powerdown.png");
        powerdown.setButtonCoords(0, board.getBoardHeight() * TILE_SIZE + 500);
        start_round = new Button("start_round.png");
       start_round.setButtonCoords(powerdown.getButtonTexture().getWidth() + 250, board.getBoardHeight() * TILE_SIZE + 550);
        lifes = new Texture("life.png");
        health = new Texture("healthpoints.png");
        yPriority = board.getBoardHeight() * TILE_SIZE + 800;
        yCard = board.getBoardHeight() * TILE_SIZE + 200;
        deck = game.getDeckObject();
        player = game.getPlayerObject();

        hand = player.getHand();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE + 1000);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(board.getBoard());
        renderer.setView(camera);
        Gdx.input.setInputProcessor(this);

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
        chooseCards = true;
        roundstart = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 input = new Vector3(screenX, screenY, 0);
        camera.unproject(input);
        if(start_round.buttonIsHovered(input) && !chooseCards) {
            roundstart = true;
            System.out.println("Starting round!");
            round();

        } else {
            if(chooseCards) {
                for (Button b : cards.keySet()) {
                    if (b.buttonIsHovered(input)) {
                        if (!hand.contains(cards.get(b))) {
                            hand.add(cards.get(b));
                            dealtCards.remove(cards.get(b));
                            System.out.println("A card has been added to the hand");
                            String c = cards.get(b).toString();
                            System.out.println(c);
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
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        renderer.render();

        GameRunner.batch.setProjectionMatrix(camera.combined);
        int lifepoints = game.getPlayersLifeCount();
        int healthpoints = game.getPlayersHitPoints();
        GameRunner.batch.begin();


        GameRunner.batch.draw(powerdown.getButtonTexture(), powerdown.getButtonX(), powerdown.getButtonY(), 350, 350);
        GameRunner.batch.draw(start_round.getButtonTexture(), start_round.getButtonX(), start_round.getButtonY(), 270, 250);
        GameRunner.batch.draw(lifes, 20, board.getBoardHeight() * TILE_SIZE + 50, 150, 150);
        GameRunner.batch.draw(health, 0, board.getBoardHeight() * TILE_SIZE + 50 + 200, 220, 220);
        font.getData().setScale(7, 7);
        font.setColor(Color.BLACK);
        font.draw(GameRunner.batch, "x " + lifepoints, 170, board.getBoardHeight() * TILE_SIZE + 50 + lifes.getHeight() / 2);
        font.draw(GameRunner.batch, "x " + healthpoints, 210, board.getBoardHeight() * TILE_SIZE + 400 + health.getHeight()/2);

        if(hand.isEmpty()) {
            chooseCards = true;
            //player.resetDealtCards();
            //player.setDealtCards(deck.dealCards(Math.min(9, player.getHitPoints())));
            //dealtCards = game.getPlayersDealtCards();
           }

        else if(hand.size()==5){
            deck.setDiscardPile(dealtCards);
            chooseCards = false;
        }


        if(chooseCards){
            drawDealtCards();
        } else {
            drawHand();
        }
        displayCards();


        GameRunner.batch.end();
    }



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

    private void drawDealtCards(){
        drawCards(dealtCards);
    }

    private void displayCards(){
        for(Button button : cards.keySet()) {
            GameRunner.batch.draw(button.getButtonTexture(), button.getButtonX(), button.getButtonY(), 390, 490);
            font.draw(GameRunner.batch, "" + cards.get(button).getPriority(), button.getButtonX() + 100, yPriority);
        }
    }

    private void drawHand(){
        drawCards(hand);
    }

    /**
     * Method takes the first card out of hand and makes the move.
     */
    private void round() {

            game.movedByCard(player, hand.get(0).getType());
            //game.moveActorsByCards(counter);
            //hand.remove(0);
            game.discard();

            //game.dealCards();
            //dealtCards = game.getPlayersDealtCards();
            //drawDealtCards();

    }

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




}
