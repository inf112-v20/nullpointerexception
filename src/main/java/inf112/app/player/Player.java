package inf112.app.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.Card;
import inf112.app.Game;

import java.util.ArrayList;


public class Player {

    //Vector holds players position
    private Vector2 player;
    private TiledMapTileLayer.Cell playerCell;
    private Position pos;
    private Direction dir;
    private ArrayList<Card> hand;
    private ArrayList<Card> dealtCards;
    private final int HIT_POINTS = 9;
    private final int LIFE_COUNT = 3;
    private Position spawnPoint;
    private int hitPoints;
    private int lifeCount;
    private boolean isDead;

    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     */
    public Player() {
        dealtCards = new ArrayList<>();
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

    }

    /**
     * Constructor mainly for testing purposes
     */
    public Player(Direction testdir) {
        dealtCards = new ArrayList<>();
        dir = testdir;
        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);
        hitPoints = HIT_POINTS;
        lifeCount = LIFE_COUNT;
        isDead = false;

    }

    /**
     * Subtracts healthscore and lives if healtscore is less than 1. Also sets the player to dead when there are no more lives.
     */
    public void handleDamage() {
        if (hitPoints < 1) {
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

    /**
     * Player loses a life and updates isDead if there are no more lives.
     */
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
     * Returns whether or not the player is dead
     *
     * @return boolean
     */
    public boolean isDead() {
        return isDead;
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
    public TiledMapTileLayer.Cell setPlayerTexture() {
        return playerCell;
    }


    /**
     * Checks which imagine to show to the screen depending on player state
     */
    public void updateState() {
        playerCell.setRotation(dir.getID());
    }

    /**
     * Prints out player attributes
     */
    public void printStatus() {
        System.out.println("Current hit points: " + hitPoints);
        System.out.println("Current life count points: " + lifeCount);
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
            int idx = 1;
            hand.add(dealtCards.get(idx));
            dealtCards.remove(idx);
        }
    }

    /**
     * Returns a card in hand given index
     *
     * @param index int
     * @return Card
     */
    public Card getCard(int index) {
        Card card = hand.get(index);
        hand.remove(index);
        return card;
    }

    public void setSpawnPoint(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
        this.pos = spawnPoint;
    }

    /**
     * @return spawnPoint The spawn point of the player
     */
    public Position getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * Resets the initHand
     */
    public void resetDealtCards() {
        dealtCards = new ArrayList<>();
    }

    /**
     * @return initHand An arraylist of cards from dealtCards.
     */
    public ArrayList<Card> getDealtCards() {
        return dealtCards;
    }

    /**
     * Adds a program card to dealtcards.
     *
     * @param card Card
     */
    public void setDealtCards(Card card) {
        dealtCards.add(card);
    }

    /**
     *
     * @return The players hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     *
     * @return lifeCount
     */
    public int getLifeCount() {
        return lifeCount;
    }
}