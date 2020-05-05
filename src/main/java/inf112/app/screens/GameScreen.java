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
import inf112.app.Card;
import inf112.app.Deck;
import inf112.app.Game;
import inf112.app.board.Board;
import inf112.app.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends ScreenAdapter {

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
    private boolean gamerunning;

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

        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
        drawDealtCards();
        chooseCards = true;
        gamerunning = true;
        loop();
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


        GameRunner.batch.draw(powerdown.getButtonTexture(), powerdown.getButtonX(), powerdown.getButtonY(), 350, 350);
        GameRunner.batch.draw(start_round.getButtonTexture(), start_round.getButtonX(), start_round.getButtonY(), 270, 250);
        GameRunner.batch.draw(lifes, 20, board.getBoardHeight() * TILE_SIZE + 50, 150, 150);
        GameRunner.batch.draw(health, 0, board.getBoardHeight() * TILE_SIZE + 50 + 200, 220, 220);
        font.getData().setScale(7, 7);
        font.setColor(Color.BLACK);
        font.draw(GameRunner.batch, "x " + lifepoints, 170, board.getBoardHeight() * TILE_SIZE + 50 + lifes.getHeight() / 2);
        font.draw(GameRunner.batch, "x " + healthpoints, 210, board.getBoardHeight() * TILE_SIZE + 400 + health.getHeight()/2);


        GameRunner.batch.end();
    }

    private void loop(){
        while(gamerunning){
            Gdx.graphics.requestRendering();
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

            //Her velger man 5 kort, trykk på venstre-nedre hjørne av kortet for å velge
            displayCards();
            if(hand.size() < 5 && chooseCards) {
                for (Button button : cards.keySet()) {
                    if (button.buttonIsHovered(input)) {
                        if (Gdx.input.justTouched()) {
                            if (!hand.contains(cards.get(button))) {
                                hand.add(cards.get(button));
                                dealtCards.remove(cards.get(button));
                                System.out.println("A card has been added to the hand");
                                String c = cards.get(button).toString();
                                System.out.println(c);
                            }
                        }
                    }
                }
            } else {
                deck.setDiscardPile(dealtCards);
                drawHand();
                player.resetDealtCards();
                int counter = 0;
                    while(counter < 5){
                    if(start_round.buttonIsHovered(input)){
                        if(Gdx.input.justTouched()){
                            System.out.println("Starting round!");
                            round();
                            //drawHand();
                            counter++;

                        }
                    }
                }
                //game.dealCards();
                player.setDealtCards(deck.dealCards(Math.min(9, player.getHitPoints())));
                dealtCards = game.getPlayersDealtCards();
                drawDealtCards();
                chooseCards = true;
            }

        }

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

}
