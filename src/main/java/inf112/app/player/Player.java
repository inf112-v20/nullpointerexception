package inf112.app.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.Card;

import java.util.ArrayList;


public class Player implements IActor {

    //Vector holds players position
    private Vector2 player;
    private TiledMapTileLayer.Cell playerCell;
    private Position currentPos;
    private Direction dir;
    private ArrayList<Card> hand;
    private ArrayList<Card> dealtCards;
    private Position spawnPoint;
    private int hitPoints;
    private int lifeCount;
    private boolean isDead;


    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param spawn   spawn point of the player
     * @param texture texture of player
     */
    public Player(Position spawn, TextureRegion texture) {
        dealtCards = new ArrayList<>();
        hitPoints = MAX_HP;
        lifeCount = MAX_LIFE;
        hand = new ArrayList<>();
        isDead = false;

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(texture));

        player = new Vector2();
        currentPos = spawn;
        spawnPoint = spawn;
    }

    /**
     * Prints out player attributes
     */
    public void printStatus() {
        System.out.println("Current hit points: " + hitPoints);
        System.out.println("Current life count points: " + lifeCount);
    }

    public void printCards() {
        for (Card card : hand) {
            System.out.println(card.toString());
        }
    }

    @Override
    public void handleDamage() {
        if (hitPoints < 1) {
            loseLife();
        } else {
            System.out.println("Player lost one hit point.");
            hitPoints -= 1;
        }
    }

    @Override
    public void repairHitPoints() {
        if (hitPoints < 9) {
            hitPoints += 1;
            System.out.println("Player gained one hit point.");
        }
        System.out.println("Player has max hit points.");

    }

    @Override
    public void loseLife() {
        if (lifeCount < 1)
            isDead = true;
        else {
            lifeCount -= 1;
            hitPoints = MAX_HP;
            System.out.println("Player lost a life.");
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public Position checkpoint() {
        return currentPos = new Position(0, 0);
    }

    @Override
    public TiledMapTileLayer.Cell setTexture() {
        return playerCell;
    }

    @Override
    public void updateTexture() {
        playerCell.setRotation(dir.getID());
    }

    @Override
    public Position getPos() {
        return currentPos;
    }

    @Override
    public void setPos(Position newPos) {
        currentPos = new Position(newPos);
    }

    @Override
    public Direction getDirection() {
        return dir;
    }

    @Override
    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    /**
     * Adds a card to the hand from initial hand.
     */
    @Override
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
        if (hand.isEmpty()) {
            System.out.println("No more cards");
            return null;
        }
        Card card = hand.get(index);
        hand.remove(index);
        return card;
    }

    /**
     * @return spawnPoint The spawn point of the player
     */
    @Override
    public Position getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    public void setSpawnPoint(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
        this.currentPos = spawnPoint;
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
    public void setDealtCards(ArrayList<Card> card) {
        dealtCards = card;
    }

    /**
     * @return The players hit points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @return lifeCount
     */
    public int getLifeCount() {
        return lifeCount;
    }
}