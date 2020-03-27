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
    private ArrayList<Card> hand;
    private ArrayList<Card> initHand;
    private final int HIT_POINTS = 9;
    private final int LIFE_COUNT = 3;
    private Position spawnPoint;
    private Scanner scanner;
    private int hitPoints;
    private int lifeCount;
    private boolean isDead;

    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     */
    public Player() {
        this.initHand = new ArrayList<>();
        hitPoints = HIT_POINTS;
        lifeCount = LIFE_COUNT;
        hand = new ArrayList<>();
        isDead = false;

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
    public Player(Direction testdir) {
        dir = testdir;
        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);
        isDead = false;
    }

    /**
     * subtracts healthscore and lives if healtscore is less than 1. Also sets the player to dead when there are no more lives.
     */
    public void handleDamage() {
        if (hitPoints < 0) {
            loseLife();
        } else {
            System.out.println("Player lost one hit point.");
            hitPoints -= 1;
        }
    }

    /**
     * Lets a player gain healthscore if it isn´t already at it´s maximum.
     */
    public void repairHitPoints() {
        if (hitPoints < 9) {
            hitPoints += 1;
            System.out.println("Player gained one hit point.");
        }
        System.out.println("Player has max hit points.");

    }

    public void loseLife() {
        if (lifeCount < 1)
            isDead = true;
        else {
            lifeCount -= 1;
            hitPoints = HIT_POINTS;
            System.out.println("Player lost a life.");
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

    public void printStatus() {
        System.out.println("Current hit points")
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
                //System.out.println((j+1) + " " + initHand.get(j).toString());
            }
            System.out.println("Pick a card. Any card.");
            int idx = 1;

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

