package inf112.app.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.app.Card;

import java.util.ArrayList;
import java.util.Map;

public class Actor implements IActor {

    private final int MAX_HP = 9;
    private final int MAX_LIFE = 3;
    private TiledMapTileLayer.Cell actorTexture;
    private Position currentPos;
    private Direction direction;
    private ArrayList<Card> hand;
    private ArrayList<Card> dealtCards;
    private ArrayList<Integer> flagList;
    private Map<Integer,Position> flagMap;
    private ArrayList<Integer> flagIDList;
    private Position spawnPoint;
    private int hitPoints;
    private int lifeCount;
    private boolean isDead;
    private boolean onFlag;
    private boolean win;

    public Actor(Position spawn, TextureRegion texture) {
        hitPoints = MAX_HP;
        lifeCount = MAX_LIFE;
        actorTexture = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(texture));
        isDead = false;
        onFlag = false;
        win = false;

        flagIDList.addAll(flagMap.keySet());
        spawnPoint = spawn;
        currentPos = spawn;
        setDirection(Direction.EAST);

        dealtCards = new ArrayList<>();
        hand = new ArrayList<>();
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
        if (hitPoints < 9)
            hitPoints += 1;
    }

    @Override
    public void loseLife() {
        if (lifeCount < 1) {
            lifeCount -= 1;
            isDead = true;
        } else {
            lifeCount -= 1;
            hitPoints = MAX_HP;
        }

    }
    @Override
    public boolean onFlag(){
        return onFlag;
    }

    @Override
    public void isOnFlag(Integer tileID){
        Map<Integer, Position> flagIntegers;
        if(flagList.isEmpty()){
            win();
        }
        if(onFlag && flagList.size()==3 && tileID.equals(flagIDList.get(0))){
            flagList.remove(1);
        }
        else if (onFlag && tileID.compareTo(flagList.size()-1) == -1){
            flagList.remove(1);
        }
    }
    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public boolean win(){ return win; }

    @Override
    public Position checkpoint() {
        return spawnPoint;
    }

    @Override
    public TiledMapTileLayer.Cell setTexture() {
        return actorTexture;
    }

    @Override
    public Position getPos() {
        return currentPos;
    }

    @Override
    public void setPos(Position pos) {
        currentPos = new Position(pos);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction newDir) {
        direction = newDir;
        actorTexture.setRotation(direction.getID());
    }

    @Override
    public void setHand() {
        for (int i = 0; i < 5; i++) {
            hand.add(dealtCards.remove(0));
        }
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
    public void setDealtCards(ArrayList<Card> cards) {
        dealtCards = cards;
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
    public Card getCard(int index) {
        if (hand.isEmpty()) {
            System.out.println("No more cards");
            return null;
        }

        return hand.remove(index);
    }
}
