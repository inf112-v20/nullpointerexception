package inf112.app.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.app.Card;

import java.util.ArrayList;


public class Player implements IActor {

    //Vector holds players position
    private final TiledMapTileLayer.Cell playerCell;
    private Position currentPos;
    private Direction direction;
    protected ArrayList<Card> hand;
    protected ArrayList<Card> dealtCards;
    private Position spawnPoint;
    private final ArrayList<Integer> flagIDList;
    private int hitPoints;
    private int lifeCount;
    private boolean isDead;
    private final boolean win;


    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param spawn   spawn point of the player
     * @param texture texture of player
     */
    public Player(Position spawn, TextureRegion texture) {
        hitPoints = MAX_HP;
        lifeCount = MAX_LIFE;

        dealtCards = new ArrayList<>();
        hand = new ArrayList<>();
        flagIDList = new ArrayList<>();
        flagIDList.add(55);
        flagIDList.add(63);
        flagIDList.add(71);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(texture));
        isDead = false;
        win = false;

        spawnPoint = spawn;
        currentPos = spawn;
        setDirection(Direction.EAST);
    }


    public void printCards() {
        for (Card card : hand) {
            System.out.println(card.toString());
        }
    }

    @Override
    public void handleDamage() {
        if (hitPoints <= 0) {
            loseLife();
            System.out.println("Player lost a life.");
        } else {
            System.out.println("Player lost one hit point.");
            hitPoints -= 1;
        }
    }

    @Override
    public void loseLife() {
        if (lifeCount <= 0)
            isDead = true;
        else {
            lifeCount -= 1;
            hitPoints = MAX_HP;
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
    public void isOnFlag(Integer tileID) {
        if (!flagIDList.isEmpty() && tileID.equals(flagIDList.get(0))) {
            flagIDList.remove(0);
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
    }

    @Override
    public Card getCard(int index) {
        return hand.get(index);
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

    @Override
    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> discard() {
        ArrayList<Card> discardList = new ArrayList<>();
        for (int i = 0; i < Math.min(hitPoints, 5); i++) {
            discardList.add(hand.remove(0));
        }
        return discardList;
    }
}