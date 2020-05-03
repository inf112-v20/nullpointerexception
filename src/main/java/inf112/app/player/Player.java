package inf112.app.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.app.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class Player implements IActor {

    //Vector holds players position
    private final TiledMapTileLayer.Cell playerCell;
    private Position currentPos;
    private Direction direction;
    private final ArrayList<Card> hand;
    private ArrayList<Card> dealtCards;
    private final ArrayList<Integer> flagList;
    private Position spawnPoint;
    private final ArrayList<Integer> flagIDList;
    private int hitPoints;
    private int lifeCount;
    private boolean isDead;
    private final boolean onFlag;
    private final boolean win;


    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param spawn   spawn point of the player
     * @param texture texture of player
     */
    public Player(Position spawn, TextureRegion texture, Map<Integer, Position> flagMap) {
        hitPoints = MAX_HP;
        lifeCount = MAX_LIFE;

        flagIDList = new ArrayList<>();
        flagIDList.addAll(flagMap.keySet());
        Collections.sort(flagIDList);
        flagList = new ArrayList<>(flagIDList.size());
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(texture));
        isDead = false;
        onFlag = false;
        win = false;

        spawnPoint = spawn;
        currentPos = spawn;
        setDirection(Direction.EAST);

        dealtCards = new ArrayList<>();
        hand = new ArrayList<>();
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
    public boolean onFlag() {
        return onFlag;
    }

    @Override
    public void isOnFlag(Integer tileID) {
        if (flagList.isEmpty()) {
            win();
        }
        if (onFlag && flagList.size() == 3 && tileID.equals(flagIDList.get(0))) {
            flagList.remove(1);
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public boolean win() {
        return win;
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
    public Position getPos() {
        return currentPos;
    }

    @Override
    public void setPos(Position newPos) {
        currentPos = new Position(newPos);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction dir) {
        direction = dir;
        playerCell.setRotation(direction.getID());
    }

    @Override
    public void setHand() {
        for (int i = 0; i < 5; i++) {
            hand.add(dealtCards.remove(0));
        }
    }

    @Override
    public Card getCard(int index) {
        if (hand.isEmpty()) {
            System.out.println("No more cards");
            return null;
        }
        Card card = hand.get(index);
        hand.remove(index);
        return card;
    }

    @Override
    public Position getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    public void setSpawnPoint(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    @Override
    public void resetDealtCards() {
        dealtCards = new ArrayList<>();
    }

    @Override
    public ArrayList<Card> getDealtCards() {
        return dealtCards;
    }

    @Override
    public void setDealtCards(ArrayList<Card> card) {
        dealtCards = card;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getLifeCount() {
        return lifeCount;
    }
}