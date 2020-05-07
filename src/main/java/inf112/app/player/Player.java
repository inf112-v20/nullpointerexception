package inf112.app.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.app.cards.Card;

import java.util.ArrayList;


public class Player implements IActor {

    private final TiledMapTileLayer.Cell playerCell;

    protected ArrayList<Card> hand;
    protected ArrayList<Card> dealtCards;
    private final ArrayList<Integer> listOfFlags;

    private int hitPoints;
    private int lifeCount;
    private int flagCount;

    private boolean poweringDown;
    private boolean isDead;

    private Direction direction;
    private Position currentPos;
    private Position spawnPoint;


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
        flagCount = 0;

        dealtCards = new ArrayList<>();
        hand = new ArrayList<>();
        listOfFlags = new ArrayList<>();
        listOfFlags.add(55);
        listOfFlags.add(63);
        listOfFlags.add(71);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(texture));

        spawnPoint = spawn;
        currentPos = spawn;
        setDirection(Direction.EAST);
    }


    /**
     * @return how many flags have been captured
     */
    public int getFlagCount() {
        return flagCount;
    }

    @Override
    public void handleDamage() {
        if (hitPoints <= 0) {
            loseLife();
        } else {
            hitPoints -= 1;
        }
    }

    @Override
    public void loseLife() {
        if (lifeCount <= 0)
            isDead = true;
        else {
            lifeCount -= 1;
            hitPoints = MAX_HP - 2;
            //isDead = true;
        }
    }

    @Override
    public void repairHitPoints() {
        if (hitPoints < 9) {
            hitPoints += 1;
        }
    }

    @Override
    public void isOnFlag(Integer tileID) {
        if (!listOfFlags.isEmpty() && tileID.equals(listOfFlags.get(0))) {
            listOfFlags.remove(0);
            flagCount++;
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

    @Override
    public boolean isPoweringDown() {
        return poweringDown;
    }

    @Override
    public void setPoweringDown(boolean poweringDown) {
        this.poweringDown = poweringDown;
    }

    public ArrayList<Card> discard() {
        ArrayList<Card> discardList = new ArrayList<>();
        for (int i = 0; i < Math.min(hitPoints, 5); i++) {
            discardList.add(hand.remove(0));
        }
        return discardList;
    }
}