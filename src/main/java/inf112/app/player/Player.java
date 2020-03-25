package inf112.app.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.Card;
import inf112.app.Game;

import java.util.ArrayList;
import java.util.Scanner;


public class Player {

    //Vector holds players position
    private Vector2 player;
    private TiledMapTileLayer.Cell playerCell;
    private Position pos;
    private Direction dir;
    private Game game;
    private ArrayList<Card> hand;
    private ArrayList<Card> initHand;
    private int healthScore;
    private int lives;
    private Position spawnPoint;
    private Scanner scanner;

    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param game game object
     */
    public Player(Game game) {
        this.initHand = new ArrayList<>();
        this.game = game;
        dir = Direction.SOUTH;
        healthScore = 9;
        lives = 3;
        this.hand = new ArrayList<>();

        Texture robotTextures = new Texture("assets/robot.png");
        TextureRegion[][] robotTexture = TextureRegion.split(robotTextures,
                (int) Game.TILE_SIZE,
                (int) Game.TILE_SIZE);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTexture[0][0]));

        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);
        spawnPoint = pos;
        scanner = new Scanner(System.in);

    }

    /**
     * Constructor mainly for testing purposes
     */
    public Player() {
        dir = Direction.SOUTH;
        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);
    }

    /**
     * Refreshing the former players position to null
     * Implements the board-movement of a player
     * Prints out the current position
     *
     * @param keycode - an integer representation of different possible inputs
     * @return true/false
     */

    public int getHealthScore() {
        return healthScore;
    }

    public int getLives(){
        return lives;
    }

    /**
     * subtracts healthscore and lives if healtscore is less than 1. Also sets the player to dead when there are no more lives.
     */
    public void looseHealthScore() {
        if (this.healthScore <= 1) {
            looselife();
        } else {
            this.healthScore -= 1;
        }
    }
    /**
     * Lets a player gain healthscore if it isn´t already at it´s maximum.
     */
    public void gainHealthScore() {
        if (this.healthScore < 9) {
            this.healthScore += 1;//Usikker på om dette er riktig regel
        }
    }
    public void looselife(){
        if(this.lives < 1){
            //updateState(); to dead.
        }
        else{
            this.lives -= 1;
        }
    }
    /**
     * Changes the position of the player to a new position
     *
     * @param newPos the new position
     */
    public void setPos(Position newPos) {
        pos = new Position(newPos);
    }

    /**
     * Changes the position of the player to a predetermined position
     *
     * @return the new position
     */
    public Position checkpoint() {
        return pos = new Position(0, 0);
    }

    /**
     * Sets the current player state to default
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setImage() {
        return playerCell;
    }

    /**
     * Checks which imagine to show to the screen depending on player state
     */
    public void updateState() {
        playerCell.setRotation(dir.getID());
    }

    /**
     * Gets the current position of the player
     *
     * @return position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Gets the current direction of the player
     *
     * @return direction
     */
    public Direction getDirection() {
        return dir;
    }

    /**
     * Changes the direction of the player to a new direction
     *
     * @param dir direction
     */
    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    /**
     * Adds a card to the hand from initial hand.
     */
    public void setHand() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < initHand.size(); j++) {
                System.out.println((j+1) + " " + initHand.get(j).toString());
            }
            System.out.println("Pick a card. Any card.");
            int idx = scanner.nextInt() - 1;
            while (initHand.size() <= idx || idx < 0) {
                System.out.println("Out of range");
                idx = scanner.nextInt() - 1;
            }
                System.out.println();
            hand.add(initHand.get(idx));
            initHand.remove(idx);
        }
    }

    /**
     * Returns a card in hand given index
     *
     * @param index int
     * @return Card
     */
    public Card getCard(int index) {
        return hand.get(index);
    }

    public void setSpawnPoint(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
        this.pos = spawnPoint;
    }

    public Position getSpawnPoint() {
        return spawnPoint;
    }
    public void setInitHand(Card card) {
        initHand.add(card);
    }
}

